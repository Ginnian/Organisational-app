package com.example.sd6501_assignment1_2180511.Journal;

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

public class JournalCardAdapter extends PagerAdapter {
    private List<JournalClass> journals;
    private LayoutInflater layoutInflater;
    private Context context;

    public JournalCardAdapter(List<JournalClass> journals, Context context) {
        this.journals = journals;
        this.context = context;
    }

    @Override
    public int getCount() {
        return journals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_card_journal, container, false);

        TextView subjectTV, entryTV, dateTV, titleTV;

        titleTV = view.findViewById(R.id.journalCard_tv_title);
        subjectTV = view.findViewById(R.id.journalCard_tv_subject);
        entryTV = view.findViewById(R.id.journalCard_tv_entry);
        dateTV = view.findViewById(R.id.journalCard_tv_date);

        titleTV.setText(journals.get(position).getTitle());
        subjectTV.setText(journals.get(position).getSubject());
        dateTV.setText(journals.get(position).getDate());
        entryTV.setText(journals.get(position).getEntry());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
