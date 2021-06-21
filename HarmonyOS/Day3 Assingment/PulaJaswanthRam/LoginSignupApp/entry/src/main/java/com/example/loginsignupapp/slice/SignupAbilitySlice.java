package com.example.loginsignupapp.slice;

import com.example.loginsignupapp.ResourceTable;
import com.example.loginsignupapp.data.Db;
import com.example.loginsignupapp.data.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class SignupAbilitySlice extends AbilitySlice {

    private TextField fName;
    private TextField lName;
    private TextField email;
    private TextField password;
    private TextField mobile;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    private Button submit;
    private OrmContext ormContext;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_signup);
        checker(ResourceTable.Id_lName, ResourceTable.Id_lName_error, this::Name_check);
        checker(ResourceTable.Id_email, ResourceTable.Id_email_error, this::Email_check);
        checker(ResourceTable.Id_password, ResourceTable.Id_password_error, this::Password_check);
        checker(ResourceTable.Id_mobile, ResourceTable.Id_mobile_error, this::Number_check);
        initViews();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ormContext = databaseHelper.getOrmContext("Db","Db.db", Db.class);
        submit.setClickedListener(this::helper);
    }

    private void initViews() {
        fName = (TextField) findComponentById(ResourceTable.Id_fName);
        lName = (TextField) findComponentById(ResourceTable.Id_lName);
        email = (TextField) findComponentById(ResourceTable.Id_email);
        password = (TextField) findComponentById(ResourceTable.Id_password);
        mobile = (TextField) findComponentById(ResourceTable.Id_mobile);
        maleBtn = (RadioButton) findComponentById(ResourceTable.Id_Male_btn);
        femaleBtn = (RadioButton) findComponentById(ResourceTable.Id_female_btn);
        submit = (Button) findComponentById(ResourceTable.Id_signup_btn);
    }

    private boolean Name_check(String text) {
        return text.matches("([a-zA-Z])*");
    }

    private boolean Email_check(String str) {
        String x = "([a-zA-Z0-9])*";
        String y = "([a-zA-Z])*";
        return str.matches(x + "[@]" + x + "[.]" + y);
    }

    private boolean Password_check(String s) {
        return s.length()>=5;
    }

    private boolean Number_check(String str) {
        return str.matches("([0-9])*") && (str.length()==10);
    }

    public void checker(int Field_Id, int Err_Id, Lambda l)
    {
        TextField textField = (TextField) findComponentById(Field_Id);
        Text err_text = (Text) findComponentById(Err_Id);

        Text.TextObserver observer = (s, i, i1, i2) -> {
            if (l.function(textField.getText())){
                err_text.setVisibility(Component.HIDE);
            }
            else{
                err_text.setVisibility(Component.VISIBLE);
            }
        };

        textField.addTextObserver(observer);
    }

    private interface Lambda {
        boolean function(String s);
    }

    public void helper(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        if (!maleBtn.isChecked() && !femaleBtn.isChecked()) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_genderErr)).show();
        } else {
            User user = new User();
            user.setEmail(email.getText());
            user.setfName(fName.getText());
            user.setlName(lName.getText());
            user.setPassword(password.getText());
            user.setMobileNumber(mobile.getText());
            user.setGender(maleBtn.isChecked() ? "M" : "F");

            OrmPredicates ormPredicates = ormContext.where(User.class);
            ormPredicates.equalTo("email", user.getEmail());
            List<User> list = ormContext.query(ormPredicates);
            if(list.size()==0 || list==null){
                new ToastDialog(this).setText("SignUp successful").show();
                ormContext.insert(user);
                ormContext.flush();
            }
            else{
                new ToastDialog(this).setText("SignUp Failed").show();
            }
        }
    }
}
