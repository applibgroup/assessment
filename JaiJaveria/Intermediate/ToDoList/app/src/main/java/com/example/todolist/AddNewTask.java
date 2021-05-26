package com.example.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
//import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";
    private EditText taskName;
    private  EditText taskDesc;
    private TextView date;
    private TextView time;
    private Context context;
    private Button save;
    private FirebaseFirestore firestore;
    private String date_string="";
    private int hour;
    private int min;
    private String timeVal;
    public static AddNewTask newInstance() {
        
//        Bundle args = new Bundle();
        
        AddNewTask fragment = new AddNewTask();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
//    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task,container,false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskName=view.findViewById(R.id.name);
        taskDesc=view.findViewById(R.id.description);
        date=view.findViewById(R.id.date);
        time=view.findViewById(R.id.time);
        save=view.findViewById(R.id.saveTask);
        firestore=FirebaseFirestore.getInstance();

        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                {
                    save.setEnabled(false);
                    save.setBackgroundColor(getResources().getColor(R.color.teal_100));
                }
                else
                {
                    save.setEnabled(true);
                    save.setBackgroundColor(getResources().getColor(R.color.teal_200));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int day=calendar.get(Calendar.DATE);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;
                        date_string=dayOfMonth+"-"+month+"-"+year;
                        date.setText(date_string);

                    }
                },  year,month, day);
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour=hourOfDay;
                        min=minute;
                        if(min<10)
                        {
                            timeVal=hour+":0"+min;
                        }
                        else
                        {
                            timeVal=hour+":"+min;
                        }

                        time.setText(timeVal);
                    }
                },12,0,false);
                timePickerDialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tname= taskName.getText().toString();
                if (tname.isEmpty())
                {
                    Toast.makeText(context,"Empty String Not Allowed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String tdesc=taskDesc.getText().toString();
                    Map<String, Object> valT=new HashMap<>();
                    valT.put("taskName", tname);
                    valT.put("taskDesc", tdesc);
                    valT.put("taskDate", date_string);
                    valT.put("taskTime", timeVal);
                    firestore.collection("task").add(valT).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                dismiss();

            }
        });



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if (activity instanceof onDialogClose)
        {
            ((onDialogClose)activity).onDiaClose(dialog);
        }
    }
}
