package com.applib.jawahar_venugopal.hmosassignment.slice;

import com.applib.jawahar_venugopal.hmosassignment.ResourceTable;
import com.applib.jawahar_venugopal.hmosassignment.customview.CustomButton;
import com.applib.jawahar_venugopal.hmosassignment.data.User;
import com.applib.jawahar_venugopal.hmosassignment.data.UserDatabase;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class LoginAbilitySlice extends AbilitySlice {

    private TextField mUsernameField;
    private TextField mPasswordField;

    private Text mUsernameErrorText;
    private Text mPasswordErrorText;

    private OrmContext mOrmContext;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        initViews();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mOrmContext = databaseHelper.getOrmContext("UserDetails", "UserDetails.db", UserDatabase.class);
    }

    private void initViews() {
        mUsernameField = (TextField) findComponentById(ResourceTable.Id_username);
        mPasswordField = (TextField) findComponentById(ResourceTable.Id_password);
        mUsernameErrorText = (Text) findComponentById(ResourceTable.Id_username_error);
        mPasswordErrorText = (Text) findComponentById(ResourceTable.Id_password_error);

        CustomButton loginButton = (CustomButton) findComponentById(ResourceTable.Id_login);
        loginButton.setClickedListener(view -> login());

    }

    private void login() {
        String username = mUsernameField.getText();
        String password = mPasswordField.getText();

        if (username == null || username.isEmpty()) {
            mUsernameErrorText.setVisibility(Component.VISIBLE);
            return;
        } else {
            mUsernameErrorText.setVisibility(Component.HIDE);
        }
        if (password == null || password.isEmpty()) {
            mPasswordErrorText.setVisibility(Component.VISIBLE);
            return;
        } else {
            mUsernameErrorText.setVisibility(Component.HIDE);
        }
        //ToDO: Offload DB operation to Background thread
        OrmPredicates query = mOrmContext.where(User.class).equalTo("mEmail", username);
        List<User> users = mOrmContext.query(query);
        if (users.isEmpty()) {
            mUsernameErrorText.setVisibility(Component.VISIBLE);
            return;
        } else {
            mUsernameErrorText.setVisibility(Component.HIDE);
        }
        if (!users.get(0).getmPassword().contentEquals(password)) {
            mPasswordErrorText.setVisibility(Component.VISIBLE);
            return;
        } else {
            mPasswordErrorText.setVisibility(Component.HIDE);
        }

        Text successText = (Text) findComponentById(ResourceTable.Id_login_success);
        successText.setText("Welcome " + users.get(0).getmFirstName());
        successText.setVisibility(Component.VISIBLE);

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
