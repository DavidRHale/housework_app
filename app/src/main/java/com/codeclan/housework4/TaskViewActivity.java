package com.codeclan.housework4;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codeclan.housework4.data.TaskListContract;
import com.codeclan.housework4.data.TaskListDBHelper;

import java.util.ArrayList;

public class TaskViewActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private CustomTextView nameView;
    private CustomTextView descriptionView;
    private CustomTextView completedView;
    private CustomTextView daysView;
    private CustomButton deleteButton;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        nameView = (CustomTextView) findViewById(R.id.item_name);
        descriptionView = (CustomTextView) findViewById(R.id.item_description);
        completedView = (CustomTextView) findViewById(R.id.item_completed);
        daysView = (CustomTextView) findViewById(R.id.item_days);
        deleteButton = (CustomButton) findViewById(R.id.delete_task_button);

        TaskListDBHelper dbHelper = new TaskListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        Long id = intent.getLongExtra("taskId", 0);

        Cursor taskCursor = getTaskCursorFromDB(id);
        task = generateTask(taskCursor);

        Cursor allTasksCursor = getAllTasksCursor();
        ArrayList<Integer> ids = getTaskIds(allTasksCursor);

        ArrayList<Cursor> daysCursors = getDaysCursors(ids);
        ArrayList<Day> days = generateDays(daysCursors);

        String isCompletedString = "Not completed";
        if (task.isCompleted() == true) {
            isCompletedString = "Completed!";
        }

        nameView.setText("Task Name: \n" + task.getName());
        descriptionView.setText("Description: \n" + task.getDescription());
        completedView.setText(isCompletedString);
        daysView.setText("Days: \n" + daysAsString(days));
    }

    private Cursor getTaskCursorFromDB(long id) {
        return mDb.query(
                TaskListContract.TasksEntry.TABLE_NAME,
                null,
                "_id=" + id,
                null,
                null,
                null,
                null
        );
    }

    private Task generateTask(Cursor cursor) {
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry.COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry.COLUMN_DESCRIPTION));
        boolean isCompleted = cursor.getLong(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry.COLUMN_IS_COMLETED)) > 0;
        long id = cursor.getLong(cursor.getColumnIndex(TaskListContract.TasksEntry._ID));
        return new Task(id, name, description, isCompleted);
    }

    public void onDeleteButtonClicked(View button) {
        long id = task.getId();
        String table = TaskListContract.TasksEntry.TABLE_NAME;
        String whereClause = TaskListContract.TasksEntry._ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        mDb.delete(table, whereClause, whereArgs);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDeleteAllButtonClicked(View button) {
        String name = task.getName();
        String description = task.getDescription();
        String table = TaskListContract.TasksEntry.TABLE_NAME;
        String whereClause = TaskListContract.TasksEntry.COLUMN_NAME + "=? AND " + TaskListContract.TasksEntry.COLUMN_DESCRIPTION + "=?" ;
        String[] whereArgs = new String[] { name, description };
        mDb.delete(table, whereClause, whereArgs);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private ArrayList<Cursor> getDaysCursors(ArrayList<Integer> ids) {
        ArrayList<Cursor> cursors = new ArrayList<>();
        for (Integer idInteger : ids) {
            int id = (int) idInteger;
            final String MY_QUERY = "SELECT " +
                    TaskListContract.DaysEntry.TABLE_NAME + ".* FROM " +
                    TaskListContract.DaysEntry.TABLE_NAME + " INNER JOIN " +
                    TaskListContract.DayTasksEntry.TABLE_NAME + " ON " +
                    TaskListContract.DaysEntry.TABLE_NAME + "." +
                    TaskListContract.DaysEntry._ID + " = " +
                    TaskListContract.DayTasksEntry.COLUMN_DAY_ID + " WHERE " +
                    TaskListContract.DayTasksEntry.COLUMN_TASK_ID + " = " + String.valueOf(id);
            Cursor cursor = mDb.rawQuery(MY_QUERY, null);
            cursors.add(cursor);
        }

        return cursors;
    }

    private ArrayList<Day> generateDays(ArrayList<Cursor> cursors) {
        ArrayList<Day> days = new ArrayList<>();

        for (Cursor cursor : cursors) {
            while (cursor.moveToNext()) {
                String dayName = cursor.getString(cursor.getColumnIndexOrThrow(TaskListContract.DaysEntry.COLUMN_DAY_NAME));
                long id = cursor.getLong(cursor.getColumnIndex(TaskListContract.DaysEntry._ID));
                days.add(new Day(id, dayName));
            }
        }

        return days;
    }

    private String daysAsString(ArrayList<Day> days) {
        String daysString = "";
        for (Day day : days) {
            daysString += day.getDayName() + " ";
        }
        return daysString;
    }

    private Cursor getAllTasksCursor() {
        String name = task.getName();
        String description = task.getDescription();
        String table = TaskListContract.TasksEntry.TABLE_NAME;
        String whereClause = TaskListContract.TasksEntry.COLUMN_NAME + "=? AND " + TaskListContract.TasksEntry.COLUMN_DESCRIPTION + "=?" ;
        String[] whereArgs = new String[] { name, description };
        return mDb.query(
                table,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }

    private ArrayList<Integer> getTaskIds(Cursor cursor) {
        ArrayList<Integer> ids = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer id = (Integer) cursor.getInt(cursor.getColumnIndexOrThrow(TaskListContract.TasksEntry._ID));
            ids.add(id);
        }
        return ids;
    }

}
