package com.example.sd6501_assignment1_2180511.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
//import com.example.sd6501_assignment1_2180511.Schedule.DeleteDialog;
import com.example.sd6501_assignment1_2180511.Journal.JournalAdapter;
import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.Journal.JournalFragment;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleAdapter;
import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleFragment;

import java.util.ArrayList;

//implements DeleteDialog.OnInputListener
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    //database
    DatabaseHandler db;

    //Journal adapter
    private RecyclerView rvJournal;
    private JournalAdapter adapterJournal;
    private ArrayList<JournalClass> journals;
    TextView noJournals;

    //Schedule adapter
    private RecyclerView rvSchedule;
//    private RecyclerView.LayoutManager layoutManagerSchedule;
    private ScheduleAdapter adapterSchedule;
    private ArrayList<ScheduleClass> schedules;
    TextView noSchedules;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container,false);
        findViews(v);

        //get session id
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null : "Activity not found";
        final long userID = mainActivity.getUserID();

        db = new DatabaseHandler(getActivity().getApplicationContext());

        //schedule recycler
        schedules = new ArrayList<>();
        schedules.addAll(db.getAllSchedulesForUserByID(userID));
        buildScheduleRecyclerView();

        //journal recycler
        journals = new ArrayList<>();
        journals.addAll(db.getAllJournalsForUserByID(userID));
        buildJournalRecyclerView();

        return v;
    }
    private void findViews(View v) {
        noJournals = v.findViewById(R.id.home_tv_noJournals);
        noSchedules = v.findViewById(R.id.home_tv_noSchedules);
        rvSchedule = v.findViewById(R.id.home_rv_schedule);
        rvJournal = v.findViewById(R.id.home_rv_journal);
    }

    private void buildJournalRecyclerView() {
        rvJournal.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerJournal = new LinearLayoutManager(getActivity().getApplication(),
                LinearLayoutManager.HORIZONTAL, false);
        rvJournal.setLayoutManager(layoutManagerJournal);
        adapterJournal = new JournalAdapter(journals);
        rvJournal.setAdapter(adapterJournal);

        adapterJournal.setOnItemClickListener(new JournalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "In Development: change categories",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                deleteJournal(position);
            }

            @Override
            public void onEditClick(int position) {
                editJournal(position);
            }

            @Override
            public void onFavouriteClick(int position) {
                Toast.makeText(getContext(), "In development: add to start of list",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAlarmClick(int position) {
                Toast.makeText(getContext(), "In development: edit time data",
                        Toast.LENGTH_SHORT).show();
            }
        });
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvJournal);
    }

    private void buildScheduleRecyclerView() {
        rvSchedule.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerSchedule = new LinearLayoutManager(getActivity().getApplication(),
                LinearLayoutManager.HORIZONTAL, false);
        rvSchedule.setLayoutManager(layoutManagerSchedule);
        adapterSchedule = new ScheduleAdapter(schedules);
        rvSchedule.setAdapter(adapterSchedule);

        adapterSchedule.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "In development: Sort by option",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
//                showDialog();
                deleteSchedule(position);
            }

            @Override
            public void onEditClick(int position) {
                editSchedule(position);
            }

            @Override
            public void onFavouriteClick(int position) {
                Toast.makeText(getContext(), "In development: edit array", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAlarmClick(int position) {
                Toast.makeText(getContext(), "In development: edit time data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLocationClick(int position) {
                Toast.makeText(getContext(), "In development: set location uing GPS",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Simulate view pager
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvSchedule);
    }

    private void editSchedule(int position) {
        //gather data
        ScheduleClass schedule = schedules.get(position);
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

//    @Override
    private void deleteSchedule(int position) {
        ScheduleClass schedule = schedules.get(position);
        db.deleteSchedule(schedule);

        Toast.makeText(getActivity().getApplication(), "Entry deleted", Toast.LENGTH_SHORT).show();

        schedules.remove(position);
        adapterSchedule.notifyItemRemoved(position);
    }

    private void editJournal(int position) {
        JournalClass journal = journals.get(position);
        JournalFragment journalFragment = new JournalFragment();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setJournalID(journal.getId());

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout_container, journalFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void deleteJournal(int position) {
        JournalClass journal = journals.get(position);
        db.deleteJournal(journal);

        Toast.makeText(getActivity().getApplication(), "Entry deleted", Toast.LENGTH_SHORT).show();

        journals.remove(position);
        adapterJournal.notifyItemRemoved(position);
    }

//    public void showDialog() {
//        Log.d(TAG, "onDeleteClick: Trying to open dialog");
//        DeleteDialog dialog = new DeleteDialog();
//        dialog.show(getFragmentManager(), "DeleteDialog");
//    }
}
