package com.example.sd6501_assignment1_2180511.Journal;

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

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private ArrayList<JournalClass> recycleList;
    private OnItemClickListener listen;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onFavouriteClick(int position);
        void onAlarmClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        listen = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView subject, date, entry;
        public Button edit, delete;
        public ImageButton alarm, favourite;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.cardEvent_iv_image);
            subject = itemView.findViewById(R.id.cardEvent_tv_subject);
            date = itemView.findViewById(R.id.cardEvent_tv_date);
            entry = itemView.findViewById(R.id.cardEvent_tv_entry);
            edit = itemView.findViewById(R.id.cardEvent_btn_edit);
            delete = itemView.findViewById(R.id.cardEvent_btn_delete);
            alarm = itemView.findViewById(R.id.cardEvent_ib_alarm);
            favourite = itemView.findViewById(R.id.cardEvent_ib_favourite);

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
        }
    }

    public JournalAdapter(ArrayList<JournalClass> eventList) {
        recycleList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card_journal, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(v, listen);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        JournalClass currentJournal = recycleList.get(i);

        viewHolder.image.setImageResource(currentJournal.getImageResource());
        viewHolder.subject.setText(currentJournal.getSubject());
        viewHolder.date.setText(currentJournal.getDate());
        viewHolder.entry.setText(currentJournal.getEntry());
    }

    @Override
    public int getItemCount() {
        return recycleList.size();
    }
}
