package com.example.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<models> taskModelList;
    FirebaseFirestore db;
    public TaskAdapter(Context context, List<models> taskModelList, FirebaseFirestore db) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.db = db;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        CheckBox task = holder.task;
        TextView date = holder.date;
        TextView time = holder.time;
        models taskModel = taskModelList.get(position);
        String taskFromDB = taskModel.getTask();
        String dateFromDB = taskModel.getDate();
        String timeFromDB = taskModel.getTime();
        boolean isTaskDone = taskModel.isTaskDone();
        time.setText(timeFromDB);
        date.setText(dateFromDB);
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
        ImageView updateb;
        TextView date;
        TextView time;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.checkBoxTask);
            deleteBtn = itemView.findViewById(R.id.deleteTaskBtn);
            updateb = itemView.findViewById(R.id.updatebtn);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

            task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final int currentPosition =  getAdapterPosition();
                    models taskModel = taskModelList.get(currentPosition);

                    String taskId = taskModel.getTaskId();

                    db.collection("todoCollection")
                            .document(taskId)
                            .update("taskDone",isChecked);
                }
            });
            updateb.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                final int currentPosition =  getAdapterPosition();
                models taskModel = taskModelList.get(currentPosition);

                String taskId = taskModel.getTaskId();


                    String newTask = taskModel.getTask().toString();
                    String newDate = taskModel.getDate().toString();
                    String newTime = taskModel.getTime().toString();

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


                    Intent intent = new Intent("msg");
                    intent.putExtra("newTask",newTask);
                    intent.putExtra("newDate",newDate);
                    intent.putExtra("newTime",newTime);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    //context.startActivity(intent);
            }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int currentPosition =  getAdapterPosition();
                    models taskModel = taskModelList.get(currentPosition);

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