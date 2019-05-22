package com.example.sd6501_assignment1_2180511.Routine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sd6501_assignment1_2180511.DatabaseHandler;
import com.example.sd6501_assignment1_2180511.Home.MainActivity;
import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleAdapter;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

public class RoutineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_routine,container,false);
        return v;
    }
}
