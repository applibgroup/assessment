package com.example.loginapp.slice;

import com.example.loginapp.data.User;
import com.example.loginapp.ResourceTable;
import com.example.loginapp.Utils.Constants;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class LoginAbilitySlice extends AbilitySlice {
    private TextField emailField;
    private Text emailError;
    private TextField passwordField;
    private Button login;
    private OrmContext ormContext;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_login);
        emailField = (TextField) findComponentById(ResourceTable.Id_login_email);
        emailError = (Text) findComponentById(ResourceTable.Id_login_email_error);
        passwordField = (TextField) findComponentById(ResourceTable.Id_login_password);
        login = (Button) findComponentById(ResourceTable.Id_login_btn);

        emailField.addTextObserver((input, start, before, count) -> validateEmail(input));
        login.setClickedListener(component -> handleLogin(component));
    }

    private void handleLogin(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        if (emailField.getText() == null
                || passwordField.getText() == null
                || emailField.getText().isEmpty()
                || passwordField.getText().isEmpty()) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_login_error_empty_fields)).show();
        } else if (emailError.getVisibility() == Component.VISIBLE) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_login_error_invalid_email)).show();
        }
        else {
            OrmPredicates ormPredicates = ormContext.where(User.class);
            ormPredicates.equalTo(Constants.FIELD_EMAIL, emailField.getText());
            ormPredicates.and();
            ormPredicates.equalTo(Constants.FIELD_PASSWORD, passwordField.getText());
            List<User> users = ormContext.query(ormPredicates);
            if (users == null || users.isEmpty()) {
                toastDialog.setText(component.getContext().getString(ResourceTable.String_login_error_invalid_credentials)).show();
            } else {
                toastDialog.setText(component.getContext().getString(ResourceTable.String_login_success)).show();
                Intent intent = new Intent();
                intent.setParam(Constants.FIELD_FIRSTNAME, users.get(0).getFirstName());
                intent.setParam(Constants.FIELD_GENDER, users.get(0).getGender());
                present(new IntroAbilitySlice(), intent);
            }
        }
    }

    public void validateEmail(String input){
        if(input==null)
            return;
        if( !(input.contains("@") && input.contains(".com")))
        {
            emailError.setVisibility(Component.VISIBLE);
        }
        else {
            emailError.setVisibility(Component.HIDE);
        }
    }
}
