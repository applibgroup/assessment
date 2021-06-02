package com.applibgroup.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class AddItem extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener date;
    private String taskStr;
    private String detail;
    private String dateStr;
    private String timeStr;
    private String type;
    private boolean update=false;

    private ArrayList<String> typesList = new ArrayList<String>(Arrays.asList("Travel", "Work", "Shopping", "Other"));
    private ArrayList<Integer> typesIDList = new ArrayList<Integer>(Arrays.asList(R.id.radio_button_1,R.id.radio_button_2,
            R.id.radio_button_3,R.id.radio_button_4));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final EditText dateEdittext= (EditText) findViewById(R.id.edit_text_add_date);
        final EditText Edit_Time = (EditText) findViewById(R.id.edit_text_add_time);

        if(getIntent().getSerializableExtra("UPDATE") != null){
            Task t = (Task) getIntent().getSerializableExtra("UPDATE");
            try {
                update = true;
                ((EditText)findViewById(R.id.edit_text_add_task)).setText(t.getTaskName());
                ((EditText)findViewById(R.id.edit_text_add_detail)).setText(t.getDetail());
                ((EditText)findViewById(R.id.edit_text_add_date)).setText(t.getDate());
                ((EditText)findViewById(R.id.edit_text_add_time)).setText(t.getTime());
                ((RadioButton)findViewById(typesIDList.get(typesList.indexOf(t.getType())))).setChecked(true);
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }

        final Calendar myCalendar = Calendar.getInstance();


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String day = String.valueOf(dayOfMonth);
                if(dayOfMonth<10)
                    day = "0"+day;

                String strDate = ""+dayOfMonth+"-"+monthOfYear+"-"+year;
                dateEdittext.setText(strDate);

            }

        };

        dateEdittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddItem.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Edit_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddItem.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour = String.valueOf(selectedHour);
                        String minute = String.valueOf(selectedMinute);
                        if(selectedHour<10)
                            hour = "0"+hour;
                        if(selectedMinute<10)
                            minute = "0"+minute;
                        Edit_Time.setText( hour + ":" + minute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        RadioGroup radioButtonGroup = findViewById(R.id.radio_button_group_type);
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        Button submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskStr = ((EditText)findViewById(R.id.edit_text_add_task)).getText().toString().trim();
                detail = ((EditText)findViewById(R.id.edit_text_add_detail)).getText().toString().trim();
                dateStr = ((EditText)findViewById(R.id.edit_text_add_date)).getText().toString().trim();
                timeStr = ((EditText)findViewById(R.id.edit_text_add_time)).getText().toString().trim();

                int selectedRadioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = ((RadioButton)findViewById(selectedRadioButtonID));

                if(taskStr.equals("") || detail.equals("") ||
                        dateStr.equals("") || timeStr.equals("") || selectedRadioButton == null )
                {
                    Toast.makeText(AddItem.this, "Enter all the Field", Toast.LENGTH_SHORT).show();
                } else {
                    type = selectedRadioButton.getText().toString().trim();
                    long timeStamp;

                    if(update) {
                        Task t = (Task) getIntent().getSerializableExtra("UPDATE");
                        timeStamp = t.getTimeStamp();
                    }else {
                        timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    }
                    Task task = new Task(taskStr, detail, dateStr, timeStr, type, timeStamp);

                    String timeStampStr = String.valueOf(task.getTimeStamp());

                    String userID = getUserID();

                    if(userID != null){
                        Intent intent = new Intent();
                        dbReference.child(userID).child(timeStampStr).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if(update) {
                                    intent.putExtra("STATUS","SUCCESS");
                                    intent.putExtra("TASK", task);
                                    setResult(Activity.RESULT_OK,intent);
                                }
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddItem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                    else {
                        Toast.makeText(AddItem.this, "Failed to add Task. Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putExtra("STATUS","CANCEL");
//                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }

    private String getUserID(){
        String uid;
        Paper.init(getApplicationContext());
        if(Paper.book().contains("id")){
            uid = Paper.book().read("id");
        } else {
            uid = UUID.randomUUID().toString();
            Paper.book().write("id",uid);
        }
        return uid;
    }

}
