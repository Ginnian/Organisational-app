package com.example.sd6501_assignment1_2180511.Schedule;

import com.example.sd6501_assignment1_2180511.LoginRegister.UserClass;
import com.example.sd6501_assignment1_2180511.R;

public class ScheduleClass {
    private int id;
    private int imageResource;
    private String entry;
    private String subject;
    private String date;

    //Storege info
    public static final String TABLE_SCHEDULE = "journal_table";
    //Columns
    public static final String KEY_SCHEDULE_ID = "id";
    public static final String KEY_SCHEDULE_ENTRY = "entry";
    public static final String KEY_SCHEDULE_SUBJECT = "subject";
    public static final String KEY_SCHEDULE_NOTIFYEVENT = "event_timestamp";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_SCHEDULE + "("
            + KEY_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_SCHEDULE_ENTRY + " TEXT,"
            + KEY_SCHEDULE_SUBJECT + " TEXT,"
            + KEY_SCHEDULE_NOTIFYEVENT + " TEXT"
            + UserClass.KEY_USER_ID + " INTEGER,"
            + "FOREIGN KEY(" + UserClass.KEY_USER_ID
            + ") REFERENCES " + UserClass.TABLE_USERS + "(" + UserClass.KEY_USER_ID +"))";

    public ScheduleClass() {
        this.imageResource = R.drawable.ic_schedule;
        this.entry = "Default entry";
        this.subject = "Unclassified";
        this.date = "Today";
    }

    public ScheduleClass(int id, int imageResource, String entry, String subject, String date) {
        this.id = id;
        this.imageResource = imageResource;
        this.entry = entry;
        this.subject = subject;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
