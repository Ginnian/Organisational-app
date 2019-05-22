package com.example.sd6501_assignment1_2180511.Journal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sd6501_assignment1_2180511.DatabaseHandler;
import com.example.sd6501_assignment1_2180511.Home.MainActivity;
import com.example.sd6501_assignment1_2180511.R;

public class JournalFragment extends Fragment {
    //debug
    private static final String TAG = "JournalFragment";

    private EditText title, subject, entry;
    private FloatingActionButton floatingActionButton;
    //new entry to database
    JournalClass userEntryDetails = new JournalClass();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_journal, container,false);

        findViews(v);

        //get session id's
        MainActivity mainActivity = (MainActivity) getActivity();
        final long userID = mainActivity.getUserID();
        long journalID = mainActivity.getJournalID();

        //journal ID retrieved from main activity, referred by home fragment
        //if journal does not exist, create new journal
        if(journalID != 0) {
            DatabaseHandler db = new DatabaseHandler(getActivity().getApplication());
            final JournalClass editJournal = db.getJournalByID(journalID);

            //populate fields wih journal to edit
            title.setText(editJournal.getTitle());
            subject.setText(editJournal.getSubject());
            entry.setText(editJournal.getEntry());

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Updating journal");
                    setJournalEntryDetails(editJournal);
                    updateJournalInDB(editJournal);

                    Toast.makeText(getActivity().getApplication(), "Journal updated", Toast.LENGTH_SHORT).show();
                    resetTextFields();
                }
            });
            //reset temp journal id for future editing
            mainActivity.setJournalID(0);
        } else {
            floatingActionButton.setEnabled(false);
            //create new journal
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Save new journal to database");
                    setJournalEntryDetails(userEntryDetails);
                    saveJournalToDB(userID);
                    Toast.makeText(getActivity(), "New Journal added", Toast.LENGTH_SHORT).show();
                    resetTextFields();
                }
            });
        }

        //Disable fab if edit texts are empty
        entry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    floatingActionButton.setEnabled(false);
                } else {
                    floatingActionButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//        entry.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                switch(keyCode) {
//                    case KeyEvent.KEYCODE_ENTER:
//                        saveJournalEntryDetails();
//                        saveJournalToDB(userID);
//                        Toast.makeText(getActivity().getApplicationContext(), "Entry saved", Toast.LENGTH_SHORT).show();
//                        resetTextFields();
//                        return true;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        }); //End keyboard submit listener
        return v;
    }

    private void findViews(View v) {
        title = v.findViewById(R.id.jourrnalEntry_et_title);
        subject = v.findViewById(R.id.journalEntry_et_subject);
        entry = v.findViewById(R.id.journalEntry_et_entry);
        floatingActionButton = v.findViewById(R.id.journalEntry_fab_addJournal);
    }

    private void setJournalEntryDetails(JournalClass journal) {
        journal.setEntry(entry.getText().toString().trim());
        journal.setTitle(title.getText().toString().trim());
        journal.setSubject(subject.getText().toString().trim());
    }

    private void saveJournalToDB(long userID) {
        Log.d(TAG, "saveJournalToDB: Saving journal to db");
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        db.insertJournal( userID,userEntryDetails.getEntry(),
                                 userEntryDetails.getSubject(),
                                 userEntryDetails.getDate(),
                                 userEntryDetails.getTitle());
    }

    private void updateJournalInDB(JournalClass journal) {
        Log.d(TAG, "updateJournalInDB: updating journal in db");
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplication());
        db.updateJournal(journal);
    }

    private void resetTextFields() {
        title.setText("");
        subject.setText("");
        entry.setText("");
    }
}
