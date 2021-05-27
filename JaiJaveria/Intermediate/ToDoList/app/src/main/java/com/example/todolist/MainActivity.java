package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoListModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity    {
    private RecyclerView recyclerView;
    private Button button;
    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    private List<ToDoListModel> list;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        button=findViewById(R.id.add_task);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        firestore=FirebaseFirestore.getInstance();
        list= new ArrayList<>();
        adapter=new ToDoAdapter(MainActivity.this,list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
        recyclerView.setAdapter(adapter);
        showData();
    }
    public static Comparator<ToDoListModel> sortOnDateAndTime= new Comparator<ToDoListModel>() {
        @Override
        public int compare(ToDoListModel o1, ToDoListModel o2) {
            String d1= o1.getTaskDate();

            String d2=o2.getTaskDate();
            if (d1.equals(d2))
            {
                return o1.getTaskTime().compareTo(o2.getTaskTime());

            }
            else
            {
//                String[] a1=d1.split("-");
//                String[] a2=d2.split("-");
//                int year1= Integer.parseInt(a1[2]);
//                int year2= Integer.parseInt(a2[2]);
//                if (year1!=year2)
//                {
//                    return (year1<year2) ? -1: 1;
//                }
//                int month1= Integer.parseInt(a1[1]);
//                int month2= Integer.parseInt(a2[1])
//                if (month2!=month1)
//                {
//                    return (month1<month2) ? -1: 1;
//                }
//                int day1=Integer.parseInt(a1[0]);
//                int day2=Integer.parseInt(a2[0]);
//                return (day1<day2) ? -1: 1;
                return d1.compareTo(d2);
            }

        }
    };
    private void showData()
    {
        firestore.collection("task").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges())
                {
                    String id=documentChange.getDocument().getId();
                    ToDoListModel toDoListModel=documentChange.getDocument().toObject(ToDoListModel.class).withID(id);

                    Log.d("ShowData MainActivity", "docChange type: "+documentChange.getType());
                    if (documentChange.getType()== DocumentChange.Type.ADDED)
                    {
//                        ToDoListModel toDoListModel=documentChange.getDocument().toObject(ToDoListModel.class).withID(id);
                        if (toDoListModel.getTaskTime()!=null)
                        {
                            list.add(toDoListModel);
                            adapter.notifyDataSetChanged();
                        }
                    }
//                    else if (documentChange.getType()== DocumentChange.Type.MODIFIED)
//                    {
//                        list.set(p,toDoListModel);
//                    }
                }
//                Collections.reverse(list);

//                public int sort()
                Collections.sort(list,sortOnDateAndTime);
            }
        });
    }

//    private Object sort() {
//    }
}