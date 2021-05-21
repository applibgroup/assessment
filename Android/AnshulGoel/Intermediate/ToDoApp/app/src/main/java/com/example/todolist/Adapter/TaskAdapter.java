package com.example.todolist.Adapter;

import android.app.Dialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.DialBox;
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

            updateTaskbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int currentPosition =  getAdapterPosition();
                    TaskModel taskModel = taskModelList.get(currentPosition);
                    String val= currentPosition+"";
                    String temp = taskModel.getTaskId();
                    Intent nn=new Intent(context, DialBox.class);
                    nn.putExtra("CurrentTaskId",temp);
                    nn.putExtra("second",val);
                    context.startActivity(nn);
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