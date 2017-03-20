package com.codeclan.housework4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codeclan.housework4.data.TaskListContract;
import com.codeclan.housework4.data.TaskListDBHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DayTasksActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private ArrayList<Day> days;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_tasks);

        Intent intent = getIntent();

        TaskListDBHelper dbHelper = new TaskListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor allDaysCursor = getAllDays();
        days = generateArrayListOfDays(getAllDays());

        Cursor taskListCursor = getTaskListForDay(1);
//        Cursor taskListCursor = getAllTasks();
        tasks = generateArrayListOfTasks(taskListCursor);

        DayTasksAdapter adapter = new DayTasksAdapter(this, tasks);
        ListView listView = (ListView) findViewById(R.id.day_tasks_list);
        listView.setAdapter(adapter);
    }

    private Cursor getAllDays() {
        return mDb.query(
                TaskListContract.DaysEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public ArrayList<Day> generateArrayListOfDays(Cursor cursor) {
        ArrayList<Day> days = new ArrayList<Day>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TaskListContract.DaysEntry.COLUMN_DAY_NAME));
            long id = cursor.getLong(cursor.getColumnIndex(TaskListContract.DaysEntry._ID));
            days.add(new Day(id, name));
        }

        return days;
    }

    private Cursor getTaskListForDay(int dayId) {
        final String MY_QUERY = "SELECT " +
                TaskListContract.TasksEntry.TABLE_NAME + ".* FROM " +
                TaskListContract.TasksEntry.TABLE_NAME + " INNER JOIN " +
                TaskListContract.DayTasksEntry.TABLE_NAME + " ON " +
                TaskListContract.TasksEntry.TABLE_NAME + "." +
                TaskListContract.TasksEntry._ID + " = " +
                TaskListContract.DayTasksEntry.COLUMN_TASK_ID + " WHERE " +
                TaskListContract.DayTasksEntry.COLUMN_DAY_ID + " = " + String.valueOf(dayId);

        return mDb.rawQuery(MY_QUERY, null);
    }

    private Cursor getAllTasks() {
        return mDb.query(
                TaskListContract.TasksEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public ArrayList<Task> generateArrayListOfTasks(Cursor cursor) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry.COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry.COLUMN_DESCRIPTION));
            boolean isCompleted = cursor.getLong(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry.COLUMN_IS_COMLETED)) > 0;
            long id = cursor.getLong(cursor.getColumnIndex(TaskListContract.TasksEntry._ID));
            tasks.add(new Task(id, name, description, isCompleted));
        }

        return tasks;
    }

}
