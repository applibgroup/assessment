package com.example.intermediate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ToDoFragment extends Fragment {

    private EditText taskName;
    private Spinner dropdown;
    private EditText details  ;
    private EditText dateAndTime;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         Button AddTBtn;
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        taskName = view.findViewById(R.id.taskEditText);
        details=view.findViewById(R.id.detailsEditText);
        dateAndTime=view.findViewById(R.id.dateandtimeEditText);

         dropdown = view.findViewById(R.id.spinner1);
        String[] items = new String[]{"Travel", "Shopping", "Gym","Party","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(super.requireContext(), android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);
        dateAndTime.setInputType(InputType.TYPE_NULL);
        final Calendar[] date = new Calendar[1];
        dateAndTime.setOnClickListener(v -> {
            final Calendar currentDate = Calendar.getInstance();
            date[0] =  Calendar.getInstance();
            new DatePickerDialog(ToDoFragment.super.requireContext(), (view12, year, monthOfYear, dayOfMonth) -> {
                date[0].set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(ToDoFragment.super.getContext(), (view1, hourOfDay, minute) -> {
                    date[0].set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date[0].set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    dateAndTime.setText( dateFormat.format(date[0]));

                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        });

        AddTBtn=view.findViewById(R.id.addtransportBtn);

        AddTBtn.setOnClickListener(v -> performAddTransport());

        return view;
    }
    public void performAddTransport(){
        String task = taskName.getText().toString();
        String detail = details.getText().toString();
        String datetime=dateAndTime.getText().toString();
        String type = dropdown.getSelectedItem().toString();

        Map<String,Object> newToDo=new HashMap<>();
        newToDo.put("task",task);
        newToDo.put("detail",detail);
        newToDo.put("type",type);
        newToDo.put("datetime",datetime);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").add(newToDo).addOnSuccessListener(documentReference -> {
            Log.d("Tag","Inserted Successful");
            NavHostFragment.findNavController(ToDoFragment.this)
                          .navigate(R.id.action_SecondFragment_to_FirstFragment);
        })
                .addOnFailureListener(lambda -> Log.d("Tag","Error Failed"));


    }
}




