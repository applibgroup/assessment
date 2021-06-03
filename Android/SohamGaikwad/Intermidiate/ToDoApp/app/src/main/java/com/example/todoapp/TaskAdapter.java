package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<TaskModel> taskModelList;
    FirebaseFirestore db;

    public TaskAdapter(Context context, List<TaskModel> taskModelList, FirebaseFirestore db) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.db = db;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        CheckBox task = holder.task;

        TaskModel taskModel = taskModelList.get(position);
        String taskFromDB = taskModel.getTask();
        boolean isTaskDone = taskModel.isTaskDone();

        task.setText(taskFromDB);
        task.setChecked(isTaskDone);

    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        CheckBox task;
        ImageView deleteBtn;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.checkBoxTask);
            deleteBtn = itemView.findViewById(R.id.deleteTaskBtn);

            task.setOnCheckedChangeListener((buttonView, isChecked) -> {
                final int currentPosition =  getAdapterPosition();
                TaskModel taskModel = taskModelList.get(currentPosition);

                String taskId = taskModel.getTaskId();

                db.collection("todoCollection")
                        .document(taskId)
                        .update("taskDone",isChecked);
            });

            deleteBtn.setOnClickListener(v -> {
                final int currentPosition =  getAdapterPosition();
                TaskModel taskModel = taskModelList.get(currentPosition);

                String taskId = taskModel.getTaskId();

                db.collection("todoCollection")
                        .document(taskId).delete()
                        .addOnSuccessListener(aVoid -> {
                            taskModelList.remove(currentPosition);
                            notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete the task.", Toast.LENGTH_SHORT).show())
                ;
            });
        }
    }
}
