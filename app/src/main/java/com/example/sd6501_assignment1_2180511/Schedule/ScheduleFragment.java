package com.example.sd6501_assignment1_2180511.Schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.example.sd6501_assignment1_2180511.DatabaseHandler;
import com.example.sd6501_assignment1_2180511.Home.MainActivity;
import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    //debug
    private static final String TAG = "ScheduleFragment";

    private CalendarView calendar;
    private EditText eventEntry;
    private TextView currentDate, subject;
    private FloatingActionButton floatingActionButton;

    ScheduleClass eventObj = new ScheduleClass();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        findViews(v);

        //get session id's
        MainActivity mainActivity = (MainActivity) getActivity();
        final long userID = mainActivity.getUserID();
        long scheduleID = mainActivity.getScheduleID();

        setDate();

        //If event exists, update row
        if(scheduleID != 0) {
            //editing the schedule
            DatabaseHandler db = new DatabaseHandler(getActivity().getApplication());
            final ScheduleClass editSchedule = db.getScheduleByID(scheduleID);

            //populate fields with event to edit
            eventEntry.setText(editSchedule.getEntry());
            currentDate.setText(editSchedule.getDate());

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Trying to update schedule");
                    setScheduleDetails(editSchedule);
                    updateScheduleInDB(editSchedule);

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Schedule updated", Toast.LENGTH_SHORT).show();

                    eventEntry.getText().clear();
                }
            });

            mainActivity.setScheduleID(0); //reset schedule id for future editing
        } else { //if event does not exist, create new row
            //disable save button on creation
            floatingActionButton.setEnabled(false);
            //create a new schedule
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setScheduleDetails(eventObj);
                    saveScheduleToDB(userID);

                    Toast.makeText(getActivity().getApplicationContext(),
                            "New schedule added", Toast.LENGTH_SHORT).show();

                    eventEntry.getText().clear();
                }
            });
        }

//        Disable fab if entry editText is empty
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

    private void setScheduleDetails(ScheduleClass schedule) {
        schedule.setEntry(eventEntry.getText().toString().trim());
        schedule.setDate(currentDate.getText().toString().trim());
        schedule.setSubject("default location in development"); //debug
    }

    private void setDate() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onSelectedDayChange: Retrieving the date from the calendar");

                currentDate.setText(dayOfMonth + "/" + month + "/" + year);
                eventObj.setDate(dayOfMonth + "/" + month + "/" + year);
            }
        });
    }

    public void findViews(View v) {
        calendar = v.findViewById(R.id.schedule_cv_events);
        eventEntry = v.findViewById(R.id.schedule_et_entry);
        currentDate = v.findViewById(R.id.schedule_tv_dateSelected);
        floatingActionButton = v.findViewById(R.id.schedule_fab_addSchedule);
    }

    private void saveScheduleToDB(long userID) {
        Log.d(TAG, "saveJournalToDB: Saving schedule to database");
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        db.insertSchedule(userID, eventObj.getEntry(), eventObj.getSubject(), eventObj.getDate());
    }

    private void updateScheduleInDB(ScheduleClass scheduleClass) {
        Log.d(TAG, "updateScheduleInDB: Trying to update a schedule entry");
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplication());
        db.updateSchedule(scheduleClass);
    }
}
