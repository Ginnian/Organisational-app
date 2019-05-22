package com.example.sd6501_assignment1_2180511.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private ArrayList<ScheduleClass> recycleList;
    private OnItemClickListener listen;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onFavouriteClick(int position);
        void onAlarmClick(int position);
        void onLocationClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        listen = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView eventName, time, date, locationTV;
        public Button edit, delete;
        public ImageButton alarm, favourite, locationBtn;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.scheduleCard_iv_image);
            eventName = itemView.findViewById(R.id.scheduleCard_tv_eventName);
            time = itemView.findViewById(R.id.scheduleCard_tv_time);
            date = itemView.findViewById(R.id.scheduleCard_tv_date);
            locationTV = itemView.findViewById(R.id.scheduleCard_tv_location);
            edit = itemView.findViewById(R.id.scheduleCard_btn_edit);
            delete = itemView.findViewById(R.id.scheduleCard_btn_delete);
            alarm = itemView.findViewById(R.id.scheduleCard_ib_alarm);
            favourite = itemView.findViewById(R.id.scheduleCard_ib_favourite);
            locationBtn = itemView.findViewById(R.id.scheduleCard_ib_location);

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

            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onFavouriteClick(position);
                        }
                    }
                }
            });

            alarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onAlarmClick(position);
                        }
                    }
                }
            });

            locationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onLocationClick(position);
                        }
                    }
                }
            });
        }
    }

    public ScheduleAdapter(ArrayList<ScheduleClass> eventList) {
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
        viewHolder.locationTV.setText(currentEvent.getSubject());
        viewHolder.date.setText(currentEvent.getDate());
        viewHolder.eventName.setText(currentEvent.getEntry());
    }

    @Override
    public int getItemCount() {
        return recycleList.size();
    }
}
