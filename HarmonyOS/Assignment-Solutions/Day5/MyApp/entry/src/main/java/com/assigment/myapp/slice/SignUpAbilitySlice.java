package com.assigment.myapp.slice;

import com.assigment.myapp.ResourceTable;
import com.assigment.myapp.Utils.AnimationUtils;
import com.assigment.myapp.Utils.Constants;
import com.assigment.myapp.Utils.PatternUtils;
import com.assigment.myapp.data.AccountDb;
import com.assigment.myapp.data.User;
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

import static com.assigment.myapp.Utils.Constants.DB_ALIAS_NAME;
import static com.assigment.myapp.Utils.Constants.DB_NAME;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class SignUpAbilitySlice extends AbilitySlice {
    private static final int MIN_PASSWORD_LENGTH = 5;

    public static final int DIALOG_BOX_WIDTH = 984;

    private static String GENDER_MALE = "M";

    private static String GENDER_FEMALE = "F";

    private static int MOBILE_LENGTH = 10;

    private Image appLogo;
    private TextField firstName;
    private TextField lastName;
    private TextField emailField;
    private TextField passwordField;
    private TextField mobileField;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private Text firstNameError;
    private Text lastNameError;
    private Text emailError;
    private Text passwordError;
    private Text mobileError;
    private Button submit;
    private OrmContext ormContext;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_signup_page);
        initViews();
        initListeners();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ormContext = databaseHelper.getOrmContext(DB_ALIAS_NAME, DB_NAME, AccountDb.class);
    }

    private void initViews() {
        firstName = (TextField) findComponentById(ResourceTable.Id_firstname);
        lastName = (TextField) findComponentById(ResourceTable.Id_lastname);
        emailField = (TextField) findComponentById(ResourceTable.Id_email);
        passwordField = (TextField) findComponentById(ResourceTable.Id_password);
        mobileField = (TextField) findComponentById(ResourceTable.Id_mobile);

        firstNameError = (Text) findComponentById(ResourceTable.Id_firstname_error);
        lastNameError = (Text) findComponentById(ResourceTable.Id_lastname_error);
        emailError = (Text) findComponentById(ResourceTable.Id_email_error);
        passwordError = (Text) findComponentById(ResourceTable.Id_password_error);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);

        maleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_male);
        femaleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_female);

        submit = (Button) findComponentById(ResourceTable.Id_signup_btn);
        appLogo = (Image) findComponentById(ResourceTable.Id_signup_page_header);
        appLogo.setPixelMap(ResourceTable.Media_my_app);
        appLogo.setScaleMode(Image.ScaleMode.STRETCH);
    }

    private void initListeners() {
        appLogo.setClickedListener(component -> AnimationUtils.setAppLogoAnimation(appLogo));
        firstName.addTextObserver((input,start,before, count)-> validateName(firstNameError,input));
        lastName.addTextObserver((input, start, before, count)-> validateName(lastNameError,input));
        emailField.addTextObserver((input, start, before, count) -> validateEmail(input));
        passwordField.addTextObserver((input, start, before, count) -> validatePassword(input));
        mobileField.addTextObserver((input, start, before, count) -> validateMobile(input));
        submit.setClickedListener(component -> handleSubmitClick(component));
        appLogo.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                AnimationUtils.setAppLogoAnimation(appLogo);
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {

            }
        });
    }

    /**
     * validates the firstname and last name
     *
     * @param errorText - error text object for the respective textfield
     * @param input - input string in the textfield
     */
    public void validateName(Text errorText, String input) {
        if (input == null) {
            return;
        }
        if (PatternUtils.isConsistOfNumber(input) || PatternUtils.isContainSpecialCharacter(input)) {
            errorText.setVisibility(Component.VISIBLE);
        } else {
            errorText.setVisibility(Component.HIDE);
        }
    }

    /**
     * validates if its a valid email address or not
     *
     * @param email - email string entered by user
     */
    public void validateEmail(String email){
        if (email==null) {
            return;
        }
        if (!(email.contains("@") && email.contains(".com"))) {
            emailError.setVisibility(Component.VISIBLE);
        } else {
            emailError.setVisibility(Component.HIDE);
        }
    }

    /**
     * validates if its a valid password or not. If the string>5 and contains a special character, then its valid
     *
     * @param password - password string entered by user
     */
    public void validatePassword(String password) {
        if (password == null) {
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH || !PatternUtils.isContainSpecialCharacter(password)) {
            passwordError.setVisibility(Component.VISIBLE);
        } else {
            passwordError.setVisibility(Component.HIDE);
        }
    }

    /**
     * validates mobile number. If the number is less that 10 digits and contains non numeric chars,
     * then its invalid
     *
     * @param mobile
     */
    public void validateMobile(String mobile) {
        if (mobile == null) {
            return;
        }
        if (mobile.length() < MOBILE_LENGTH || !PatternUtils.isContainOnlyNumber(mobile)) {
            mobileError.setVisibility(Component.VISIBLE);
        } else {
            mobileError.setVisibility(Component.HIDE);
        }
    }

    /**
     * checks if the form can be submitted or not
     *
     * @param component - button
     */
    public void handleSubmitClick(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        if (firstName.getText() == null
                || lastName.getText() == null
                || emailField.getText() == null
                || passwordField.getText() == null
                || mobileField.getText() == null
                || firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || emailField.getText().isEmpty()
                || mobileField.getText().isEmpty()) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_error)).show();
        } else if (firstNameError.getVisibility() == Component.VISIBLE
                || lastNameError.getVisibility() == Component.VISIBLE
                || emailError.getVisibility() == Component.VISIBLE
                || passwordError.getVisibility() == Component.VISIBLE
                || mobileError.getVisibility() == Component.VISIBLE) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_error)).show();
        } else if (!maleButton.isChecked() && !femaleButton.isChecked()) {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_error_select_gender)).show();
        } else {
            User user = new User();
            user.setEmail(emailField.getText());
            user.setPassword(passwordField.getText());
            user.setFirstName(firstName.getText());
            user.setLastName(lastName.getText());
            user.setMobileNumber(mobileField.getText());
            user.setGender(maleButton.isChecked() ? GENDER_MALE : GENDER_FEMALE);
            if (canInsertUserinAccountDb(user)) {
                insetUserIntoAccountDb(user);
                showSignupSucessDialog(component, user);
            } else {
                toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_account_exist_error)).show();
            }
        }
    }

    private boolean canInsertUserinAccountDb(User user) {
        OrmPredicates ormPredicates = ormContext.where(User.class);
        ormPredicates.equalTo(Constants.FIELD_EMAIL, user.getEmail());
        List<User> users = ormContext.query(ormPredicates);
        if (users == null || users.size() == 0) {
            return true;
        }
        return false;
    }

    public void insetUserIntoAccountDb(User user) {
        ormContext.insert(user);
        ormContext.flush();
    }

    public void showSignupSucessDialog(Component component, User user) {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitleText(component.getContext().getString(ResourceTable.String_singup_success_dialog_title));
        commonDialog.setContentText(component.getContext().getString(ResourceTable.String_singup_success_dialog_content));
        commonDialog.setAutoClosable(true);
        commonDialog.setSize(MATCH_CONTENT, MATCH_CONTENT);
        commonDialog.setButton(IDialog.BUTTON1,
                component.getContext().getString(ResourceTable.String_signup_success_dialog_option_1),
                (iDialog, i) -> {
            Intent intent = new Intent();
            intent.setParam(Constants.FIELD_EMAIL, user.getEmail());
            intent.setParam(Constants.FIELD_PASSWORD, user.getPassword());
            present(new LoginAbilitySlice(), intent);
            iDialog.destroy();
        });
        commonDialog.setButton(IDialog.BUTTON2,
                component.getContext().getString(ResourceTable.String_signup_success_dialog_option_2),
                (iDialog, i) -> {
            iDialog.destroy();
        });
        commonDialog.show();
    }
}
