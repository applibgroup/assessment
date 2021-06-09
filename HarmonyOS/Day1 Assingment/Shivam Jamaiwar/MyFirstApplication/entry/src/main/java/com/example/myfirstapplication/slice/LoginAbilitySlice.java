package com.example.myfirstapplication.slice;

import com.example.myfirstapplication.HomeAbility;
import com.example.myfirstapplication.ResourceTable;
import com.example.myfirstapplication.Utils;
import com.example.myfirstapplication.component.CustomComponent;
import com.example.myfirstapplication.orm.User;
import com.example.myfirstapplication.orm.UserStore;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class LoginAbilitySlice extends AbilitySlice {

    //Components
    private TextField mTFEmail, mTFPassword;
    private Text mTErrEmail, mTErrPassword;
    private Button mBtnLogin;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);

        initComponents();

        mBtnLogin.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

                if (validateData()) {

                    DatabaseHelper helper = new DatabaseHelper(LoginAbilitySlice.this);
                    // Parameter context is of the ohos.app.Context type. Do not use slice.getContext() to obtain the context. Instead, pass the slice directly; otherwise, an error occurs indicating that no class is found.
                    OrmContext context = helper.getOrmContext("UserStore", "userStore.db", UserStore.class);

                    OrmPredicates query = context.where(User.class)
                            .equalTo("email", mTFEmail.getText().trim())
                            .and()
                            .equalTo("password", mTFPassword.getText().trim());
                    List<User> users = context.query(query);
                    if (users.isEmpty()) {
                        new ToastDialog(getContext())
                                .setText("Login Failed!")
                                .show();
                    } else {
                        new ToastDialog(getContext())
                                .setText("Login Success!")
                                .show();

                        Intent intent = new Intent();
                        intent.setParam("name", users.get(0).getFirstName() + " " + users.get(0).getLastName());

                        /*Operation operation = new Intent.OperationBuilder()
                                .withAbilityName(HomeAbility.class.getName())
                                .build();
                        intent.setOperation(operation);
                        startAbility(intent);*/

                        present(new HomeAbilitySlice(), intent);
                    }
                }
            }
        });
    }

    private void initComponents() {
        mTFEmail = (TextField) findComponentById(ResourceTable.Id_input_email);
        mTFPassword = (TextField) findComponentById(ResourceTable.Id_input_password);

        mTErrEmail = (Text) findComponentById(ResourceTable.Id_txt_err_email);
        mTErrPassword = (Text) findComponentById(ResourceTable.Id_txt_err_password);

        mBtnLogin = (Button) findComponentById(ResourceTable.Id_btn_login);
    }

    private boolean validateData() {
        ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
        ShapeElement textFieldElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field);

        //Check email
        if (mTFEmail.getText() == null || mTFEmail.getText().isEmpty()) {
            mTFEmail.setBackground(errorElement);
            mTErrEmail.setVisibility(Component.VISIBLE);
            return false;
        } else if (!Utils.checkEmail(mTFEmail.getText())){
            mTFEmail.setBackground(errorElement);
            mTErrEmail.setVisibility(Component.VISIBLE);
            mTErrEmail.setText("Please enter valid email!");
            return false;
        } else {
            mTFEmail.setBackground(textFieldElement);
            mTErrEmail.setVisibility(Component.INVISIBLE);
        }

        //Check password
        if (mTFPassword.getText() == null || mTFPassword.getText().isEmpty()) {
            mTFPassword.setBackground(errorElement);
            mTErrPassword.setVisibility(Component.VISIBLE);
            return false;
        } else {
            mTFPassword.setBackground(textFieldElement);
            mTErrPassword.setVisibility(Component.INVISIBLE);
        }

        return true;
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
