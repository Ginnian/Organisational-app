package com.example.sd6501_assignment1_2180511.Journal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
    private JournalClass userEntryDetails;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_journal, container,false);
        userEntryDetails = new JournalClass();

        findViews(v);

        //get session id
        MainActivity mainActivity = (MainActivity) getActivity();
        final long userID = mainActivity.getUserID();

        entry.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch(keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        saveJournalEntryDetails();
                        saveJournalToDB(userID);
                        Toast.makeText(getActivity().getApplicationContext(), "Entry saved", Toast.LENGTH_SHORT).show();
                        resetTextFields();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        }); //End keyboard submit listener

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Trying to save new journal entry to database");
                saveJournalEntryDetails();
                saveJournalToDB(userID);
                Toast.makeText(getActivity().getApplicationContext(), "Entry saved", Toast.LENGTH_SHORT).show();
                resetTextFields();
            }
        });

        return v;
    }

    private void findViews(View v) {
        title = v.findViewById(R.id.jourrnalEntry_et_title);
        subject = v.findViewById(R.id.journalEntry_et_subject);
        entry = v.findViewById(R.id.journalEntry_et_entry);
        floatingActionButton = v.findViewById(R.id.journalEntry_fab_addJournal);
    }

    private void saveJournalEntryDetails() {
        userEntryDetails.setEntry(entry.getText().toString().trim());
        userEntryDetails.setTitle(title.getText().toString().trim());
        userEntryDetails.setSubject(subject.getText().toString().trim());
    }

    private void saveJournalToDB(long userID) {
        DatabaseHandler dbhandler = new DatabaseHandler(getActivity().getApplicationContext());
        dbhandler.insertJournal( userID,
                                 userEntryDetails.getEntry(),
                                 userEntryDetails.getSubject(),
                                 userEntryDetails.getDate(),  //debug
                                 userEntryDetails.getTitle());
    }

    private void resetTextFields() {
        title.setText(null);
        subject.setText(null);
        entry.setText(null);
    }
}
