package com.example.todoapplication;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.Adapter.ToDoAdapter;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {
    private final ToDoAdapter adapter;
    public TouchHelper(ToDoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setMessage("Are You Sure?")
                    .setTitle("Delete Task")
                    .setPositiveButton("Yes", (dialogInterface, i) -> adapter.deleteTask(position))
                    .setNegativeButton("No", (dialogInterface, i) -> adapter.notifyItemChanged(position));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            adapter.editTask(position);
        }
    }
}
