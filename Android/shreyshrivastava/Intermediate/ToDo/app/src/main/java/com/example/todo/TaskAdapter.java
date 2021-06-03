package com.example.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.logging.Logger;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<TaskModel> taskModelList;
    FirebaseFirestore db;
    EditText editTextTask;

    public TaskAdapter(Context context, List<TaskModel> taskModelList, FirebaseFirestore db, EditText editTextTask) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.db = db;
        this.editTextTask = editTextTask;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler, parent, false);
        return new TaskViewHolder(view, editTextTask);
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
        ImageView updateBtn;
        EditText editTextTask;

        private static final String TAG = "MyActivity";


        public TaskViewHolder(@NonNull View itemView, EditText editTextTask) {

            super(itemView);

            this.editTextTask = editTextTask;
            task = itemView.findViewById(R.id.checkBoxTask);
            deleteBtn = itemView.findViewById(R.id.deleteTaskBtn);
            updateBtn = itemView.findViewById(R.id.updateTaskBtn);

            task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final int currentPosition =  getAdapterPosition();
                    TaskModel taskModel = taskModelList.get(currentPosition);

                    String taskId = taskModel.getTaskId();

                    db.collection("todoCollection")
                            .document(taskId)
                            .update("taskDone",isChecked);
                }
            });

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int currentPosition =  getAdapterPosition();
                    TaskModel taskModel = taskModelList.get(currentPosition);

                    String taskId = taskModel.getTaskId();
                    String task = taskModel.getTask();

                    editTextTask.setText(task);
                    editTextTask.setSelection(editTextTask.getText().length());

                    db.collection("todoCollection")
                            .document(taskId).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    taskModelList.remove(currentPosition);
                                    notifyDataSetChanged();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to delete the task.", Toast.LENGTH_SHORT).show();
                                }
                            })
                    ;


                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int currentPosition =  getAdapterPosition();
                    TaskModel taskModel = taskModelList.get(currentPosition);

                    String taskId = taskModel.getTaskId();

                    db.collection("todoCollection")
                            .document(taskId).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    taskModelList.remove(currentPosition);
                                    notifyDataSetChanged();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to delete the task.", Toast.LENGTH_SHORT).show();
                                }
                            })
                    ;




                }
            });
        }
    }
}