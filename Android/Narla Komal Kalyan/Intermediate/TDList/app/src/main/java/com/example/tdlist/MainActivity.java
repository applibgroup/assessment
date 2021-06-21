package com.example.tdlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tdlist.Adapter.ListItemAdapter;
import com.example.tdlist.Model.ToDo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    List<ToDo> toDoList = new ArrayList<>();
    FirebaseFirestore db;

    RecyclerView listItem;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    public MaterialEditText title,description;
    public boolean isUpdate = false;
    public String idUpdate = "";

    ListItemAdapter adapter;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        dialog = new SpotsDialog(this);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            if(!isUpdate)
            {
                setData(Objects.requireNonNull(title.getText()).toString(), Objects.requireNonNull(description.getText()).toString());
            }
            else
            {
                updateData(Objects.requireNonNull(title.getText()).toString(), Objects.requireNonNull(description.getText()).toString());
                isUpdate = !isUpdate;
            }
        });

        listItem = findViewById(R.id.listTodo);
        listItem.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listItem.setLayoutManager(layoutManager);

        loadData();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("DELETE"))
            deleteItem(item.getOrder());
        return super.onContextItemSelected(item);
    }

    private void deleteItem(int index) {
        db.collection("ToDoList")
                .document(toDoList.get(index).getId())
                .delete()
                .addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show());

        db.collection("ToDoList")
                .document(toDoList.get(index).getId())
                .addSnapshotListener((documentSnapshot, e) -> loadData());
    }

    private void updateData(String title, String description) {
        db.collection("ToDoList").document(idUpdate)
                .update("title",title,"description",description)
                .addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, "Updated!", Toast.LENGTH_SHORT).show());

        db.collection("ToDoList").document(idUpdate)
                .addSnapshotListener((documentSnapshot, e) -> loadData());
    }

    private void setData(String title, String description) {
        String id = UUID.randomUUID().toString();
        Map<String,Object> todo = new HashMap<>();
        todo.put("id",id);
        todo.put("title",title);
        todo.put("description",description);

        db.collection("ToDoList").document(id)
                .set(todo).addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, "Added!", Toast.LENGTH_SHORT).show());

        db.collection("ToDoList").document(id)
                .addSnapshotListener((documentSnapshot, e) -> loadData());
    }

    private void loadData() {
        dialog.show();
        if(toDoList.size() > 0)
            toDoList.clear();
        db.collection("ToDoList")
                .get()
                .addOnCompleteListener(task -> {
                    for(DocumentSnapshot doc: Objects.requireNonNull(task.getResult()))
                    {
                        ToDo todo = new ToDo(doc.getString("id"), doc.getString("title"), doc.getString("description"));

                        boolean flag = true;
                        for (ToDo elem : toDoList) {
                            if (elem.getId().equals(todo.getId())) {
                                flag = false;
                                break;
                            }
                        }

                        if(flag)
                            toDoList.add(todo);
                    }
                    adapter = new ListItemAdapter(MainActivity.this, toDoList);
                    listItem.setAdapter(adapter);
                    dialog.dismiss();
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}