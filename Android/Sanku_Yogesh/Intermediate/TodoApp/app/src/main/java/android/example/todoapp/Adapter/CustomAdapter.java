package android.example.todoapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.example.todoapp.MainActivity;
import android.example.todoapp.Model.taskobject;
import android.example.todoapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<taskobject> localDataSet;
    public Context activity;
    private FirebaseFirestore firestore1;
    private OnItemClickListener onItemClickListener;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView texttitle;
        public TextView textdate;
        public TextView texttime;
        OnItemClickListener onItemClickListener;
//        public CheckBox checkBox;
        public ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            // Define click listener for the ViewHolder's View

            texttitle = (TextView) view.findViewById(R.id.tasktitle);
            textdate = (TextView) view.findViewById(R.id.date);
            texttime = (TextView) view.findViewById(R.id.time);
//            checkBox = view.findViewById(R.id.checkbox);
            view.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    //Initialize the dataset of the Adapter.
    public CustomAdapter(Context mainActivity, List<taskobject> dataSet) {
        this.activity = mainActivity;
        localDataSet = dataSet;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.temp, viewGroup, false);

        firestore1 = FirebaseFirestore.getInstance();

        return new ViewHolder(view,onItemClickListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        taskobject tkobject = localDataSet.get(position);
        viewHolder.texttitle.setText(tkobject.title);
        viewHolder.texttime.setText(tkobject.time);
        viewHolder.textdate.setText(tkobject.date);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
