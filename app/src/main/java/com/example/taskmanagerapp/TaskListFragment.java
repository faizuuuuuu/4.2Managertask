package com.example.taskmanagerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerapp.DatabaseHelper;
import com.example.taskmanagerapp.TaskAdapter;
import com.example.taskmanagerapp.TaskDataModel;

import java.util.ArrayList;
import java.util.List;


public class TaskListFragment extends Fragment {

    private EditText input_title, input_desc, input_due;
    private Button btn_add, btn_update, btn_delete;
    private RecyclerView recycler_for;
    private TaskAdapter adapter_for;
    private List<TaskDataModel> list_for_tasks;
    private DatabaseHelper db_for;
    private TaskDataModel task_selected;

    @Override
    public View onCreateView(LayoutInflater inflater_for, ViewGroup container_for,
                             Bundle savedInstanceState) {
        View root_for = inflater_for.inflate(R.layout.fragment_task_list, container_for, false);

        input_title = root_for.findViewById(R.id.inputTitle);
        input_desc = root_for.findViewById(R.id.inputDesc);
        input_due = root_for.findViewById(R.id.inputDueDate);
        btn_add = root_for.findViewById(R.id.btnAdd);
        btn_update = root_for.findViewById(R.id.btnUpdate);
        btn_delete = root_for.findViewById(R.id.btnDelete);
        recycler_for = root_for.findViewById(R.id.recyclerTasks);

        db_for = new DatabaseHelper(getContext());

        list_for_tasks = db_for.getAllTasks();
        adapter_for = new TaskAdapter(getContext(), list_for_tasks, this);
        recycler_for.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_for.setAdapter(adapter_for);

        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);

        // Pick a date
        input_due.setOnClickListener(v -> showDatePicker());

        btn_add.setOnClickListener(v -> {
            String val_title = input_title.getText().toString().trim();
            String val_desc = input_desc.getText().toString().trim();
            String val_due = input_due.getText().toString().trim();

            if (TextUtils.isEmpty(val_title) || TextUtils.isEmpty(val_due)) {
                Toast.makeText(getContext(), "Title and due date required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (task_selected == null) {
                db_for.insertTask(val_title, val_desc, val_due);
                clearInputs();
                refreshTaskList();
            } else {
                resetSelection();
            }
        });

        btn_update.setOnClickListener(v -> {
            if (task_selected != null) {
                String new_title = input_title.getText().toString();
                String new_desc = input_desc.getText().toString();
                String new_due = input_due.getText().toString();
                db_for.updateTask(task_selected.getVal_id(), new_title, new_desc, new_due);
                resetSelection();
                refreshTaskList();
            }
        });

        btn_delete.setOnClickListener(v -> {
            if (task_selected != null) {
                db_for.deleteTask(task_selected.getVal_id());
                resetSelection();
                refreshTaskList();
            }
        });

        return root_for;
    }

    public void onTaskClicked(TaskDataModel task_for) {
        task_selected = task_for;
        input_title.setText(task_for.getVal_title());
        input_desc.setText(task_for.getVal_desc());
        input_due.setText(task_for.getVal_due());
        btn_add.setText("Cancel");
        btn_update.setEnabled(true);
        btn_delete.setEnabled(true);
    }

    private void refreshTaskList() {
        list_for_tasks = db_for.getAllTasks();
        adapter_for.updateTaskList(list_for_tasks);
    }

    private void resetSelection() {
        task_selected = null;
        clearInputs();
        btn_add.setText("Add");
        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);
    }

    private void clearInputs() {
        input_title.setText("");
        input_desc.setText("");
        input_due.setText("");
    }

    private void showDatePicker() {
        Calendar cal_for = Calendar.getInstance();
        int year_for = cal_for.get(Calendar.YEAR);
        int month_for = cal_for.get(Calendar.MONTH);
        int day_for = cal_for.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(getContext(), (view, year, month, day) -> {
            String date_for = year + "-" + (month + 1) + "-" + day;
            input_due.setText(date_for);
        }, year_for, month_for, day_for).show();
    }
}