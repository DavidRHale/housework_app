package com.codeclan.housework4;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.codeclan.housework4.data.*;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    private Button mondayButton;
    private Button tuesdayButton;
    private Button wednesdayButton;
    private Button thursdayButton;
    private Button fridayButton;
    private Button saturdayButton;
    private Button sundayButton;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mondayButton = (Button) findViewById(R.id.monday_button);
        tuesdayButton = (Button) findViewById(R.id.tuesday_button);
        wednesdayButton = (Button) findViewById(R.id.wednesday_button);
        thursdayButton = (Button) findViewById(R.id.thursday_button);
        fridayButton = (Button) findViewById(R.id.friday_button);
        saturdayButton = (Button) findViewById(R.id.saturday_button);
        sundayButton = (Button) findViewById(R.id.sunday_button);
        addTaskButton = (Button) findViewById(R.id.add_task_button);

        TaskListDBHelper dbHelper = new TaskListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor allDaysCursor = getAllDays();

        if (allDaysCursor.getCount() != 7) {
            addAllDays();
        }
    }

    public void onMondayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 1);
        startActivity(intent);
    }

    public void onTuesdayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 2);
        startActivity(intent);
    }

    public void onWednesdayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 3);
        startActivity(intent);
    }

    public void onThursdayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 4);
        startActivity(intent);
    }

    public void onFridayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 5);
        startActivity(intent);
    }

    public void onSaturdayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 6);
        startActivity(intent);
    }

    public void onSundayButtonClicked(View button) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", 7);
        startActivity(intent);
    }

    private long addDayToDB(String dayName) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.DaysEntry.COLUMN_DAY_NAME, dayName);

        return mDb.insert(TaskListContract.DaysEntry.TABLE_NAME, null, cv);
    }

    private long addDayTaskToDB(int dayId, int taskId) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.DayTasksEntry.COLUMN_DAY_ID, dayId);
        cv.put(TaskListContract.DayTasksEntry.COLUMN_TASK_ID, taskId);

        return mDb.insert(TaskListContract.DayTasksEntry.TABLE_NAME, null, cv);
    }

    private void addAllDays() {
        addDayToDB("Monday");
        addDayToDB("Tuesday");
        addDayToDB("Wednesday");
        addDayToDB("Thursday");
        addDayToDB("Friday");
        addDayToDB("Saturday");
        addDayToDB("Sunday");
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

    private long addTaskToDB(String name, String description) {
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.TasksEntry.COLUMN_NAME, name);
        cv.put(TaskListContract.TasksEntry.COLUMN_DESCRIPTION, description);
        cv.put(TaskListContract.TasksEntry.COLUMN_IS_COMLETED, false);

        return mDb.insert(TaskListContract.TasksEntry.TABLE_NAME, null, cv);
    }

    public void onAddTaskButtonClicked(View button) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }
}
