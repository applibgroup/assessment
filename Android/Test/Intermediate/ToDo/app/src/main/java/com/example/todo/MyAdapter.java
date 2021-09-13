package com.example.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";


    List<Model> myList;
    String uTitle;
    String uDesc,uId;
    FirebaseFirestore db;

    public MyAdapter(List<Model> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(myList.get(position).title);
        holder.desc.setText(myList.get(position).desc);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uTitle=myList.get(position).title;
                uDesc=myList.get(position).desc;
                uId=myList.get(position).id;

                updateTask(v,uId,position);


            }
        });

    }

    private void updateTask(View v,String uId,int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
        View updateView=LayoutInflater.from(v.getContext()).inflate(R.layout.update,null);
        builder.setView(updateView);

        AlertDialog dialog=builder.create();
        dialog.setCancelable(false);
        dialog.show();

        EditText upTitle=updateView.findViewById(R.id.update_title);
        EditText upDesc=updateView.findViewById(R.id.update_desc);
        Button delete=updateView.findViewById(R.id.delete_button);
        Button update=updateView.findViewById(R.id.update_button);

        upTitle.setText(uTitle);
        upDesc.setText(uDesc);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFireStore(upTitle.getText().toString(),upDesc.getText().toString(),uId,v,dialog,position);


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(uId,v,dialog);
            }
        });





    }

    private void deleteItem(String uId,View v,AlertDialog d) {
        FirebaseFirestore db;
        db=FirebaseFirestore.getInstance();
        db.collection("task").document(uId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();

            }
        });
        d.dismiss();


    }

    private void updateFireStore(String tit,String des,String id,View v,AlertDialog d,int position) {
        FirebaseFirestore db;
        db=FirebaseFirestore.getInstance();
        db.collection("task").document(id).update("title",tit,"description",des).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(v.getContext(), "Updated", Toast.LENGTH_SHORT).show();
            }
        });
        d.dismiss();


    }



    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc;
        CardView card;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            desc = itemView.findViewById(R.id.desc_text);
            card = itemView.findViewById(R.id.card);
        }
    }
}
