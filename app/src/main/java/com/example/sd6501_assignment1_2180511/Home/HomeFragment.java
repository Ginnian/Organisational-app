package com.example.sd6501_assignment1_2180511.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sd6501_assignment1_2180511.DatabaseHandlerUsers;
import com.example.sd6501_assignment1_2180511.Journal.JournalCardAdapter;
import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleCardAdapter;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    //database
    DatabaseHandlerUsers db;

    //schedule
    ViewPager viewPagerSchedule;
    ScheduleCardAdapter adapterSchedule;
    List<ScheduleClass> schedules;
    TextView noSchedules;

    //journal
    ViewPager viewPagerJournal;
    JournalCardAdapter adapterJournal;
    List<JournalClass> journals;
    TextView noJournals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home,container,false);

        findViews(v);

        db = new DatabaseHandlerUsers(getActivity().getApplicationContext());

        //get session id
        MainActivity mainActivity = (MainActivity) getActivity();
        final long userID = mainActivity.getUserID();

        setScheduleView(v, userID);
        setJournalView(v, userID);

        //sort by date
        //sort by category

        return v;
    }

    private void setJournalView(View v, long id) {
        journals = new ArrayList<>();
        journals.addAll(db.getAllJournalsForUserByID(id));

        if(!journals.isEmpty()) {
            adapterJournal = new JournalCardAdapter(journals, getActivity().getApplicationContext());
            viewPagerJournal = v.findViewById(R.id.home_vp_journal);
            viewPagerJournal.setAdapter(adapterJournal);
            viewPagerJournal.setPadding(0,0,130,0);
        } else {
            noJournals.setText("No Journals saved. Tap the icon to add a new one");
        }
    }

    private void setScheduleView(View v, long id) {
        schedules = new ArrayList<>();
        schedules.addAll(db.getAllSchedulesForUserByID(id));

        if(!schedules.isEmpty()) {
            adapterSchedule = new ScheduleCardAdapter(schedules, getActivity().getApplicationContext());
            viewPagerSchedule = v.findViewById(R.id.home_vp_schedule);
            viewPagerSchedule.setAdapter(adapterSchedule);
            viewPagerSchedule.setPadding(0,0,130,0);

            viewPagerSchedule.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    viewPagerSchedule.setBackgroundColor(getResources().getColor(R.color.transparent));
                }

                @Override
                public void onPageSelected(int i) {

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        } else {
            noSchedules.setText("No Schedules saved. Tap the icon to add an new one");
        }
    }

    private void findViews(View v) {
        noJournals = v.findViewById(R.id.home_tv_noJournals);
        noSchedules = v.findViewById(R.id.home_tv_noSchedules);
    }
}
