package com.example.todoapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.AddTask;
import com.example.todoapplication.MainActivity;
import com.example.todoapplication.Model.ToDoModel;
import com.example.todoapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private final List<ToDoModel> toDoModelList;
    private final MainActivity activity;
    private FirebaseFirestore firebaseFirestore;

    public ToDoAdapter(MainActivity mainActivity, List<ToDoModel> toDoModelList){
        this.toDoModelList = toDoModelList;
        activity = mainActivity;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.task, parent, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    public void deleteTask(int position){
        ToDoModel toDoModel = toDoModelList.get(position);
        firebaseFirestore.collection("task").document(toDoModel.TaskId).delete();
        toDoModelList.remove(position);
        notifyItemChanged(position);
    }

    public void editTask(int position){
        ToDoModel toDoModel = toDoModelList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task", toDoModel.getTask());
        bundle.putString("due", toDoModel.getDue());
        bundle.putString("id", toDoModel.TaskId);

        AddTask addTask = new AddTask();
        addTask.setArguments(bundle);
        addTask.show(activity.getSupportFragmentManager(), addTask.getTag());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull  ToDoAdapter.MyViewHolder holder, int position) {

        ToDoModel toDoModel = toDoModelList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());
        holder.mDueDate.setText("Due On "+toDoModel.getDue());

        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                firebaseFirestore.collection("task").document(toDoModel.TaskId).update("status", 1);

            }else {
                firebaseFirestore.collection("task").document(toDoModel.TaskId).update("status", 0);
            }
        });
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return toDoModelList.size();
    }

    public Context getContext() {
        return activity;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDate;
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);

            mDueDate = itemView.findViewById(R.id.due_date);
            mCheckBox = itemView.findViewById(R.id.check_box);
        }
    }
}
