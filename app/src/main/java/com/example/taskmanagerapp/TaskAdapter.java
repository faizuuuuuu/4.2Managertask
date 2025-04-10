package com.example.taskmanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmanagerapp.TaskListFragment;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context ctx_for;
    private List<TaskDataModel> task_list_for;
    private TaskListFragment frag_for;

    public TaskAdapter(Context ctx_for, List<TaskDataModel> task_list_for, TaskListFragment frag_for) {
        this.ctx_for = ctx_for;
        this.task_list_for = task_list_for;
        this.frag_for = frag_for;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent_for, int viewType_for) {
        View view_for = LayoutInflater.from(ctx_for).inflate(R.layout.item_task, parent_for, false);
        return new TaskViewHolder(view_for);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder_for, int pos_for) {
        TaskDataModel task_for = task_list_for.get(pos_for);
        holder_for.title_text_for.setText(task_for.getVal_title());
        holder_for.desc_text_for.setText(task_for.getVal_desc());
        holder_for.due_text_for.setText("Due: " + task_for.getVal_due());

        holder_for.itemView.setOnClickListener(v -> frag_for.onTaskClicked(task_for));
    }

    @Override
    public int getItemCount() {
        return task_list_for.size();
    }

    public void updateTaskList(List<TaskDataModel> new_list_for) {
        this.task_list_for = new_list_for;
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title_text_for, desc_text_for, due_text_for;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text_for = itemView.findViewById(R.id.textTaskTitle);
            desc_text_for = itemView.findViewById(R.id.textTaskDesc);
            due_text_for = itemView.findViewById(R.id.textTaskDue);
        }
    }
}
