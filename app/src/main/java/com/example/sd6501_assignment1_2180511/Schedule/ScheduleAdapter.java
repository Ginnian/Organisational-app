package com.example.sd6501_assignment1_2180511.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    ArrayList<ScheduleClass> recycleList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView subjectTV;
        public TextView entryTV;
        public TextView dateTV;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cardEvent_iv_image);
            subjectTV = itemView.findViewById(R.id.cardEvent_tv_subject);
            entryTV = itemView.findViewById(R.id.cardEvent_tv_entry);
            dateTV = itemView.findViewById(R.id.cardEvent_tv_date);
        }
    }
    public ScheduleAdapter(ArrayList<ScheduleClass> eventList) {
        recycleList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ScheduleClass currentEvent = recycleList.get(i);

        viewHolder.image.setImageResource(currentEvent.getImageResource());
        viewHolder.subjectTV.setText(currentEvent.getSubject());
        viewHolder.dateTV.setText(currentEvent.getDate());
        viewHolder.entryTV.setText(currentEvent.getEntry());
    }

    @Override
    public int getItemCount() {
        return recycleList.size();
    }
}
