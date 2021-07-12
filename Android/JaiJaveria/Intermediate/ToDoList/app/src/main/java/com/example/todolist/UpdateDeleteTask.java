package com.example.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoListModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class UpdateDeleteTask extends BottomSheetDialogFragment {
    public static final String TAG = "UpdateDeleteTask";
    private ToDoAdapter toDoAdapter;
    private EditText taskName;
    private  EditText taskDesc;
    private TextView date;
    private TextView time;
    private Context context;
    private Button update;
    private Button delete;
    private FirebaseFirestore firestore;
    private String date_string="";
    private int hour;
    private int min;
    private String timeVal;
    int position;
    Bundle bundle;
    private String id="";

    public static UpdateDeleteTask newInstance(ToDoAdapter toDoAdapter) {

        UpdateDeleteTask fragment = new UpdateDeleteTask(toDoAdapter);
        return fragment;
    }
    public UpdateDeleteTask(ToDoAdapter toDoAdapter)
    {
        this.toDoAdapter=toDoAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_delete_task,container,false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskName=view.findViewById(R.id.name);
        taskDesc=view.findViewById(R.id.description);
        date=view.findViewById(R.id.date);
        time=view.findViewById(R.id.time);
        update=view.findViewById(R.id.xupdateTask);
        delete=view.findViewById(R.id.xdeleteTask);
        firestore=FirebaseFirestore.getInstance();

        bundle=getArguments();
        assert(bundle!=null);
        taskName.setText(bundle.getString("name"));
        taskDesc.setText(bundle.getString("desc"));
        date_string=bundle.getString("date");
        date.setText(date_string);
        timeVal=bundle.getString("time");
        time.setText(timeVal);
        id=bundle.getString("id");
        position=bundle.getInt("position");

        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                {
                    update.setEnabled(false);
                    update.setBackgroundColor(getResources().getColor(R.color.teal_100));
                }
                else
                {
                    update.setEnabled(true);
                    update.setBackgroundColor(getResources().getColor(R.color.teal_200));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to remove any keyboard that might we there. We do not need it
                date.setEnabled(false);
                date.setEnabled(true);
                Calendar calendar= Calendar.getInstance();
                int day=calendar.get(Calendar.DATE);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;
                        date_string=year+"-"+month+"-"+dayOfMonth;
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tname= taskName.getText().toString();
                if (tname.isEmpty() || date_string.isEmpty() || timeVal.isEmpty())
                {
                    Toast.makeText(context,"Please fill the date, time or name. One of them is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String tdesc=taskDesc.getText().toString();
                    Log.d("UpDel", "value of timeVal "+timeVal);
                    firestore.collection("task").document(id).update("taskName", tname, "taskDesc", tdesc,
                            "taskDate", date_string, "taskTime",timeVal);
                    ToDoListModel toDoListModel=new ToDoListModel(tname, tdesc, date_string,timeVal, id);
                    toDoAdapter.updateTaskList(position,toDoListModel);
                    toDoAdapter.notifyDataSetChanged();
                    dismiss();
                }


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UpDel delete", " Delete button Clicked");
                    AlertDialog.Builder builder=new AlertDialog.Builder(toDoAdapter.getContext());
                    builder.setMessage("Are you Sure")
                            .setTitle("Delete The Task")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    toDoAdapter.deleteTask(position);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .show();

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
