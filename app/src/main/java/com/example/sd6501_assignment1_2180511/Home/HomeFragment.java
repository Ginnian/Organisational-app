package com.example.sd6501_assignment1_2180511.Home;

import android.content.Context;
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
import com.example.sd6501_assignment1_2180511.Schedule.DraftScheduleAdapter;
import com.example.sd6501_assignment1_2180511.Journal.JournalCardAdapter;
import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleCardAdapter;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    //database
    DatabaseHandler db;

    //schedule
//    ViewPager viewPagerSchedule;
//    ScheduleCardAdapter adapterSchedule;
//    List<ScheduleClass> schedules;
//    TextView noSchedules;

    //journal
    ViewPager viewPagerJournal;
    JournalCardAdapter adapterJournal;
    List<JournalClass> journals;
    TextView noJournals;

    //draft schedule adapter
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private DraftScheduleAdapter adapter;
    private ArrayList<ScheduleClass> draftSchedules;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home,container,false);

        findViews(v);
        //get session id
        MainActivity mainActivity = (MainActivity) getActivity();
        final long userID = mainActivity.getUserID();

        db = new DatabaseHandler(getActivity().getApplicationContext());

        //draft recycler
        draftSchedules = new ArrayList<>();
        draftSchedules.addAll(db.getAllSchedulesForUserByID(userID));
        buildRecyclerVIew(v);

//        setScheduleView(v, userID);
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

//    private void setScheduleView(View v, long id) {
//        schedules = new ArrayList<>();
//        schedules.addAll(db.getAllSchedulesForUserByID(id));
//
//        if(!schedules.isEmpty()) {
//            adapterSchedule = new ScheduleCardAdapter(schedules, getActivity().getApplicationContext());
////            viewPagerSchedule = v.findViewById(R.id.home_vp_schedule);
//            viewPagerSchedule.setAdapter(adapterSchedule);
//            viewPagerSchedule.setPadding(0,0,130,0);
//
//            viewPagerSchedule.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int i, float v, int i1) {
//                    viewPagerSchedule.setBackgroundColor(getResources().getColor(R.color.transparent));
//                }
//
//                @Override
//                public void onPageSelected(int i) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int i) {
//
//                }
//            });
//        } else {
//            noSchedules.setText("No Schedules saved. Tap the icon to add an new one");
//        }
//    }

    private void buildRecyclerVIew(View v) {
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplication(),
                LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        adapter = new DraftScheduleAdapter(draftSchedules);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new DraftScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
            }

            @Override
            public void onEditClick(int position) {
                editItem(position);
            }
        });

        //Simulate view pager
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
    }

    private void editItem(int position) {
        //gather data
        ScheduleClass schedule = draftSchedules.get(position);
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setScheduleID(schedule.getId());

        //Send to fragment to be edited
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout_container, scheduleFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void deleteItem(int position) {
        ScheduleClass schedule = draftSchedules.get(position);
        db.deleteSchedule(schedule);

        Toast.makeText(getActivity().getApplication(), "Entry deleted", Toast.LENGTH_SHORT).show();

        draftSchedules.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void findViews(View v) {
        noJournals = v.findViewById(R.id.home_tv_noJournals);
//        noSchedules = v.findViewById(R.id.home_tv_noSchedules);
        rv = v.findViewById(R.id.home_rv_schedule);
    }
}
