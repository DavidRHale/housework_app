package com.codeclan.housework4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by David Hale on 20/03/2017.
 */

public class DayTasksAdapter extends ArrayAdapter<Task> {

    public DayTasksAdapter(Context context, ArrayList<Task> tasks) {
        super (context, 0, tasks);
    }

    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.day_tasks_item, parent, false);
        }

        Task currentTask = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.name);
        name.setText(currentTask.getName());
        name.setTag(currentTask);

        TextView completed = (TextView) listItemView.findViewById(R.id.completed);
        completed.setTag(currentTask);
        String isCompleted = "Not completed";
        if (currentTask.isCompleted() == true) {
            isCompleted = "Completed!";
        }
        completed.setText(isCompleted);


        return listItemView;
    }
}
