package com.example.sd6501_assignment1_2180511.ToDo;

import java.util.ArrayList;

public class ToDoClass {
    private long toDoId;
    private String toDoName;
    private ArrayList<String> toDoItemsList;

    //Storege info
    public static final String TABLE_TODO = "todo_table";
    //Columns
    public static final String KEY_TODO_ID = "todo_id";
    public static final String KEY_TODO_NAME = "todo_name";
    public static final String KEY_TODO_ITEM = "todo_item";
    public static final String KEY_USER_ID = "accountID";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_TODO + "("
            + KEY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TODO_NAME + " TEXT,"
            + KEY_TODO_ITEM + " TEXT,"
            + KEY_USER_ID + " INTEGER" + ")";
//            + "FOREIGN KEY(" + UserClass.KEY_USER_ID
//            + ") REFERENCES " + UserClass.TABLE_USERS + "(" + KEY_JOURNAL_ID +"))";

    public ToDoClass() {}

    public ToDoClass(long toDoId, String toDoName, ArrayList<String> toDoItemsList) {
        this.toDoId = toDoId;
        this.toDoName = toDoName;
        this.toDoItemsList = toDoItemsList;
    }

    public long getToDoId() {
        return toDoId;
    }

    public void setToDoId(long toDoId) {
        this.toDoId = toDoId;
    }

    public String getToDoName() {
        return toDoName;
    }

    public void setToDoName(String toDoName) {
        this.toDoName = toDoName;
    }

    public ArrayList<String> getToDoItemsList() {
        return toDoItemsList;
    }

    public void setToDoItemsList(ArrayList<String> toDoItemsList) {
        this.toDoItemsList = toDoItemsList;
    }

    public int numberOfItemsInToDO(ArrayList<String> items) {
        return items.size();
    }
}
