package com.codeclan.housework4;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.codeclan.housework4.data.TaskListContract;

import java.util.ArrayList;

/**
 * Created by David Hale on 20/03/2017.
 */

public class Task {

    private long id;
    private String name;
    private String description;
    private boolean isCompleted;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.isCompleted = false;
    }

    public Task(long id, String name, String description, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        isCompleted = isCompleted;
    }

    public long addToDB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.TasksEntry.COLUMN_NAME, this.name);
        cv.put(TaskListContract.TasksEntry.COLUMN_DESCRIPTION, this.description);
        cv.put(TaskListContract.TasksEntry.COLUMN_IS_COMLETED, this.isCompleted);

        return db.insert(TaskListContract.TasksEntry.TABLE_NAME, null, cv);
    }
}
