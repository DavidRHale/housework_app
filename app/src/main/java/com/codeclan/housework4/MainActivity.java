package com.codeclan.housework4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Button todayButton;
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
        todayButton = (Button) findViewById(R.id.today_button);
        addTaskButton = (Button) findViewById(R.id.add_task_button);

        TaskListDBHelper dbHelper = new TaskListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor allDaysCursor = getAllDays();

        if (allDaysCursor.getCount() != 7) {
            addAllDays();
            allDaysCursor = getAllDays();
        }

        ResetTaskService.setServiceAlarm(this);
    }

    public void onMondayButtonClicked(View button) {
        onButtonClicked(button, 1);
    }

    public void onTuesdayButtonClicked(View button) {
        onButtonClicked(button, 2);
    }

    public void onWednesdayButtonClicked(View button) {
        onButtonClicked(button, 3);
    }

    public void onThursdayButtonClicked(View button) {
        onButtonClicked(button, 4);
    }

    public void onFridayButtonClicked(View button) {
        onButtonClicked(button, 5);
    }

    public void onSaturdayButtonClicked(View button) {
        onButtonClicked(button, 6);
    }

    public void onSundayButtonClicked(View button) {
        onButtonClicked(button, 7);
    }

    public void onButtonClicked(View button, int dayId) {
        Intent intent = new Intent(this, DayTasksActivity.class);
        intent.putExtra("dayID", dayId);
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

    public void onTodayButtonClicked(View button) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                onButtonClicked(mondayButton, 1);
                break;
            case Calendar.TUESDAY:
                onButtonClicked(tuesdayButton, 2);
                break;
            case Calendar.WEDNESDAY:
                onButtonClicked(wednesdayButton, 3);
                break;
            case Calendar.THURSDAY:
                onButtonClicked(thursdayButton, 4);
                break;
            case Calendar.FRIDAY:
                onButtonClicked(fridayButton, 5);
                break;
            case Calendar.SATURDAY:
                onButtonClicked(saturdayButton, 6);
                break;
            case Calendar.SUNDAY:
                onButtonClicked(sundayButton, 7);
                break;
        }
    }

//    public void setAlarm() {
//        String alarm = Context.ALARM_SERVICE;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(alarm);
//
//        Intent intent = new Intent( "RESET_TASKS" );
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//        int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
//        long interval = AlarmManager.INTERVAL_DAY;
//        long triggerTime = System.currentTimeMillis() + millisecondsToMidnight() + 500000;
//
//        alarmManager.setRepeating(type, triggerTime, interval, pendingIntent);
//    }
//
//    public long millisecondsToMidnight() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long millisecondsToMidnight = (calendar.getTimeInMillis()-System.currentTimeMillis());
//        return millisecondsToMidnight;
//    }
}
