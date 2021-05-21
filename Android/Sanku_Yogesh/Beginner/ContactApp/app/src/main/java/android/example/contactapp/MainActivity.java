package android.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    contact o1 = new contact("name1","1999999991","name1name1@gmail.com", R.drawable.avatar_1);
    contact o2 = new contact("name2","2999999992","name2name2@gmail.com", R.drawable.avatar_2);
    contact o3 = new contact("name3","3999999993","name3name3@gmail.com", R.drawable.avatar_3);
    contact o4 = new contact("name4","4999999994","name4name4@gmail.com", R.drawable.avatar_4);
    contact o5 = new contact("name5","5999999995","name5name5@gmail.com", R.drawable.avatar_5);
    contact o6 = new contact("name6","6999999996","name6name6@gmail.com", R.drawable.avatar_6);
    contact o7 = new contact("name7","7999999997","name7name7@gmail.com", R.drawable.avatar_7);
    contact o8 = new contact("name8","8999999998","name8name8@gmail.com", R.drawable.avatar_8);
    contact[] arr = {o1,o2,o3,o4,o5,o6,o7,o8};
////    contact[] arr = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        customadapter cd = new customadapter(this, R.layout.customlayout,arr);
        listView.setAdapter(cd);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("yogeshcontactapp.name",arr[position].name);
                intent.putExtra("yogeshcontactapp.phone",arr[position].phone);
                intent.putExtra("yogeshcontactapp.emailid",arr[position].emailid);
                intent.putExtra("yogeshcontactapp.photoid",arr[position].photoid);
                startActivity(intent);
            }
        });
    }
}