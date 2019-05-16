package com.example.sd6501_assignment1_2180511.Schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sd6501_assignment1_2180511.DatabaseHandlerUsers;
import com.example.sd6501_assignment1_2180511.DraftScheduleAdapter;
import com.example.sd6501_assignment1_2180511.Home.MainActivity;
import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    //debug
    private static final String TAG = "ScheduleFragment";

    private CalendarView calendar;
    private EditText eventEntry;
    private TextView currentDate;
    private FloatingActionButton floatingActionButton;

//    Build event view
    ArrayList<ScheduleClass> events = new ArrayList<>();
    ScheduleClass eventObj = new ScheduleClass();
//    RecyclerView rv;
//    RecyclerView.Adapter rvAdapter;
//    RecyclerView.LayoutManager rvLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        findViews(v);
//        buildRecyclerLayout();

        //get session id
        MainActivity mainActivity = (MainActivity) getActivity();
        final long userID = mainActivity.getUserID();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onSelectedDayChange: Retrieving the date from the calendar");

                currentDate.setText(dayOfMonth + "/" + month + "/" + year);
                eventObj.setDate(dayOfMonth + "/" + month + "/" + year);
            }
        });

        floatingActionButton.setEnabled(false);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventObj.setEntry(eventEntry.getText().toString().trim());
                
                saveJournalToDB(userID);
                Toast.makeText(getActivity().getApplicationContext(), 
                        "New schedule added", Toast.LENGTH_SHORT).show();
//                events.add(eventObj);
                eventEntry.getText().clear();
            }
        });

//        Disable fab if entry editText is empty'
        eventEntry.addTextChangedListener(new TextWatcher() {
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

        return v;
    }
    public void findViews(View v) {
        calendar = v.findViewById(R.id.schedule_cv_events);
        eventEntry = v.findViewById(R.id.schedule_et_entry);
        currentDate = v.findViewById(R.id.schedule_tv_dateSelected);
        floatingActionButton = v.findViewById(R.id.schedule_fab_addSchedule);
//        rv = v.findViewById(R.id.schedule_rv_allevents);
    }  //find view variables

//    public void buildRecyclerLayout() {
//        rv.setHasFixedSize(true);
//        rvLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        rvAdapter = new DraftScheduleAdapter(events);
//        rv.setLayoutManager(rvLayoutManager);
//        rv.setAdapter(rvAdapter);
//    } //Build event layout for the recycler view

    private void saveJournalToDB(long userID) {
        Log.d(TAG, "saveJournalToDB: Saving schedule to database");
        
        DatabaseHandlerUsers dbhandler = new DatabaseHandlerUsers(getActivity().getApplicationContext());
        dbhandler.insertSchedule(userID, eventObj.getEntry(), eventObj.getSubject(), eventObj.getDate());
    }
}
