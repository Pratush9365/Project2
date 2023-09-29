package com.pratush.to_dolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends Activity {
    private EditText editTextTask;
    private Button buttonAddTask;
    private ListView listViewTasks;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskListAdapter;
    private Stack<Task> deletedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        ListView listView = findViewById(R.id.listViewTasks); // Replace with your ListView ID
        TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.task_item, taskList);
        listView.setAdapter(taskAdapter);


        deletedTasks = new Stack<>();

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskList.get(position);
                task.toggleCompleted();
                taskListAdapter.notifyDataSetChanged();
            }
        });

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskList.get(position);
                taskList.remove(position);
                deletedTasks.push(task);
                taskListAdapter.notifyDataSetChanged();
                showUndoSnackbar();
                return true;
            }
        });
    }

    public void addTask(View view) {
        String taskDescription = editTextTask.getText().toString();
        if (!taskDescription.isEmpty()) {
            Task task = new Task(taskDescription, false);
            taskList.add(task);
            taskListAdapter.notifyDataSetChanged();
            editTextTask.getText().clear();
        }
    }

    private void showUndoSnackbar() {
        Toast.makeText(this, "Task deleted. Undo?", Toast.LENGTH_LONG).show();
    }

    public void undoDelete(View view) {
        if (!deletedTasks.isEmpty()) {
            Task task = deletedTasks.pop();
            taskList.add(task);
            taskListAdapter.notifyDataSetChanged();
        }
    }
}

