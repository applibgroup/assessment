package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private ListView listView;

    private String ContactNames[] = {
            "Amit Shah",
            "Biden",
            "Dalai Lama",
            "Jacinda",
            "Jinping",
            "N Modi",
            "V Putin",
            "Donald Trump"
    };

    private String Email[] = {
            "Amit@india",
            "Biden@us",
            "Dalai@tibet",
            "Jacinda@nz",
            "Jinping@ch",
            "Modi@india",
            "Putin@russia",
            "Trump@us"
    };

    private Integer imageid[] = {
            R.drawable.amit,
            R.drawable.biden,
            R.drawable.dalai,
            R.drawable.jacinda,
            R.drawable.jinping,
            R.drawable.modi,
            R.drawable.putin,
            R.drawable.trump
    };

    private String number[] = {
            "127773",
            "155552",
            "12388",
            "15216",
            "12311",
            "15254",
            "12886",
            "14554"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView=(ListView)findViewById(R.id.listview);

        CustomContactList customContactList = new CustomContactList(this, ContactNames, imageid);
        listView.setAdapter(customContactList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getApplicationContext(),"You Selected "+ContactNames[position] + "",Toast.LENGTH_SHORT).show();
                Bundle extras = new Bundle();
                extras.putString("ContactName",ContactNames[position]);
                extras.putString("imageid", imageid[position].toString());
                extras.putString("number", number[position]);
                extras.putString("Email",Email[position]);
                //extras.putIntArray("USER_SELCTIONS", [1, 2, 3, 4, 5]);
                Intent intent = new Intent(view.getContext(), details.class);
                intent.putExtras(extras);
                //intent.putExtra("id",position);
                startActivity(intent);
                }
            });
    }

}
