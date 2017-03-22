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

        String isCompletedString = "Not completed";
        if (task.isCompleted() == true) {
            isCompletedString = "Completed!";
        }

        nameView.setText(task.getName());
        descriptionView.setText(task.getDescription());
        completedView.setText(isCompletedString);
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

}
