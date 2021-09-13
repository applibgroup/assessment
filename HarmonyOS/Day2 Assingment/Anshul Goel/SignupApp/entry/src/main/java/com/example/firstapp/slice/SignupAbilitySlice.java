package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {

    private TextField firstname,secname,password,mobile,emailID;
    private Text fname_msg,sname_msg,mobile_msg,email_msg,pass_msg;
    private RadioButton male_btn,female_btn;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        firstname= (TextField) findComponentById(ResourceTable.Id_firstname);
        secname= (TextField) findComponentById(ResourceTable.Id_secondname);
        password= (TextField) findComponentById(ResourceTable.Id_password);
        mobile= (TextField) findComponentById(ResourceTable.Id_mobileNumber);
        emailID= (TextField) findComponentById(ResourceTable.Id_emailId);

        //for displaying error msg
        fname_msg= (Text) findComponentById(ResourceTable.Id_error_msg_fname);
        sname_msg= (Text) findComponentById(ResourceTable.Id_error_msg_sname);
        mobile_msg= (Text) findComponentById(ResourceTable.Id_error_msg_number);
        email_msg= (Text) findComponentById(ResourceTable.Id_error_msg_email);
        pass_msg= (Text) findComponentById(ResourceTable.Id_error_msg_password);

        firstname.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.isEmpty())
                    fname_msg.setText("Please Enter the First name");
                else if(! s.matches("^[a-zA-Z]*$"))
                    fname_msg.setText("Ivalid format: Name contains only alphabets");
                else
                    fname_msg.setText("");
            }
        });
        secname.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.isEmpty())
                    sname_msg.setText("Please Enter the Second name");
                else if(! s.matches("^[a-zA-Z]*$"))
                    sname_msg.setText("Ivalid format: Name contains only alphabets");
                else
                    sname_msg.setText("");
            }
        });

        emailID.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.isEmpty())
                    email_msg.setText("Enter the email ID");
                else if(s.contains("@") && s.contains(".com"))
                    email_msg.setText("");
                else
                    email_msg.setText("Invalid emailID: not a proper format");
            }
        });

        password.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                Pattern p = Pattern.compile("[^A-Za-z0-9]");
                Matcher m = p.matcher(s);
                if(s.isEmpty())
                    pass_msg.setText("Please Enter the Password");
                else if(s.length()<5)
                    pass_msg.setText("Password should of be atleast 5 characters");
                else if(! m.find())
                    pass_msg.setText("Password must have a special character");
                else
                    pass_msg.setText("");
            }
        });

        mobile.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                Pattern p = Pattern.compile("[^0-9]");
                Matcher m = p.matcher(s);
                if(s.isEmpty())
                    mobile_msg.setText("Please Enter Mobile number");
                else if(s.length()!=10 || m.find())
                    mobile_msg.setText("Invalid mobile number");
                else
                    mobile_msg.setText("");
            }
        });

        male_btn= (RadioButton) findComponentById(ResourceTable.Id_male);
        female_btn= (RadioButton) findComponentById(ResourceTable.Id_female);

        male_btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                female_btn.setChecked(false);
            }
        });

        female_btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                male_btn.setChecked(false);
            }
        });

        Button btn= (Button) findComponentById(ResourceTable.Id_registerButton);
        btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Pattern p = Pattern.compile("[^A-Za-z0-9]");
                Matcher m = p.matcher(password.getText());
                p=Pattern.compile("[^0-9]");
                Matcher m2= p.matcher(mobile.getText());

                if(firstname.getText().isEmpty() || secname.getText().isEmpty() || emailID.getText().isEmpty()
                    || password.getText().isEmpty() || mobile.getText().isEmpty()) {
                    new ToastDialog(getContext())
                            .setText("Fields are empty!")
                            .show();
                }
                else if(!firstname.getText().matches("^[a-zA-Z]*$") || !secname.getText().matches("^[a-zA-Z]*$")
                    || !emailID.getText().contains("@") || !emailID.getText().contains(".com")
                    || password.getText().length()<5 || !m.find()
                    || mobile.getText().length()!=10 || m2.find()){

                    new ToastDialog(getContext())
                            .setText("Please enter valid inputs")
                            .show();
                }
                else if(!male_btn.isChecked() && !female_btn.isChecked())
                    new ToastDialog(getContext())
                            .setText("Please Enter the Gender")
                            .show();
                else
                    new ToastDialog(getContext())
                            .setText("Successfully Registered")
                            .show();
            }
        });


    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
