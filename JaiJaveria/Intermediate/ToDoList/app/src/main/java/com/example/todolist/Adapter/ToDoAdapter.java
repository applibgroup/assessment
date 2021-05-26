package com.example.todolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoListModel;
import com.example.todolist.R;
import com.google.firebase.firestore.FirebaseFirestore;

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

    @Override
    public void onBindViewHolder(@NonNull   ToDoAdapter.MyViewHolder holder, int position) {
        ToDoListModel toDoListModel=todolist.get(position);
        String s=toDoListModel.getTaskDate()+"\n"+toDoListModel.getTaskTime();
        holder.datetime.setText(s);
        holder.name.setText(toDoListModel.getTaskName());
    }

    @Override
    public int getItemCount() {
        return todolist.size();
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
