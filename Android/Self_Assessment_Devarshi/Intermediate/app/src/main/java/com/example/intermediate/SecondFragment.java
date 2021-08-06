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
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;


public class SecondFragment extends Fragment {
    private EditText taskName;
    private Spinner dropdown;
    private EditText details  ;
    private EditText dateAndTime;
    private ToDo t;
    private String id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void setToDo(ToDo t,String s)
    {
        this.id=s;
        this.t = t;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Button delete;
        Button update;
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        taskName = view.findViewById(R.id.taskEditText);
        details=view.findViewById(R.id.detailsEditText);
        dateAndTime=view.findViewById(R.id.dateandtimeEditText);
        dropdown = view.findViewById(R.id.spinner1);
        taskName.setText(t.getTask());
        details.setText(t.getDetails());
        dateAndTime.setText(t.getDatetime());

        String[] items = new String[]{"Travel", "Shopping", "Gym","Party","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(super.requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        Spinner s=dropdown;
        s.setSelection(adapter.getPosition(t.getType()));

        dateAndTime.setInputType(InputType.TYPE_NULL);
        final Calendar[] date = new Calendar[1];
        dateAndTime.setOnClickListener(v -> {
            final Calendar currentDate = Calendar.getInstance();
            date[0] =  Calendar.getInstance();
            new DatePickerDialog(SecondFragment.super.requireContext(), (view12, year, monthOfYear, dayOfMonth) -> {
                date[0].set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(SecondFragment.super.getContext(), (view1, hourOfDay, minute) -> {
                    date[0].set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date[0].set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    dateAndTime.setText( dateFormat.format(date[0]));

                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        });

        delete=view.findViewById(R.id.delete);
        update=view.findViewById(R.id.update);

        delete.setOnClickListener(v -> performDelete());
        update.setOnClickListener(v -> performUpdate());

        return view;
    }
    public void performDelete(){
        db.collection("todos").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    if(isAdded())
                    {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        FirstFragment someFragment = new FirstFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }

                })
                .addOnFailureListener(e -> Log.w("TAG", "Error deleting document", e));
    }
    public void performUpdate(){
        String task = taskName.getText().toString();
        String detail = details.getText().toString();
        String datetime=dateAndTime.getText().toString();
        String type = dropdown.getSelectedItem().toString();
        DocumentReference doc=db.collection("todos").document(id);
        doc.update("task",task);
        doc.update("detail",detail);
        doc.update("datetime",datetime);
        doc.update("type",type).addOnSuccessListener(unused -> {
            if(isAdded())
            {
                FirstFragment someFragment = new FirstFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }

        });
    }
}




