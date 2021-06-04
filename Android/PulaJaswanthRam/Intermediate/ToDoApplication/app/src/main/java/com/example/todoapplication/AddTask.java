package com.example.todoapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.todoapplication.R.color.teal_200;

public class AddTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddTask";

    private TextView set_due_date;
    private EditText editText;
    private Button save_btn;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private String dueDate = "";
    private String id = "";

    public static AddTask newInstance(){
        return new AddTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        set_due_date = view.findViewById(R.id.set_due_tv);
        editText = view.findViewById(R.id.edit_text);
        save_btn = view.findViewById(R.id.save_button);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            id = bundle.getString("id");
            String dueDateUpdate = bundle.getString("due");

            editText.setText(task);
            set_due_date.setText(dueDateUpdate);

            if (task.length() > 0){
                save_btn.setEnabled(false);
                save_btn.setBackgroundColor(Color.GRAY);
            }
        }

        firebaseFirestore = FirebaseFirestore.getInstance();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundColor(Color.GRAY);
                }else{
                    save_btn.setEnabled(true);
                    save_btn.setBackgroundColor(getResources().getColor(teal_200));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        set_due_date.setOnClickListener(view1 -> {

            Calendar calendar = Calendar.getInstance();

            int DD = calendar.get(Calendar.DATE);
            int MM = calendar.get(Calendar.MONTH);
            int YYYY = calendar.get(Calendar.YEAR);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, year, month, day) -> {
                month = month + 1;          // As month starts from 0
                set_due_date.setText(day + "/" + month + "/" + year);

                dueDate = day + "/" + month + "/" + year;
            }, YYYY, MM, DD);

            datePickerDialog.show();
        });

        boolean finalIsUpdate = isUpdate;
        save_btn.setOnClickListener(view12 -> {
            String task = editText.getText().toString();

            if (finalIsUpdate){
                firebaseFirestore.collection("task").document(id).update("task", task, "due", dueDate);
                Toast.makeText(context,"Task Updated!", Toast.LENGTH_SHORT).show();
            }
            else {
                if (task.isEmpty()){
                    Toast.makeText(context, "Empty task not Allowed !!", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Object> task_map = new HashMap<>();

                    task_map.put("task", task);
                    task_map.put("due", dueDate);
                    task_map.put("status", 0);
                    task_map.put("time", FieldValue.serverTimestamp());

                    firebaseFirestore.collection("task").add(task_map).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()){
                            Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }
            dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
