package com.example.sd6501_assignment1_2180511.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sd6501_assignment1_2180511.Journal.JournalCardAdapter;
import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleCardAdapter;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    //schedule
    ViewPager viewPagerSchedule;
    ScheduleCardAdapter adapterSchedule;
    List<ScheduleClass> schedules;

    //journal
    ViewPager viewPagerJournal;
    JournalCardAdapter adapterJournal;
    List<JournalClass> journals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home,container,false);

        schedules = new ArrayList<>();
        schedules.add(new ScheduleClass());
        schedules.add(new ScheduleClass());
        schedules.add(new ScheduleClass());
        schedules.add(new ScheduleClass());
        schedules.add(new ScheduleClass());

        adapterSchedule = new ScheduleCardAdapter(schedules, getActivity().getApplicationContext());
        viewPagerSchedule = v.findViewById(R.id.home_vp_schedule);
        viewPagerSchedule.setAdapter(adapterSchedule);
        viewPagerSchedule.setPadding(0,0,130,0);

        journals = new ArrayList<>();
        journals.add(new JournalClass());
        journals.add(new JournalClass());
        journals.add(new JournalClass());
        journals.add(new JournalClass());
        journals.add(new JournalClass());

        adapterJournal = new JournalCardAdapter(journals, getActivity().getApplicationContext());
        viewPagerJournal = v.findViewById(R.id.home_vp_journal);
        viewPagerJournal.setAdapter(adapterJournal);
        viewPagerJournal.setPadding(0,0,130,0);

        viewPagerJournal.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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

        //sort by date
        //sort by category

        return v;
    }
}
