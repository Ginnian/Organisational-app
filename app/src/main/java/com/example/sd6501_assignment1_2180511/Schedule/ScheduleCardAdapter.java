package com.example.sd6501_assignment1_2180511.Schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sd6501_assignment1_2180511.R;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;

import java.util.List;

public class ScheduleCardAdapter extends PagerAdapter {
    private List<ScheduleClass> schedules;
    private LayoutInflater layoutInflater;
    private Context context;

    public ScheduleCardAdapter(List<ScheduleClass> schedules, Context context) {
        this.schedules = schedules;
        this.context = context;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_card_schedule, container, false);

        ImageView image;
        TextView subjectTV, entryTV, dateTV;

        image = view.findViewById(R.id.cardEvent_iv_image);
        subjectTV = view.findViewById(R.id.cardEvent_tv_subject);
        entryTV = view.findViewById(R.id.cardEvent_tv_entry);
        dateTV = view.findViewById(R.id.cardEvent_tv_date);

        image.setImageResource(schedules.get(position).getImageResource());
        subjectTV.setText(schedules.get(position).getSubject());
        dateTV.setText(schedules.get(position).getDate());
        entryTV.setText(schedules.get(position).getEntry());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
