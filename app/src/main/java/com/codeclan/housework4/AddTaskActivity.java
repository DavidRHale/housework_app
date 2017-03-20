package com.codeclan.housework4;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

    public void onSubmitButtonClicked() {
        String nameString = name.getText().toString();
        String descriptionString = description.getText().toString();

        Task task = new Task(nameString, descriptionString);
        task.addToDB(mDb);
    }
}
