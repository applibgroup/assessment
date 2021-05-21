package android.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.todoapp.Adapter.CustomAdapter;
import android.example.todoapp.Model.taskobject;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public  RecyclerView recyclerView;
    public FirebaseFirestore db;
    public List<taskobject> tasklist = new ArrayList<taskobject>();;
    public CustomAdapter cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cd  = new CustomAdapter(MainActivity.this,tasklist);
        recyclerView.setAdapter(cd);
        showData();
        initListener();
    }

    public void openactivity(View view){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

    private void initListener(){

        cd.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this,MainActivityupdate.class);
                taskobject tk = tasklist.get(position);
                intent.putExtra("title",tk.title);
                intent.putExtra("date",tk.date);
                intent.putExtra("time",tk.time);
                intent.putExtra("details",tk.details);
                intent.putExtra("addbutton",0);
                intent.putExtra("id", tk.id);
                startActivity(intent);
            }
        });


    }

    public void showData(){
        db.collection("task")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(tasklist.size()>0) tasklist.clear();
                db.collection("task").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc: task.getResult()){
                            taskobject tkobject = new taskobject(doc.getString("tasktitle"),doc.getString("taskdetails"),
                                    doc.getString("taskdate"),doc.getString("tasktime"),doc.getId());
                            tasklist.add(tkobject);
                        }
                        cd.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}