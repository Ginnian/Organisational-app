package com.example.sd6501_assignment1_2180511;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sd6501_assignment1_2180511.Journal.JournalFragment;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleFragment;
import com.example.sd6501_assignment1_2180511.ToDo.ToDoFragment;

public class MainActivity extends AppCompatActivity {
    EditText title;
    EditText subject;
    EditText entry;
    TextView displayEntry;
    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        navBar.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout_container,
                new HomeFragment()).commit();
    }

    private void findViews() {
        title = findViewById(R.id.jourrnalEntry_et_title);
        subject = findViewById(R.id.journalEntry_et_subject);
        entry = findViewById(R.id.journalEntry_et_entry);
        displayEntry = findViewById(R.id.journalEntry_tv_savedentry);
        navBar = findViewById(R.id.layout_bottomNavigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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

                default:
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout_container,
                    fragment).commit();
            return true;
        }
    };
}
