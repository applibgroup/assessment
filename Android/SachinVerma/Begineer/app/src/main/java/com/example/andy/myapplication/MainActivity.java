package com.example.andy.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
//    public static final String table = "contacts";
    public static final String a = "pname";
    public static final String b = "phone_no";
    public static final String c = "id";
    public static final String d = "image_link";
    DBHelper mydb = new DBHelper(this);
    ListView listView;
    ArrayList<SubjectData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
//        read_data();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        list.clear();
        read_data();
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void read_data() {
        SQLiteDatabase db = mydb.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
//        String[] projection = {
//                BaseColumns._ID,
//                a,
//                b,
//                c
//        };

// Filter results WHERE "title" = 'My Title'
////        String selection = null;
//        String[] selectionArgs = {""};
//
//// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                a + " ASC";

        Cursor cursor = db.rawQuery("SELECT * FROM contacts ORDER BY pname ASC", null);
        Log.d(LOG_TAG, "no of data in cursor is " + cursor.getCount());

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String i = cursor.getString(cursor.getColumnIndexOrThrow(a));
                String j = cursor.getString(cursor.getColumnIndexOrThrow(b));
                String k = cursor.getString(cursor.getColumnIndexOrThrow(d));
                String l = cursor.getString(cursor.getColumnIndexOrThrow(c));
                SubjectData data = new SubjectData(i, j, l, k);
//                Log.d(LOG_TAG, "name is " + data.SubjectName);
//                Log.d(LOG_TAG, "image is " + data.Image);
                list.add(data);
            }
            cursor.close();

            if(!list.isEmpty()) {
                CustomAdapter customAdapter = new CustomAdapter(this, list);
                listView.setAdapter(customAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(LOG_TAG, "contact is clicked");
                        String par1 = list.get(position).SubjectName;
                        String par2 = list.get(position).Number;
                        String par3 = list.get(position).Id;
                        String par4 = list.get(position).Image;

                        Log.d(LOG_TAG, "name : " + par1);
                        Log.d(LOG_TAG, "no : " + par2);
                        Log.d(LOG_TAG, "id : " + par3);
                        Log.d(LOG_TAG, "image : " + par4);

                        ArrayList<String> List = new ArrayList<>();
                        List.add(par1);
                        List.add(par2);
                        List.add(par3);
                        List.add(par4);

                        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                        intent.putStringArrayListExtra("data", List);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
