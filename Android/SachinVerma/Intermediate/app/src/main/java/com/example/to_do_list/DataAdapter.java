package com.example.to_do_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    public static ArrayList<Data> List;
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    // creating constructor for our adapter class
    public DataAdapter(ArrayList<Data> List, Context context) {
        DataAdapter.List = List;
        DataAdapter.context = context;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Data event = List.get(position);
        holder.a.setText(event.getTask());
        holder.b.setText(event.getDetails());
        holder.c.setText(event.getDate());
        holder.d.setText(event.getTime());
        holder.e.setText(event.getType());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return List.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView a;
        private final TextView b;
        private final TextView c;
        private final TextView d;
        private final TextView e;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            a = itemView.findViewById(R.id.TASK);
            b = itemView.findViewById(R.id.DETAILS);
            c = itemView.findViewById(R.id.DATE);
            d = itemView.findViewById(R.id.TIME);
            e = itemView.findViewById(R.id.TYPE);

            itemView.setOnClickListener(v -> {

                // after clicking of the item of recycler view.
                // we are passing our course object to the new activity.
                Data event = List.get(getAdapterPosition());

                // below line is creating a new intent.
                Intent i = new Intent(context, UpdateEvent.class);
                ArrayList<String> el = new ArrayList<>();
                el.add(event.getTask());
                el.add(event.getDetails());
                el.add(event.getDate());
                el.add(event.getTime());
                el.add(event.getType());
                el.add(event.getId());

                // below line is for putting our course object to our next activity.
//                i.putExtra("event", event);
                i.putStringArrayListExtra("event", el);

                // after passing the data we are starting our activity.
                context.startActivity(i);
            });
        }
    }
}
