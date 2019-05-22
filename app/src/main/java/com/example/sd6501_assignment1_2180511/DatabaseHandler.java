package com.example.sd6501_assignment1_2180511;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.LoginRegister.UserClass;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;
import com.example.sd6501_assignment1_2180511.ToDo.ToDoClass;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    //debugging
    private static final String TAG = "DatabaseHandler";

    //database info
    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "usersdb";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Trying to create new tables");
        db.execSQL(UserClass.CREATE_TABLE);
        db.execSQL(JournalClass.CREATE_TABLE);
        db.execSQL(ScheduleClass.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserClass.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + JournalClass.TABLE_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleClass.TABLE_SCHEDULE);
        onCreate(db);
    }

    public long insertUser (String name, String password, String email) {
        Log.d(TAG, "addUser: Trying to add a user to database");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(UserClass.KEY_USERNAME, name);
        cValues.put(UserClass.KEY_PASSWORD, password);
        cValues.put(UserClass.KEY_EMAIL, email);

        long newRowId = db.insert(UserClass.TABLE_USERS, null, cValues);
        db.close();

        return newRowId; // row id
    }

//    public  void createToDoListTable(String category, ArrayList<ToDoClass> toDoList) {
//
//    }

    public UserClass getUserByID(long id) {
        //debug
        Log.d(TAG, "getUser: Trying to retrieve user from database");

        //read only database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserClass.TABLE_USERS,
                new String[]{UserClass.KEY_USER_ID, UserClass.KEY_USERNAME,
                             UserClass.KEY_PASSWORD, UserClass.KEY_EMAIL},
                UserClass.KEY_USER_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare user object
        UserClass user = new UserClass( cursor.getInt(cursor.getColumnIndex(UserClass.KEY_USER_ID)),
                cursor.getString(cursor.getColumnIndex(UserClass.KEY_USERNAME)),
                cursor.getString(cursor.getColumnIndex(UserClass.KEY_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(UserClass.KEY_EMAIL)));

        cursor.close();

        return user;
    }

    public void updateUser(UserClass user) {
        Log.d(TAG, "updateUser: Trying to update user in database");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(UserClass.KEY_USERNAME, user.getName());
        cValues.put(UserClass.KEY_PASSWORD, user.getPassword());
        cValues.put(UserClass.KEY_EMAIL, user.getEmail());

        db.update(UserClass.TABLE_USERS,
                cValues, UserClass.KEY_USER_ID + "=?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(UserClass user) {
        SQLiteDatabase db = this.getWritableDatabase();

        //delete record by user ID
        db.delete(UserClass.TABLE_USERS, UserClass.KEY_USER_ID + "=?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + UserClass.TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UserClass.TABLE_USERS,
                new String[]{UserClass.KEY_USER_ID, UserClass.KEY_USERNAME,
                        UserClass.KEY_PASSWORD, UserClass.KEY_EMAIL},
                UserClass.KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            return true;
        }
        //if email does not exist
        return false;
    }

    public ArrayList<UserClass> getAllUsersFromDB() {
        ArrayList<UserClass> listUsers = new ArrayList<>();

        String query = "SELECT * FROM " + UserClass.TABLE_USERS;
        //String query = "SELECT username, password, email FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and add to list
        if (cursor.moveToFirst()) {
            do {UserClass user = new UserClass();
                user.setId(cursor.getInt(cursor.getColumnIndex(UserClass.KEY_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserClass.KEY_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserClass.KEY_PASSWORD)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserClass.KEY_EMAIL)));

                listUsers.add(user);
            } while (cursor.moveToNext());
        }
        db.close();

        return listUsers;
    }

    //journals
    public long insertJournal (long id, String entry, String subject,
                               String timestamp, String title) {
        Log.d(TAG, "addUser: Trying to add a journal to database");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(JournalClass.KEY_JOURNAL_ENTRY, entry);
        cValues.put(JournalClass.KEY_JOURNAL_SUBJECT, subject);
        cValues.put(JournalClass.KEY_JOURNAL_TIMESTAMP, timestamp);
        cValues.put(JournalClass.KEY_JOURNAL_TITLE, title);
        cValues.put(JournalClass.KEY_USER_ID, id);

        long rowID = db.insert(JournalClass.TABLE_JOURNAL, null, cValues);
        db.close();

        return rowID;
    }

    public JournalClass getJournalByID(long id) {
        //debug
        Log.d(TAG, "getUser: Trying to retrieve journal from database");

        //read only database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(JournalClass.TABLE_JOURNAL,
                new String[]{JournalClass.KEY_JOURNAL_ID, JournalClass.KEY_JOURNAL_ENTRY,
                        JournalClass.KEY_JOURNAL_SUBJECT, JournalClass.KEY_JOURNAL_TIMESTAMP,
                        JournalClass.KEY_JOURNAL_TITLE},
                JournalClass.KEY_JOURNAL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare user object
        JournalClass journal = new JournalClass(
                cursor.getInt(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_ID)),
                cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_ENTRY)),
                cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_TITLE)));

        cursor.close();

        return journal;
    }

    public void updateJournal(JournalClass journal) {
        Log.d(TAG, "updateUser: Trying to update journal in database");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(JournalClass.KEY_JOURNAL_ENTRY, journal.getEntry());
        cValues.put(JournalClass.KEY_JOURNAL_SUBJECT, journal.getSubject());
        cValues.put(JournalClass.KEY_JOURNAL_TIMESTAMP, journal.getDate());
        cValues.put(JournalClass.KEY_JOURNAL_TITLE, journal.getTitle());

        db.update(JournalClass.TABLE_JOURNAL,
                cValues, JournalClass.KEY_JOURNAL_ID + "=?",
                new String[]{String.valueOf(journal.getId())});
        db.close();
    }

    public void deleteJournal(JournalClass journal) {
        SQLiteDatabase db = this.getWritableDatabase();

        //delete record by user ID
        db.delete(JournalClass.TABLE_JOURNAL, JournalClass.KEY_JOURNAL_ID + "=?",
                new String[]{String.valueOf(journal.getId())});
        db.close();
    }

    public int getJournalCount() {
        String countQuery = "SELECT  * FROM " + JournalClass.TABLE_JOURNAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public ArrayList<JournalClass> getAllJournalsForUserByID(long id) {
        ArrayList<JournalClass> listJournals = new ArrayList<>();

        String query = "SELECT * FROM " + JournalClass.TABLE_JOURNAL +
                       " WHERE " + JournalClass.KEY_USER_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and add to list
        if (cursor.moveToFirst()) {
            do {JournalClass journal = new JournalClass();
                journal.setId(cursor.getInt(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_ID)));
                journal.setEntry(cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_ENTRY)));
                journal.setSubject(cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_SUBJECT)));
                journal.setDate(cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_TIMESTAMP)));
                journal.setTitle(cursor.getString(cursor.getColumnIndex(JournalClass.KEY_JOURNAL_TITLE)));

                listJournals.add(journal);
            } while (cursor.moveToNext());
        }
        db.close();

        return listJournals;
    }

    //Schedules
    public long insertSchedule (long id, String entry, String subject, String timestamp) {
        Log.d(TAG, "insertSchedule: Trying to add schedule to the database");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(ScheduleClass.KEY_SCHEDULE_ENTRY, entry);
        cValues.put(ScheduleClass.KEY_SCHEDULE_NOTIFYEVENT, timestamp);
        cValues.put(ScheduleClass.KEY_SCHEDULE_SUBJECT, subject);
        cValues.put(ScheduleClass.KEY_SCHEDULE_USERID, id);
        //add image

        long rowID = db.insert(ScheduleClass.TABLE_SCHEDULE, null, cValues);
        db.close();

        return rowID;
    }

    public ArrayList<ScheduleClass> getAllSchedulesForUserByID(long id) {
        ArrayList<ScheduleClass> listSchedule = new ArrayList<>();

        String query = "SELECT * FROM " + ScheduleClass.TABLE_SCHEDULE +
                " WHERE " + ScheduleClass.KEY_SCHEDULE_USERID+ " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and add to list
        if (cursor.moveToFirst()) {
            do {ScheduleClass schedule = new ScheduleClass();
                schedule.setId(cursor.getInt(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_ID)));
                schedule.setEntry(cursor.getString(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_ENTRY)));
                schedule.setSubject(cursor.getString(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_SUBJECT)));
                schedule.setDate(cursor.getString(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_NOTIFYEVENT)));

                listSchedule.add(schedule);
            } while (cursor.moveToNext());
        }
        db.close();

        return listSchedule;
    }

    public void deleteSchedule(ScheduleClass schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        //delete record by user ID
        db.delete(ScheduleClass.TABLE_SCHEDULE, ScheduleClass.KEY_SCHEDULE_ID + "=?",
                new String[]{String.valueOf(schedule.getId())});
        db.close();
    }

    public ScheduleClass getScheduleByID(long id) {
        //debug
        Log.d(TAG, "getScheduleByID: Trying to retrieve schedule from database");

        //read only database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ScheduleClass.TABLE_SCHEDULE,
                new String[]{ScheduleClass.KEY_SCHEDULE_ID, ScheduleClass.KEY_SCHEDULE_ENTRY,
                        ScheduleClass.KEY_SCHEDULE_SUBJECT, ScheduleClass.KEY_SCHEDULE_NOTIFYEVENT},
                ScheduleClass.KEY_SCHEDULE_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare user object
        ScheduleClass schedule = new ScheduleClass(
                cursor.getInt(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_ID)),
                1, //debug
                cursor.getString(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_ENTRY)),
                cursor.getString(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(ScheduleClass.KEY_SCHEDULE_NOTIFYEVENT)));

        cursor.close();

        return schedule;
    }

    public void updateSchedule(ScheduleClass schedule) {
        Log.d(TAG, "updateUser: Trying to update journal in database");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(ScheduleClass.KEY_SCHEDULE_SUBJECT, schedule.getEntry());
        cValues.put(ScheduleClass.KEY_SCHEDULE_NOTIFYEVENT, schedule.getDate());
        cValues.put(ScheduleClass.KEY_SCHEDULE_ENTRY, schedule.getEntry());

        db.update(ScheduleClass.TABLE_SCHEDULE,
                cValues, JournalClass.KEY_JOURNAL_ID + "=?",
                new String[]{String.valueOf(schedule.getId())});
        db.close();
    }
}
