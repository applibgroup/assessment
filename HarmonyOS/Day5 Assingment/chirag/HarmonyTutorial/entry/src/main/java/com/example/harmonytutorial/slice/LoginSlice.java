package com.example.harmonytutorial.slice;

import com.example.harmonytutorial.InputValidation;
import com.example.harmonytutorial.ResourceTable;
import com.example.harmonytutorial.StoreDB;
import com.example.harmonytutorial.UserEntity;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class LoginSlice extends AbilitySlice {
    private Button login;
    private Text user_email;
    private Text user_password;
    private DatabaseHelper dbHelper;
    private OrmContext ormContext;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        initViews();
        dbHelper = new DatabaseHelper(this);
        ormContext = dbHelper.getOrmContext("StoreDB", "StoreDB.db", StoreDB.class);
        login.setClickedListener(component -> handleSubmitClick(component));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
    private void initViews() {
        user_email = (TextField) findComponentById(ResourceTable.Id_user_email);
        user_password = (TextField) findComponentById(ResourceTable.Id_user_password);
        login = (Button) findComponentById(ResourceTable.Id_login_button);
        user_password.setText(null);
        user_email.setText(null);
    }
    public void handleSubmitClick(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        if (user_email.getText() == null || user_email.getText().trim().isEmpty() ||
             user_password.getText().trim().isEmpty() || user_password.getText() == null){
            toastDialog.setText("Please fill all the fields").show();
        }else if(!(user_email.getText().contains("@") && user_email.getText().contains(".com"))){
            toastDialog.setText("Entered Email not correct").show();
        }else {
            OrmPredicates pred = ormContext.where(UserEntity.class);
            pred.equalTo("email", user_email.getText());
            pred.and();
            pred.equalTo("password", user_password.getText());
            List<UserEntity> user = ormContext.query(pred);
            if(user.size() == 0){
                toastDialog.setText("Entered Email or Password is not correct").show();
            }
            else{
                Intent intent = new Intent();
                intent.setParam("first name",user.get(0).getFirstName());
                intent.setParam("last name", user.get(0).getLastName());
                present(new SuccessLoginSlice(),new Intent());
            }
        }
    }
}
