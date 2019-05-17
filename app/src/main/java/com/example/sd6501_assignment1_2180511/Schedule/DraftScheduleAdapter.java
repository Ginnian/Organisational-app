package com.example.sd6501_assignment1_2180511.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;

public class DraftScheduleAdapter extends RecyclerView.Adapter<DraftScheduleAdapter.ViewHolder> {
    private ArrayList<ScheduleClass> recycleList;
    private OnItemClickListener listen;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        listen = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView subjectTV, entryTV, dateTV;
        public ImageButton delete, edit;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.cardEvent_iv_image);
            subjectTV = itemView.findViewById(R.id.cardEvent_tv_subject);
            entryTV = itemView.findViewById(R.id.cardEvent_tv_entry);
            dateTV = itemView.findViewById(R.id.cardEvent_tv_date);
            delete = itemView.findViewById(R.id.cardEvent_ib_delete);
            edit = itemView.findViewById(R.id.cardEvent_ib_edit);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
    public DraftScheduleAdapter(ArrayList<ScheduleClass> eventList) {
        recycleList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card_schedule, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(v, listen);
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
