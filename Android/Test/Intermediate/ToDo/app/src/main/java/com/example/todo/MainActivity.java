package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FloatingActionButton fab;
    Button show;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<Model> list;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore=FirebaseFirestore.getInstance();

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();
        myAdapter=new MyAdapter(list);
        recyclerView.setAdapter(myAdapter);

        showData();


        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        show=findViewById(R.id.show_button);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });



    }

    public  void showData() {
        firebaseFirestore.collection("task").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                for(DocumentSnapshot snapshot:task.getResult()){
                    Model model=new Model(snapshot.getString("title"),snapshot.getString("description"),snapshot.getString("id"));
                    list.add(model);


                }
                myAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "FAiled in Fetching"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addTask() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View v=LayoutInflater.from(this).inflate(R.layout.activity_additem,null);
        builder.setView(v);

        AlertDialog dialog=builder.create();
        dialog.setCancelable(false);
        dialog.show();

        EditText title=v.findViewById(R.id.add_title);
        EditText desc=v.findViewById(R.id.add_desc);
        Button cancel=v.findViewById(R.id.cancel_button);
        Button save=v.findViewById(R.id.save_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String sTitle=title.getText().toString();
                String sDesc=desc.getText().toString();
                String id= UUID.randomUUID().toString();
                Log.d(TAG, "onClick: save "+sTitle);
                Log.d(TAG, "onClick: save "+id);

                if(sTitle.isEmpty()){
                    Toast.makeText(MainActivity.this, "Emplty title", Toast.LENGTH_SHORT).show();
                    title.setError("Enter Value");

                }

                else
                {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id",id);
                    map.put("title",sTitle);
                    map.put("description",sDesc);

                    firebaseFirestore.collection("task").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                showData();
                dialog.dismiss();
            }
        });




    }
}