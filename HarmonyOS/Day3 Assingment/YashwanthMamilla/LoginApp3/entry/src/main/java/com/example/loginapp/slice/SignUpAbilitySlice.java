package com.example.loginapp.slice;

import com.example.loginapp.data.AccountDb;
import com.example.loginapp.data.User;
import com.example.loginapp.ResourceTable;
import com.example.loginapp.Utils.Constants;
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
import static com.example.loginapp.Utils.Constants.DB_ALIAS_NAME;
import static com.example.loginapp.Utils.Constants.DB_NAME;

public class SignUpAbilitySlice extends AbilitySlice {
    TextField firstName;
    TextField lastName;
    TextField emailField;
    TextField passwordField;
    TextField mobileField;
    Text firstNameError;
    Text lastNameError;
    Text emailError;
    Text passwordError;
    Text mobileError;
    RadioButton maleButton;
    RadioButton femaleButton;
    Button submit;
    Image appLogo;
    private OrmContext ormContext;

    private static String GENDER_MALE = "M";
    private static String GENDER_FEMALE = "F";
    private static final String PATTERN_NUMBER_REGEX = ".*\\d.*";
    private static final String PATTERN_SPECIAL_CHAR = "[^a-zA-Z0-9]";
    private static final String PATTERN_NUMBER = "[^0-9]";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_signup);

        firstName = (TextField) findComponentById(ResourceTable.Id_firstname);
        firstNameError = (Text) findComponentById(ResourceTable.Id_firstname_error);

        lastName = (TextField) findComponentById(ResourceTable.Id_lastname);
        lastNameError = (Text) findComponentById(ResourceTable.Id_lastname_error);

        emailField = (TextField) findComponentById(ResourceTable.Id_email);
        emailError = (Text) findComponentById(ResourceTable.Id_email_error);

        passwordField = (TextField) findComponentById(ResourceTable.Id_password);
        passwordError = (Text) findComponentById(ResourceTable.Id_password_error);

        mobileField = (TextField) findComponentById(ResourceTable.Id_mobile);
        mobileError = (Text) findComponentById(ResourceTable.Id_mobile_error);

        maleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_male);
        femaleButton = (RadioButton) findComponentById(ResourceTable.Id_gender_female);

        submit = (Button) findComponentById(ResourceTable.Id_signup_btn);

        appLogo = (Image) findComponentById(ResourceTable.Id_signup_page_header);
        appLogo.setPixelMap(ResourceTable.Media_my_app);
        appLogo.setScaleMode(Image.ScaleMode.STRETCH);

        firstName.addTextObserver((input,start,before, count)-> validateName(firstNameError,input));
        lastName.addTextObserver((input, start, before, count)-> validateName(lastNameError,input));
        emailField.addTextObserver((input, start, before, count) -> validateEmail(input));
        passwordField.addTextObserver((input, start, before, count) -> validatePassword(input));
        mobileField.addTextObserver((input, start, before, count) -> validateMobile(input));
        submit.setClickedListener(component -> handleSubmitClick(component));

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ormContext = databaseHelper.getOrmContext(DB_ALIAS_NAME, DB_NAME, AccountDb.class);

    }

    private void handleSubmitClick(Component component) {
        ToastDialog toastDialog = new ToastDialog(component.getContext());
        if (firstName.getText() == null || lastName.getText() == null || emailField.getText() == null
                || passwordField.getText() == null || mobileField.getText() == null
                || firstName.getText().isEmpty() || lastName.getText().isEmpty() || emailField.getText().isEmpty()
                || passwordField.getText().isEmpty() || mobileField.getText().isEmpty())
        {
            toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_error)).show();
        }
        else if (firstNameError.getVisibility() == Component.VISIBLE
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
            if(!isUserExists(user))
            {
                CreateUser(user);toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_success)).show();
            } else {
                toastDialog.setText(component.getContext().getString(ResourceTable.String_submit_account_exist_error)).show();
            }
        }
    }

    private boolean isUserExists(User user){
        OrmPredicates ormPredicates = ormContext.where(User.class);
        ormPredicates.equalTo(Constants.FIELD_EMAIL, user.getEmail());
        List<User> users = ormContext.query(ormPredicates);
        if (users == null || users.size() == 0) {
            return false;
        }
        else return true;
    }

    private void CreateUser(User user){
        ormContext.insert(user);
        ormContext.flush();
    }

    public void validateName(Text error,String input){
        if(input==null)
            return;
        boolean isNumber = input.matches(PATTERN_NUMBER_REGEX);
        Pattern pattern = Pattern.compile(PATTERN_SPECIAL_CHAR);
        Matcher match=pattern.matcher(input);
        boolean isSpecial_char=match.find();
        if( isNumber || isSpecial_char )
        {
            error.setVisibility(Component.VISIBLE);
        }
        else {
            error.setVisibility(Component.HIDE);
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

    public void validateMobile(String input){
        if(input==null)
            return ;
        Pattern pattern = Pattern.compile(PATTERN_NUMBER);
        Matcher matcher = pattern.matcher(input);
        if( input.length()<10 || matcher.find())
            mobileError.setVisibility(Component.VISIBLE);
        else mobileError.setVisibility(Component.HIDE);
    }

    public void validatePassword(String input){
        if(input==null)
            return;
        Pattern pattern = Pattern.compile(PATTERN_SPECIAL_CHAR);
        Matcher match=pattern.matcher(input);
        boolean isSpecial_char=match.find();
        if( input.length()<5 || !isSpecial_char)
            passwordError.setVisibility(Component.VISIBLE);
        else passwordError.setVisibility(Component.HIDE);
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
