package com.example.myfirstapplication.slice;

import com.example.myfirstapplication.ResourceTable;
import com.example.myfirstapplication.Utils;
import com.example.myfirstapplication.orm.User;
import com.example.myfirstapplication.orm.UserStore;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignupAbilitySlice extends AbilitySlice {

    public static final int DIALOG_BOX_WIDTH = 984;

    //Components
    private TextField mTFFirstName, mTFLastName, mTFEmail, mTFPassword, mTFMobile;
    private Text mTErrFirstName, mTErrLastName, mTErrEmail, mTErrPassword, mTErrMobile;
    private Button mBtnSignup;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        initComponents();

        mBtnSignup.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (validateData()) {
                    User user = new User();
                    user.setFirstName(mTFFirstName.getText().trim());
                    user.setLastName(mTFLastName.getText().trim());
                    user.setEmail(mTFEmail.getText().trim());
                    user.setPassword(mTFPassword.getText().trim());
                    user.setMobile(mTFMobile.getText().trim());


                    DatabaseHelper helper = new DatabaseHelper(SignupAbilitySlice.this);
                    // Parameter context is of the ohos.app.Context type. Do not use slice.getContext() to obtain the context. Instead, pass the slice directly; otherwise, an error occurs indicating that no class is found.
                    OrmContext context = helper.getOrmContext("UserStore", "userStore.db", UserStore.class);

                    boolean isSuccessed = context.insert(user);
                    isSuccessed = context.flush();

                    if (isSuccessed) {
                        new CommonDialog(getContext())
                                .setTitleText("SignUp success!!")
                                .setContentText("Go to login page to enter the app, Please confirm")
                                .setButton(IDialog.BUTTON1, "yes", (iDialog, i) -> {
                                    present(new LoginAbilitySlice(), new Intent());
                                    iDialog.destroy();
                                })
                                .setButton(IDialog.BUTTON2, "No", (iDialog, i) -> {
                                    iDialog.destroy();
                                })
                                .setAutoClosable(true)
                                .setSize(DIALOG_BOX_WIDTH, MATCH_CONTENT)
                                .show();


                        new ToastDialog(getContext())
                                .setText("SignUp successful!")
                                .show();
                    }
                }
            }
        });
    }

    private void initComponents() {
        mTFFirstName = (TextField) findComponentById(ResourceTable.Id_input_first_name);
        mTFLastName = (TextField) findComponentById(ResourceTable.Id_input_last_name);
        mTFEmail = (TextField) findComponentById(ResourceTable.Id_input_email);
        mTFPassword = (TextField) findComponentById(ResourceTable.Id_input_password);
        mTFMobile = (TextField) findComponentById(ResourceTable.Id_input_mobile);

        mTErrFirstName = (Text) findComponentById(ResourceTable.Id_txt_err_first_name);
        mTErrLastName = (Text) findComponentById(ResourceTable.Id_txt_err_last_name);
        mTErrEmail = (Text) findComponentById(ResourceTable.Id_txt_err_email);
        mTErrPassword = (Text) findComponentById(ResourceTable.Id_txt_err_password);
        mTErrMobile = (Text) findComponentById(ResourceTable.Id_txt_err_mobile);

        mBtnSignup = (Button) findComponentById(ResourceTable.Id_btn_signup);
    }

    private boolean validateData() {
        ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
        ShapeElement textFieldElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field);

        //Check first name
        if (mTFFirstName.getText() == null || mTFFirstName.getText().isEmpty()) {
            mTFFirstName.setBackground(errorElement);
            mTErrFirstName.setVisibility(Component.VISIBLE);
            return false;
        } else {
            mTFFirstName.setBackground(textFieldElement);
            mTErrFirstName.setVisibility(Component.INVISIBLE);
        }

        //Check last name
        if (mTFLastName.getText() == null || mTFLastName.getText().isEmpty()) {
            mTFLastName.setBackground(errorElement);
            mTErrLastName.setVisibility(Component.VISIBLE);
            return false;
        } else {
            mTFLastName.setBackground(textFieldElement);
            mTErrLastName.setVisibility(Component.INVISIBLE);
        }

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

        //Check mobile
        if (mTFMobile.getText() == null || mTFMobile.getText().isEmpty()) {
            mTFMobile.setBackground(errorElement);
            mTErrMobile.setVisibility(Component.VISIBLE);
            return false;
        } else if (Utils.checkMobileNo(mTFMobile.getText())) {
            mTFMobile.setBackground(errorElement);
            mTErrMobile.setVisibility(Component.VISIBLE);
            mTErrMobile.setText("Please enter valid mobile no.!");
            return false;
        } else {
            mTFMobile.setBackground(textFieldElement);
            mTErrMobile.setVisibility(Component.INVISIBLE);
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
