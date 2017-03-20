package com.codeclan.housework4.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David Hale on 20/03/2017.
 */

public class TaskListDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_list.db";
    private static final int DATABASE_VERSION = 1;

    public TaskListDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TASK_TABLE = "CREATE TABLE " +
                TaskListContract.TasksEntry.TABLE_NAME + " (" +
                TaskListContract.TasksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskListContract.TasksEntry.COLUMN_NAME + " STRING NOT NULL, " +
                TaskListContract.TasksEntry.COLUMN_DESCRIPTION + " STRING NOT NULL, " +
                TaskListContract.TasksEntry.COLUMN_IS_COMLETED + " BOOLEAN NOT NULL" +
                ");";

        final String SQL_CREATE_DAY_TABLE = "CREATE TABLE " +
                TaskListContract.DaysEntry.TABLE_NAME + " (" +
                TaskListContract.DaysEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskListContract.DaysEntry.COLUMN_DAY_NAME + " STRING NOT NULL" +
                ");";

        final String SQL_CREATE_TASK_LIST_TABLE = "CREATE TABLE " +
                TaskListContract.DayTasksEntry.TABLE_NAME + " (" +
                TaskListContract.DayTasksEntry.COLUMN_DAY_ID + " INTEGER, " +
                TaskListContract.DayTasksEntry.COLUMN_TASK_ID + " INTEGER, FOREIGN KEY (" +
                TaskListContract.DayTasksEntry.COLUMN_DAY_ID + ") REFERENCES " +
                TaskListContract.DaysEntry.TABLE_NAME + "(" +
                TaskListContract.DaysEntry._ID + "), FOREIGN KEY (" +
                TaskListContract.DayTasksEntry.COLUMN_TASK_ID + ") REFERENCES " +
                TaskListContract.TasksEntry.TABLE_NAME + "(" +
                TaskListContract.TasksEntry._ID +
                "));";
        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_DAY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TASK_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskListContract.DayTasksEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskListContract.TasksEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskListContract.DaysEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
