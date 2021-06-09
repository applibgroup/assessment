package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView events;
    private ArrayList<Data> List;
    private DataAdapter adapter;
    private FirebaseFirestore db;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events = findViewById(R.id.Event_list);
        pb = findViewById(R.id.ProgressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        List = new ArrayList<>();
        events.setHasFixedSize(true);
        events.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(List, this);
        events.setAdapter(adapter);

        db.collection("Data").get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d("MainActivity", "Entered to print data");
            if(!queryDocumentSnapshots.isEmpty()) {
                pb.setVisibility(View.GONE);
                java.util.List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d : list) {
                    Data c = new Data(d.getString("task"), d.getString("details"), d.getString("date"), d.getString("time"), d.getString("type"));
                    c.setId(d.getId());
                    List.add(c);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
        });
    }

    public void LaunchAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}