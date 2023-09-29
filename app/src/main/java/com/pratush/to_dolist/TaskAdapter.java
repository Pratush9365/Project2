package com.pratush.to_dolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int resource;

    public TaskAdapter(Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Task task = getItem(position);


        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
        CheckBox checkBoxCompleted = convertView.findViewById(R.id.checkBoxCompleted);

        textViewDescription.setText(task.getDescription());
        checkBoxCompleted.setChecked(task.isCompleted());

        return convertView;
    }
}
