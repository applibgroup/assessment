package com.example.to_do_list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UpdateEvent extends AppCompatActivity {


    private EditText a,b,c,d;
    private String sa,sb,sc,sd;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        ArrayList<String> arr = getIntent().getStringArrayListExtra("event");
        Data event = new Data(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4));
        event.setId(arr.get(5));

        // getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        a = findViewById(R.id.Task);
        b = findViewById(R.id.Details);
        c = findViewById(R.id.Date);
        d = findViewById(R.id.Time);

        // creating variable for button
        Button B1 = findViewById(R.id.UpdateEvent);
        Button B2 = findViewById(R.id.DeleteEvent);

        a.setText(event.getTask());
        b.setText(event.getDetails());
        c.setText(event.getDate());
        d.setText(event.getTime());

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to delete the course.
                deleteEvent(event);
            }
        });


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sa = a.getText().toString();
                sb = b.getText().toString();
                sc = c.getText().toString();
                sd = d.getText().toString();


                updateEvent(event, sa, sb, sc, sd);
            }
        });
    }



    private void deleteEvent(Data event) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("Data").
                // after that we are getting the document
                // which we have to delete.
                        document(event.getId()).

                // after passing the document id we are calling
                // delete method to delete this document.
                        delete().
                // after deleting call on complete listener
                // method to delete this data.
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // inside on complete method we are checking
                        // if the task is success or not.
                        if (task.isSuccessful()) {
                            // this method is called when the task is success
                            // after deleting we are starting our MainActivity.
                            Toast.makeText(UpdateEvent.this, "Event has been deleted from Databse.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateEvent.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(UpdateEvent.this, "Fail to delete the event. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        finish();
    }

    private void updateEvent(Data event, String sa, String sb, String sc, String sd) {
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        Log.d("UpdateEvent", "sa : " + sa);
        Data UpdatedEvent = new Data(sa, sb, sc, sd, event.getType());

        // after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        // inside on failure method we are
// displaying a failure message.
        db.collection("Data").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(event.getId()).

                // after setting our document id we are
                // passing our whole object class to it.
                        set(UpdatedEvent).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(aVoid -> {
                    // on successful completion of this process
                    // we are displaying the toast message.
                    Toast.makeText(UpdateEvent.this, "Event has been updated..", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(UpdateEvent.this, "Fail to update the data..", Toast.LENGTH_SHORT).show());
        finish();
    }
}

