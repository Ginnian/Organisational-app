package com.example.sd6501_assignment1_2180511.ToDo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {
    private EditText task;
    private ListView taskList;
    private FloatingActionButton floatingActionButton;
    final ArrayList<String> allTasks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todo, container, false);

        findViews(v);
        floatingActionButton.setEnabled(false);

        enableTextEntryFabButton();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Task added",
                        Toast.LENGTH_SHORT).show();
                allTasks.add(task.getText().toString().trim());
                taskList.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_checked, allTasks));
            }
        });
        return v;
    }
    private void findViews(View v) {
        task = v.findViewById(R.id.todo_et_task);
        taskList = v.findViewById(R.id.todo_lv_alltasks);
        floatingActionButton = v.findViewById(R.id.todo_fab_addtolist);
    }

    private void enableTextEntryFabButton() {
        task.addTextChangedListener(new TextWatcher() {
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
    }


}
