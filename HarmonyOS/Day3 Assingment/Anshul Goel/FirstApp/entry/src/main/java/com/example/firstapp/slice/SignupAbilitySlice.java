package com.example.firstapp.slice;

import com.example.firstapp.ResourceTable;
import com.example.firstapp.User;
import com.example.firstapp.UserData;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {

    private TextField firstname,secname,password,mobile,emailID;
    private Text fname_msg,sname_msg,mobile_msg,email_msg,pass_msg;
    private RadioButton male_btn,female_btn;
    private DatabaseHelper helper;
    private OrmContext context;

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

        //to check whether the first name format
        firstname.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.isEmpty())
                    fname_msg.setText(getString(ResourceTable.String_Empty_Field));
                else if(! s.matches(getString(ResourceTable.String_name_regx)))
                    fname_msg.setText(getString(ResourceTable.String_Only_aplha));
                else
                    fname_msg.setText(getString(ResourceTable.String_Clear_Err));
            }
        });

        //to check whether the second name format
        secname.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.isEmpty())
                    sname_msg.setText(getString(ResourceTable.String_Empty_Field));
                else if(! s.matches(getString(ResourceTable.String_name_regx)))
                    sname_msg.setText(getString(ResourceTable.String_Only_aplha));
                else
                    sname_msg.setText(getString(ResourceTable.String_Clear_Err));
            }
        });

        //to check whether the email format
        emailID.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.isEmpty())
                    email_msg.setText(getString(ResourceTable.String_Empty_Field));
                else if(s.contains(getString(ResourceTable.String_at_the_rate)) && s.contains(getString(ResourceTable.String_dot)))
                    email_msg.setText(getString(ResourceTable.String_Clear_Err));
                else
                    email_msg.setText(getString(ResourceTable.String_email_err));
            }
        });

        //to check whether the password format
        password.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                Pattern p = Pattern.compile(getString(ResourceTable.String_pass_regx));
                Matcher m = p.matcher(s);
                if(s.isEmpty())
                    pass_msg.setText(getString(ResourceTable.String_Empty_Field));
                else if(s.length()<5)
                    pass_msg.setText(getString(ResourceTable.String_Pass_error1));
                else if(! m.find())
                    pass_msg.setText(getString(ResourceTable.String_Pass_error2));
                else
                    pass_msg.setText(getString(ResourceTable.String_Clear_Err));
            }
        });

        //to check whether the mobile number format
        mobile.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                Pattern p = Pattern.compile(getString(ResourceTable.String_mobile_regx));
                Matcher m = p.matcher(s);
                if(s.isEmpty())
                    mobile_msg.setText(getString(ResourceTable.String_Empty_Field));
                else if(s.length()!=10 || m.find())
                    mobile_msg.setText(getString(ResourceTable.String_Mobile_err));
                else
                    mobile_msg.setText(getString(ResourceTable.String_Clear_Err));
            }
        });

        //switching other button to off on clicking on one of the button
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
                male_btn.setChecked(false);  //making it off if male is clicked
            }
        });


        helper= new DatabaseHelper(SignupAbilitySlice.this);      //calling database instance
        context= helper.getOrmContext(getString(ResourceTable.String_UserData),getString(ResourceTable.String_UserData_db), UserData.class);


        Button btn= (Button) findComponentById(ResourceTable.Id_registerButton);
        btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Pattern p = Pattern.compile(getString(ResourceTable.String_pass_regx));
                Matcher m = p.matcher(password.getText());
                p=Pattern.compile(getString(ResourceTable.String_mobile_regx));
                Matcher m2= p.matcher(mobile.getText());

                if(firstname.getText().isEmpty() || secname.getText().isEmpty() || emailID.getText().isEmpty()
                    || password.getText().isEmpty() || mobile.getText().isEmpty()) {
                    new ToastDialog(getContext())
                            .setText(getString(ResourceTable.String_Empty_Field))
                            .show();
                }
                else if(!firstname.getText().matches(getString(ResourceTable.String_name_regx)) || !secname.getText().matches(getString(ResourceTable.String_name_regx))
                    || !emailID.getText().contains(getString(ResourceTable.String_at_the_rate)) || !emailID.getText().contains(getString(ResourceTable.String_dot))
                    || password.getText().length()<5 || !m.find() || mobile.getText().length()!=10 || m2.find()){

                    new ToastDialog(getContext())
                            .setText(getString(ResourceTable.String_Enter_valid))
                            .show();
                }
                else if(!male_btn.isChecked() && !female_btn.isChecked())
                    new ToastDialog(getContext())
                            .setText(getString(ResourceTable.String_Gender_Msg))
                            .show();   //if none of the button is clicked
                else
                {
                    OrmPredicates predicates= context.where(User.class).equalTo(getString(ResourceTable.String_email),emailID.getText());
                    List<User> users= context.query(predicates);  // for checking whether the email is already registered
                    if(users.size()==0)
                    {
                        User user = new User();
                        user.setFirstname(firstname.getText());
                        user.setSecname(secname.getText());
                        user.setEmail(emailID.getText());
                        user.setPassword(password.getText());
                        user.setMobile(mobile.getText());
                        if(male_btn.isChecked())
                            user.setGender(1);  //for male
                        else
                            user.setGender(2);  //for female

                        boolean isSuccess = context.insert(user);  //inserting into database
                        isSuccess = context.flush();

                        if (isSuccess == true) {
                            new ToastDialog(getContext())
                                    .setText(getString(ResourceTable.String_Register_Msg))
                                    .show();
                            present(new LoginAbilitySlice(), new Intent()); //if signup successfull, it will take to login page
                        } else
                            new ToastDialog(getContext())
                                    .setText(getString(ResourceTable.String_Error_Occ))
                                    .show();
                    }
                    else {
                        new ToastDialog(getContext())
                                .setText(getString(ResourceTable.String_Email_exist))
                                .show(); //shows error is email already registered/exists
                    }
                }
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
