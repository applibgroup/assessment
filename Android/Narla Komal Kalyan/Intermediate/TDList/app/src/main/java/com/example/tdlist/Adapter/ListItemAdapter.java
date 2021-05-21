package com.example.tdlist.Adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdlist.MainActivity;
import com.example.tdlist.Model.ToDo;
import com.example.tdlist.R;

import java.util.List;

class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener
{
    ItemClickListner itemClickListner;
    TextView item_title,item_description;

    public ListItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        item_title = (TextView) itemView.findViewById(R.id.item_title);
        item_description = (TextView) itemView.findViewById(R.id.item_description);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAbsoluteAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,0,getAbsoluteAdapterPosition(),"DELETE");
    }
}

public class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    MainActivity mainActivity;
    List<ToDo> todoList;

    public ListItemAdapter(MainActivity mainActivity, List<ToDo> todoList) {
        this.mainActivity = mainActivity;
        this.todoList = todoList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        holder.item_title.setText(todoList.get(position).getTitle());
        holder.item_description.setText(todoList.get(position).getDescription());

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                mainActivity.title.setText(todoList.get(position).getTitle());
                mainActivity.description.setText(todoList.get(position).getDescription());

                mainActivity.isUpdate = true;
                mainActivity.idUpdate = todoList.get(position).getId();
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
