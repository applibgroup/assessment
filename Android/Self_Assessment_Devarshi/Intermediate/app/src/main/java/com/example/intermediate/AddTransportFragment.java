package com.example.intermediate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AddTransportFragment extends Fragment {

    private TransportViewModel galleryViewModel;

    private EditText TransName;
    private Spinner dropdown;
    private EditText From  ;
    private EditText Departure;
    DatePickerDialog picker;
    private Button AddTBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                (TransportViewModel) ViewModelProviders.of(this).get(TransportViewModel.class);
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        TransName = view.findViewById(R.id.transnameEditText);
        From=view.findViewById(R.id.fromEditText);
        Departure=(EditText)view.findViewById(R.id.sdateEditText);

         dropdown = view.findViewById(R.id.spinner1);
        String[] items = new String[]{"Travel", "Shopping", "Gym","Party","Others"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(super.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        Departure.setInputType(InputType.TYPE_NULL);
        final Calendar[] date = new Calendar[1];
        Departure.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar currentDate = Calendar.getInstance();
                date[0] =  Calendar.getInstance();
                new DatePickerDialog(AddTransportFragment.super.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date[0].set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(AddTransportFragment.super.getContext(), new TimePickerDialog.OnTimeSetListener() {
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

        AddTBtn=view.findViewById(R.id.addtransportBtn);

        AddTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAddTransport(v);
            }
        });

        return view;
    }
    public void performAddTransport(View view){
        String task = TransName.getText().toString();
        String detail = From.getText().toString();
        String datetime=Departure.getText().toString();
        String type = dropdown.getSelectedItem().toString();
        //  MainActivity.prefConfig.displayToast(type.getText().toString());

        Map<String,Object> newToDo=new HashMap<>();
        newToDo.put("task",task);
        newToDo.put("detail",detail);
        newToDo.put("type",type);
        newToDo.put("datetime",datetime);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos").add(newToDo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Tagy","Inserted Successgully");
                NavHostFragment.findNavController(AddTransportFragment.this)
                              .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("Tagy","Error Failed");
                    }
                });

        //Call<User> call= MainActivity.apiInterface.addTransport(MainActivity.prefConfig.readTripid().toString(),type,tname,from,to,departure,arrival,cost);
        //call.enqueue(new Callback<User>() {
        /*    @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok")) {

                    Intent intent = new Intent(AddTransportFragment.super.getContext(), HomeActivity.class);
                    startActivity(intent);

                }
                else
                {
                    MainActivity.prefConfig.displayToast(""+response.body().getResponse());
                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                MainActivity.prefConfig.displayToast(""+t);
            }
        });*/
    }
}




