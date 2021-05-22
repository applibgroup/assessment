package com.example.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.DocumentId.TaskModel;
import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<TaskModel> taskModelList;
    FirebaseFirestore db;

    public TaskAdapter(MainActivity context, List<TaskModel> taskModelList, FirebaseFirestore db) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.db = db;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
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
        ImageView updateTaskbtn;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.checkBoxTask);
            deleteBtn = itemView.findViewById(R.id.deleteTaskBtn);
            updateTaskbtn= itemView.findViewById(R.id.updateTaskBtn);
            db=FirebaseFirestore.getInstance();
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

            updateTaskbtn=itemView.findViewById(R.id.updateTaskBtn);
            updateTaskbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int currentPosition =  getAdapterPosition();
                    TaskModel taskModel = taskModelList.get(currentPosition);

                    String taskId = taskModel.getTaskId();
                    String prev=taskModel.getTask();

                    sendMessage(prev);

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
                                    Toast.makeText(context,"Failed to delete the task.",Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                private void sendMessage(String msg) {
                    Log.d("sender", "Broadcasting message");
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", msg);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });

//            db=FirebaseFirestore.getInstance();
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
                                    Toast.makeText(context,"Task deleted",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"Failed to delete the task.",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }
    }

}