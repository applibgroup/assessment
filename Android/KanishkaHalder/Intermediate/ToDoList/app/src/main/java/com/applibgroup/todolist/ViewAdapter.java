package com.applibgroup.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private ArrayList<Task> taskList;
    private OnViewClickListener clickListener;

    public ViewAdapter(ArrayList<Task> dataList, OnViewClickListener onViewClickListener ){
        this.taskList = dataList;
        this.clickListener = onViewClickListener;
    }

    public void updateList(ArrayList<Task> list){
        this.taskList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_item_layout,parent,false);
        return new ViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTaskName());

        String dateStr = task.getDate();
        int month = Integer.parseInt(dateStr.substring(dateStr.indexOf('-')+1,dateStr.indexOf('-',3)));
        month = month-1;
        dateStr = dateStr.substring(0,2);
        String monthString = new DateFormatSymbols().getMonths()[month];
        dateStr = dateStr +" " + monthString;

        holder.date.setText(dateStr);
        holder.time.setText(task.getTime());

        switch (task.getType()){
            case "Travel":
                holder.imageView.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_around));
                break;
            case "Work":
                holder.imageView.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_suitcase));
                break;
            case "Shopping":
                holder.imageView.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_shopping_cart));
                break;
            default:
                holder.imageView.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_clipboards));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title;
        TextView date;
        TextView time;

        OnViewClickListener viewClickListener;

        public ViewHolder(@NonNull View itemView, OnViewClickListener onViewClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view_type_image);
            title = itemView.findViewById(R.id.text_view_title);
            date = itemView.findViewById(R.id.text_view_date);
            time = itemView.findViewById(R.id.text_view_time);
            this.viewClickListener = onViewClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            viewClickListener.onListItemClick(getAdapterPosition());
        }
    }
    public interface OnViewClickListener {
        void onListItemClick(int position);
    }
}
