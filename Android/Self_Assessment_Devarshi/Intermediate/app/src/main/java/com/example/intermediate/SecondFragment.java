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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intermediate.R;
import com.example.intermediate.TransportViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class SecondFragment extends Fragment {

    private TransportViewModel galleryViewModel;

    private EditText TransName;
    private Spinner dropdown;
    private EditText From  ;
    private EditText Departure;
    DatePickerDialog picker;
    private Button delete;
    private Button update;
    private ToDo t;
    private String id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void setToDo(ToDo t,String s)
    {
        this.id=s;
        this.t = t;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                (TransportViewModel) ViewModelProviders.of(this).get(TransportViewModel.class);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TransName = view.findViewById(R.id.transnameEditText);
        From=view.findViewById(R.id.fromEditText);
        Departure=(EditText)view.findViewById(R.id.sdateEditText);
        Bundle args = getArguments();
        dropdown = view.findViewById(R.id.spinner1);
        TransName.setText(t.getTask());
        From.setText(t.getDetails());
        Departure.setText(t.getDatetime());

        String[] items = new String[]{"Travel", "Shopping", "Gym","Party","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(super.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        Spinner s=view.findViewById(R.id.spinner1);
        s.setSelection(adapter.getPosition(t.getType()));

        Departure.setInputType(InputType.TYPE_NULL);
        final Calendar[] date = new Calendar[1];
        Departure.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar currentDate = Calendar.getInstance();
                date[0] =  Calendar.getInstance();
                new DatePickerDialog(SecondFragment.super.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date[0].set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(SecondFragment.super.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date[0].set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date[0].set(Calendar.MINUTE, minute);
                                android.text.format.DateFormat df = new android.text.format.DateFormat();
                                SimpleDateFormat dateFormat = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                Departure.setText( dateFormat.format(date[0]));

                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
            }

        });

        delete=view.findViewById(R.id.delete);
        update=view.findViewById(R.id.update);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performDelete(v);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performUpdate(v);
            }
        });

        return view;
    }
    public void performDelete(View view){
        db.collection("todos").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        FirstFragment someFragment = new FirstFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, someFragment); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }
    public void performUpdate(View view){
        String task = TransName.getText().toString();
        String detail = From.getText().toString();
        String datetime=Departure.getText().toString();
        String type = dropdown.getSelectedItem().toString();
        DocumentReference doc=db.collection("todos").document(id);
        doc.update("task",task);
        doc.update("detail",detail);
        doc.update("datetime",datetime);
        doc.update("type",type).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                FirstFragment someFragment = new FirstFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

    }

}




