package in.museon.assignment.ui.view;

import android.support.design.widget.TextInputLayout;

import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.exception.ConversionException;

/**
 * @author Wellington Costa on 02/10/2017.
 */
public class TextInputLayoutAdapter implements ViewDataAdapter<TextInputLayout, String> {

    @Override
    public String getData(TextInputLayout view) throws ConversionException {
        String s = "";
        try {
            s = view.getEditText().getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
}