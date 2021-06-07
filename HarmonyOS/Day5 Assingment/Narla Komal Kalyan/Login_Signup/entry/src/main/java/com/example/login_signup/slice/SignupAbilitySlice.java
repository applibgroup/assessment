package com.example.login_signup.slice;

import com.example.login_signup.MYDB;
import com.example.login_signup.ResourceTable;
import com.example.login_signup.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupAbilitySlice extends AbilitySlice {
    private TextField fname;
    private TextField lname;
    private TextField email;
    private TextField password;
    private TextField mobnum;
    private RadioContainer radioContainer;

    private Text fname_error;
    private Text lname_error;
    private Text email_error;
    private Text password_error;
    private Text mobnum_error;

    private String fname_str;
    private String lname_str;
    private String email_str;
    private String password_str;
    private String mobnum_str;
    private int gender;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);
        DatabaseHelper helper = new DatabaseHelper(this);

        fname = (TextField) findComponentById(ResourceTable.Id_first_name);
        lname = (TextField) findComponentById(ResourceTable.Id_last_name);
        email = (TextField) findComponentById(ResourceTable.Id_email);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        mobnum = (TextField) findComponentById(ResourceTable.Id_mobile_number);
        radioContainer = (RadioContainer) findComponentById(ResourceTable.Id_gender_container);

        fname_error = (Text) findComponentById(ResourceTable.Id_first_name_error);
        lname_error = (Text) findComponentById(ResourceTable.Id_last_name_error);
        email_error = (Text) findComponentById(ResourceTable.Id_email_error);
        password_error = (Text) findComponentById(ResourceTable.Id_password_error);
        mobnum_error = (Text) findComponentById(ResourceTable.Id_mobile_number_error);

        fname.setText("");
        lname.setText("");
        email.setText("");
        password.setText("");
        mobnum.setText("");
        radioContainer.mark(0);

        Button create_account = (Button)findComponentById(ResourceTable.Id_create_account);
        create_account.setClickedListener(component -> {
            fname_str = fname.getText();
            lname_str = lname.getText();
            email_str = email.getText();
            password_str = password.getText();
            mobnum_str = mobnum.getText();
            gender = radioContainer.getMarkedButtonId();

            fname_error.setText("None");
            lname_error.setText("None");
            email_error.setText("None");
            password_error.setText("None");
            mobnum_error.setText("None");

            if(sanitycheck()) {
                OrmContext context = helper.getOrmContext("MYDB", "MYDB.db", MYDB.class);
                OrmPredicates query = context.where(User.class).equalTo("email", email_str);
                List<User> users = context.query(query);
                if(users.size()==0) {
                    User user = new User();
                    user.setFirstName(fname_str);
                    user.setLastName(lname_str);
                    user.setEmail(email_str);
                    user.setPassword(password_str);
                    user.setMobileNumber(mobnum_str);
                    if(gender==0)
                        user.setGender("Male");
                    else
                        user.setGender("Female");
                    boolean isSuccessed = context.insert(user);
                    isSuccessed = context.flush();

                    if(isSuccessed) {
                        new ToastDialog(getContext())
                            .setText("Account Created Successfully!")
                            .show();
                        showCommonDialog();

                    }
                    else {
                        new ToastDialog(getContext())
                                .setText("Oops Something went wrong!")
                                .show();
                        present(new SignupAbilitySlice(),new Intent());
                    }
                }
                else {
                    email_error.setText("Email already taken! Please try another");
                    email_error.setVisibility(Component.VISIBLE);
                }
            }
            else {
                new ToastDialog(getContext())
                    .setText("Please specify valid details!")
                    .show();
            }
        });
    }

    public boolean sanitycheck() {
        boolean answer = true;

        if(fname_str.equals(""))
            fname_error.setText("Enter a non empty First Name");
        else if(!Pattern.matches("[a-zA-Z]+",fname_str))
            fname_error.setText("First Name must contain letters only");

        if(lname_str.equals(""))
            lname_error.setText("Enter a non empty Last Name");
        else if(!Pattern.matches("[a-zA-Z]+",lname_str))
            lname_error.setText("Last Name must contain letters only");

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if(email_str.equals(""))
            email_error.setText("Enter a non empty Email Address");
        else if(!pat.matcher(email_str).matches())
            email_error.setText("Incorrect Email format");

        if(password_str.equals(""))
            password_error.setText("Enter a non empty Password");
        else if(password_str.length()<5)
            password_error.setText("Password must have at least 5 characters");

        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(mobnum_str);
        if(mobnum_str.equals(""))
            mobnum_error.setText("Enter a non empty Mobile Number");
        else if(!(m.find() && m.group().equals(mobnum_str)))
            mobnum_error.setText("Invalid Mobile Number format");

        if(fname_error.getText().equals("None"))
            fname_error.setVisibility(Component.INVISIBLE);
        else {
            fname_error.setVisibility(Component.VISIBLE);
            answer = false;
        }

        if(lname_error.getText().equals("None"))
            lname_error.setVisibility(Component.INVISIBLE);
        else {
            lname_error.setVisibility(Component.VISIBLE);
            answer = false;
        }

        if(email_error.getText().equals("None"))
            email_error.setVisibility(Component.INVISIBLE);
        else {
            email_error.setVisibility(Component.VISIBLE);
            answer = false;
        }

        if(password_error.getText().equals("None"))
            password_error.setVisibility(Component.INVISIBLE);
        else {
            password_error.setVisibility(Component.VISIBLE);
            answer = false;
        }

        if(mobnum_error.getText().equals("None"))
            mobnum_error.setVisibility(Component.INVISIBLE);
        else {
            mobnum_error.setVisibility(Component.VISIBLE);
            answer = false;
        }

        return answer;
    }

    public void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText("Signup Success!!");
        commonDialog.setContentText("Go to Login page?");
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(650,400);
        commonDialog.setButton(IDialog.BUTTON1, "Yes", (iDialog, i) -> {
            Intent intent = new Intent();
            intent.setParam("email", email_str);
            intent.setParam("password",password_str);
            present(new LoginAbilitySlice(),intent);
            iDialog.destroy();
        });
        commonDialog.setButton(IDialog.BUTTON2,"No", (iDialog, i) -> {
            present(new MainAbilitySlice(),new Intent());
            iDialog.destroy();
        });
        commonDialog.show();
    }
}