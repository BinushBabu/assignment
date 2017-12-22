package in.museon.assignment.util;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidator {

    public final static boolean isValidName(TextInputLayout inputLayout) {
        String NAME_PATTERN = "[a-zA-Z ]+";
        Pattern pattern = Pattern.compile(NAME_PATTERN);

        Matcher matcher = pattern.matcher(inputLayout.getEditText().getText().toString());
        boolean valid= matcher.matches();
        if(!valid){
            setTextInputError(inputLayout,"please enter valid name");}
        else{
           clearTextInputError(inputLayout);
        }
        return  valid;
    }
    // validating phone number
    public  static boolean isValidPhoneNumber(TextInputLayout inputLayout) {

        String mobno = "[+]?[0-9]{10,13}";
        Pattern pattern = Pattern.compile(mobno);
        Matcher matcher = pattern.matcher(inputLayout.getEditText().getText().toString());
        boolean valid= matcher.matches();
        if(!valid){
            setTextInputError(inputLayout,"please enter valid mobile number");}
        else{
          clearTextInputError(inputLayout);
        }
        return  valid;
    }




    public static void setTextInputError(TextInputLayout tilObj, String msg) {
        tilObj.setErrorEnabled(true);
        tilObj.setError(msg);
    }
    public static void clearTextInputError(TextInputLayout tilObj) {
        tilObj.setErrorEnabled(false);
    }

}
