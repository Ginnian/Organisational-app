package com.example.sd6501_assignment1_2180511.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.sd6501_assignment1_2180511.Journal.JournalFragment;
import com.example.sd6501_assignment1_2180511.LoginRegister.LoginActivity;
import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Routine.RoutineFragment;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleFragment;
import com.example.sd6501_assignment1_2180511.ToDo.ToDoFragment;
import com.example.sd6501_assignment1_2180511.Toolbar.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    //debug
    private static final String TAG = "MainActivity";

    EditText title, subject, entry;
    BottomNavigationView navBar;
    Toolbar topToolbar;

    //Communicate data between fragments
    long userID, journalID, scheduleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        navBar.setOnNavigationItemSelectedListener(navListener);

        setSupportActionBar(topToolbar);

        //user session
        userID = getIntent().getIntExtra("userID", 0);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout_container,
                new HomeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        title = findViewById(R.id.jourrnalEntry_et_title);
        subject = findViewById(R.id.journalEntry_et_subject);
        entry = findViewById(R.id.journalEntry_et_entry);
        navBar = findViewById(R.id.layout_bottomNavigation);
        topToolbar = findViewById(R.id.home_tb_top);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Log.d(TAG, "onNavigationItemSelected: Switching between fragments");
            Fragment fragment = null;

            switch (menuItem.getItemId()) {
                case R.id.menu_nav_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.menu_nav_journal:
                    fragment = new JournalFragment();
                    break;
                case R.id.menu_nav_schedule:
                    fragment = new ScheduleFragment();
                    break;
                case R.id.menu_nav_todo:
                    fragment = new ToDoFragment();
                    break;
                case R.id.menu_nav_routine:
                    fragment = new RoutineFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout_container,
                    fragment).commit();
            return true;
        }
    };

    //data communication between fragments
    public long getUserID() {
        return userID;
    }

    public long getJournalID() {
        return journalID;
    }

    public void setJournalID(long id) {
        journalID = id;
    }

    public long getScheduleID(){
        return scheduleID;
    }

    public void setScheduleID(long id) {
        scheduleID = id;
    }
    //end data communication
}
