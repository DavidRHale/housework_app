package com.codeclan.housework4;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.codeclan.housework4.data.TaskListContract;

import java.util.ArrayList;

/**
 * Created by David Hale on 20/03/2017.
 */

public class Day {

    private String dayName;
    private long id;

    public Day(String dayName) {
        this.dayName = dayName;
    }

    public Day(long id, String dayName) {
        this.id = id;
        this.dayName = dayName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public long getId() {
        return id;
    }

    public long addToDB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.TasksEntry.COLUMN_NAME, this.dayName);

        return db.insert(TaskListContract.TasksEntry.TABLE_NAME, null, cv);
    }
}
