package com.applibgroup.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements ViewAdapter.OnViewClickListener {

    private String userID;
    public static ArrayList<Task> taskList = new ArrayList<>();
    private final int ADDITEM_REQUEST_CODE = 130;
    private final int DETAIL_REQUEST_CODE = 131;

    private static ViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(getApplicationContext());
        if(Paper.book().contains("id")){
            userID = Paper.book().read("id");
        } else {
            userID = UUID.randomUUID().toString();
            Paper.book().write("id",userID);
        }

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child(userID).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                findViewById(R.id.internet_connectivity).setVisibility(View.GONE);
                Log.d("FIREBASE OUTPUT", String.valueOf(snapshot.getChildrenCount()));
                ArrayList<Task> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(Task.class));
                    Log.d("FIREBASE OUTPUT", String.valueOf(dataSnapshot.getValue(Task.class).getTaskName()));
                }
                Collections.reverse(list);
                updateList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("REFRESHING", "onRefresh: REFRESH");
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        findViewById(R.id.fab_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddItem.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyler_view_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewAdapter(taskList,this);
        recyclerView.setAdapter(adapter);
    }

    public static void updateList(ArrayList<Task> list){
        taskList = list;
        adapter.updateList(taskList);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("TASK",taskList.get(position));
        startActivity(intent);
    }
}
