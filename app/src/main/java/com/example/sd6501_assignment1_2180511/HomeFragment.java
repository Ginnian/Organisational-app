package com.example.sd6501_assignment1_2180511;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class HomeFragment extends Fragment {
    ExpandableListView elv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home,container,false);

        elv = v.findViewById(R.id.home_evl_overview);

        //create and display list of entries
        //sort by date
        //sort by category

        return v;
    }
}
