package com.codeclan.housework4;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.codeclan.housework4.data.TaskListContract;
import com.codeclan.housework4.data.TaskListDBHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by David Hale on 21/03/2017.
 */

public class ResetTaskService extends IntentService {

    private static SQLiteDatabase mDb;
    private static final String TAG = "ResetTaskService";
    private static final int POLL_INTERVAL = 1000 * 60;

    public static Intent newIntent(Context context) {
        return new Intent(context, ResetTaskService.class);
    }

    public ResetTaskService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int todayInt = findCurrentDay();
        int index = findIndex(todayInt);

        Cursor allDaysCursor = getAllDays();
        ArrayList<Day> days = generateArrayListOfDays(allDaysCursor);

        Day today = days.get(index);

        int todayId = (int) today.getId();

        Cursor tasksCursor = getTaskListForDay(todayId);
        ArrayList<Task> tasks = generateArrayListOfTasks(tasksCursor);

        setTasksToIncomplete(tasks);
    }

    public static void setServiceAlarm(Context context) {
        TaskListDBHelper dbHelper = new TaskListDBHelper(context);
        mDb = dbHelper.getWritableDatabase();

        Intent intent = ResetTaskService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int type = AlarmManager.RTC;
        long triggerTime = System.currentTimeMillis() + ResetTaskService.millisecondsToMidnight() + 4200000;
        long interval = POLL_INTERVAL;

        alarmManager.setInexactRepeating(type, triggerTime, interval, pendingIntent);
    }

    public static long millisecondsToMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long millisecondsToMidnight = (calendar.getTimeInMillis()-System.currentTimeMillis());
        return millisecondsToMidnight;
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

    public int findCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    public int findIndex(int todayInt) {
        switch (todayInt) {
            case 1:
                return 6;
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
        }
        return 8;
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

    public void setTasksToIncomplete(ArrayList<Task> tasks) {
        for (Task task : tasks) {
            if (task.isCompleted()) {
                task.changeCompletedStatus();
                long id = task.getId();
                boolean isCompleted = task.isCompleted();

                updateTaskOnDB(id, isCompleted);
            }
        }
    }

    private void updateTaskOnDB(long id, boolean isCompleted) {
        String strFilter = "_id=" + id;
        ContentValues cv = new ContentValues();
        cv.put(TaskListContract.TasksEntry.COLUMN_IS_COMLETED, isCompleted);
        mDb.update(TaskListContract.TasksEntry.TABLE_NAME, cv, strFilter, null);
    }



}
