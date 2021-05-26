package com.example.contactfinal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private static final String TAG = "RecycleViewAdapter";
    private Context context;
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<String> numbers=new ArrayList<>();
    private ArrayList<Integer> images=new ArrayList<>();

    public RecycleViewAdapter(Context context, ArrayList<String> names, ArrayList<String> numbers, ArrayList<Integer> images) {
        this.context = context;
        this.names = names;
        this.numbers = numbers;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder =new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.image.setImageResource(images.get(position));
        holder.name.setText(names.get(position));
        holder.number.setText(numbers.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked"+names.get(position));
                //Toast.makeText(context,names.get(position) , Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(context,GalleryActivity.class);
                intent.putExtra("image",images.get(position));
                intent.putExtra("name",names.get(position));
                intent.putExtra("number",numbers.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name,number;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.image_name);
            number=itemView.findViewById(R.id.image_number);
            parentLayout=itemView.findViewById(R.id.parent_layout);

        }

    }
}
