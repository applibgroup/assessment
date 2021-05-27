package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoListModel;
import com.example.todolist.R;
import com.example.todolist.UpdateDeleteTask;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoListModel> todolist;
    private MainActivity mainActivity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(MainActivity mainActivity, List<ToDoListModel> l)
    {
        this.mainActivity=mainActivity;
        todolist=l;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.task, parent, false);
        firestore=FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }
    public void deleteTask(int p)
    {
        ToDoListModel toDoListModel=todolist.get(p);
        Log.d("TodoAdapter DelTask", "Before deleting task from Firebase");
        firestore.collection("task").document(toDoListModel.taskID).delete();
        todolist.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, getItemCount());
    }
    public void editTask(int p)
    {
        ToDoListModel toDoListModel=todolist.get(p);
        Bundle bundle= new Bundle();
        bundle.putString("name",toDoListModel.getTaskName());
        bundle.putString("desc",toDoListModel.getTaskDesc());
        bundle.putString("date",toDoListModel.getTaskDate());
        bundle.putString("time",toDoListModel.getTaskTime());
        bundle.putString("id",toDoListModel.taskID);
        bundle.putInt("position",p);
//        AddNewTask addNewTask=new AddNewTask();
//        addNewTask.setArguments(bundle);
//        addNewTask.show(mainActivity.getSupportFragmentManager(), addNewTask.getTag());
        UpdateDeleteTask updateDeleteTask=new UpdateDeleteTask(this);
        updateDeleteTask.setArguments(bundle);
        updateDeleteTask.show(mainActivity.getSupportFragmentManager(), updateDeleteTask.getTag());

    }
    public void updateTaskList(int p, ToDoListModel toDoListModel)
    {
        todolist.set(p,toDoListModel);
        Collections.sort(todolist,MainActivity.sortOnDateAndTime);
    }
    @Override
    public void onBindViewHolder(@NonNull   ToDoAdapter.MyViewHolder holder, int position) {
        ToDoListModel toDoListModel=todolist.get(position);
        String s=toDoListModel.getTaskDate()+"\n"+toDoListModel.getTaskTime();
        holder.datetime.setText(s);
        holder.name.setText(toDoListModel.getTaskName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todolist.size();
    }

    public Context getContext() {
        return mainActivity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, datetime;

        public MyViewHolder(@NonNull   View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.xtaskname);
            datetime=itemView.findViewById(R.id.xdueDateTime);
        }
    }
}
