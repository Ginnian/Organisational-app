package com.example.sd6501_assignment1_2180511;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.sd6501_assignment1_2180511.Journal.JournalClass;
import com.example.sd6501_assignment1_2180511.LoginRegister.UserClass;
import com.example.sd6501_assignment1_2180511.Schedule.ScheduleClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandlerUsers extends SQLiteOpenHelper {
    //debugging
    private static final String TAG = "DatabaseHandlerUsers";

    //database info
    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "usersdb";

    public DatabaseHandlerUsers(Context context) {
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
                new String[]{email},//Where clause
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
}
