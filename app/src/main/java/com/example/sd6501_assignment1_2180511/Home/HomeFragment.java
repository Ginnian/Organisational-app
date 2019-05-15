package com.example.sd6501_assignment1_2180511.Home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
//    ExpandableListView elv;
    ViewPager viewPager;
    Adapter adapter;
    List<ScheduleClass> schedules;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

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

        adapter = new Adapter(schedules, getActivity().getApplicationContext());

        viewPager = v.findViewById(R.id.home_vp_schedule);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0,0,130,0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                viewPager.setBackgroundColor(getResources().getColor(R.color.transparent));
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
//        elv = v.findViewById(R.id.home_evl_overview);

        //create and display list of entries
        //sort by date
        //sort by category

        return v;
    }
}
