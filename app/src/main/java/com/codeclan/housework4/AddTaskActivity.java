package com.codeclan.housework4;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.codeclan.housework4.data.TaskListContract;
import com.codeclan.housework4.data.TaskListDBHelper;

public class AddTaskActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    private EditText name;
    private EditText description;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        name = (EditText) findViewById(R.id.enter_name);
        description = (EditText) findViewById(R.id.enter_description);
        monday = (CheckBox) findViewById(R.id.monday_checkbox);
        tuesday = (CheckBox) findViewById(R.id.tuesday_checkbox);
        wednesday = (CheckBox) findViewById(R.id.wednesday_checkbox);
        thursday = (CheckBox) findViewById(R.id.thursday_checkbox);
        friday = (CheckBox) findViewById(R.id.friday_checkbox);
        saturday = (CheckBox) findViewById(R.id.saturday_checkbox);
        sunday = (CheckBox) findViewById(R.id.sunday_checkbox);
        submitButton = (Button) findViewById(R.id.submit_task_button);

        TaskListDBHelper dbHelper = new TaskListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }

    public void onSubmitTaskButtonClicked(View button) {
        String nameString = name.getText().toString();
        String descriptionString = description.getText().toString();

        addAllTasksToDB(nameString, descriptionString);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private long addDayTaskToDB(int dayId, int taskId) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.DayTasksEntry.COLUMN_DAY_ID, dayId);
        cv.put(TaskListContract.DayTasksEntry.COLUMN_TASK_ID, taskId);

        return mDb.insert(TaskListContract.DayTasksEntry.TABLE_NAME, null, cv);
    }

    private Cursor getTasksFromDB(Task task) {
        String selection = TaskListContract.TasksEntry.COLUMN_NAME + " = ?";
        String[] selectionArgs = { task.getName() };
        return mDb.query(
                TaskListContract.TasksEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                TaskListContract.TasksEntry._ID + " DESC"
        );
    }

    private void addAllTasksToDB(String name, String description) {
        if (monday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(1, id);
        }

        if (tuesday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(2, id);
        }

        if (wednesday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(3, id);
        }

        if (thursday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(4, id);
        }

        if (friday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(5, id);
        }

        if (saturday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(6, id);
        }

        if (sunday.isChecked()) {
            Task task = new Task(name, description);
            task.addToDB(mDb);
            Cursor taskCursor = getTasksFromDB(task);
            taskCursor.moveToFirst();
            int id = taskCursor.getInt(taskCursor.getPosition());
            addDayTaskToDB(7, id);
        }
    }
}
