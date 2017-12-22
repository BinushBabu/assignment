package in.museon.assignment.ui.dialog;

import android.widget.Toast;

import in.museon.assignment.app.App;


/**
 *
 * @author dev.Cobb
 * @version 1.0
 * @since 8 oct 2016
 *
 *class {@link ToastHelper } using  create toast
 *
 *  A toast is a view containing a quick little message for the user. The toast class helps you create and show those.
 *  When the view is shown to the user, appears as a floating view over the application.
    It will never receive focus. The user will probably be in the middle of typing something else.
    The idea is to be as unobtrusive as possible, while still showing the user the information you want them to see.
    Two examples are the volume control, and the brief message saying that your settings have been saved.
 *
 **/

public class ToastHelper {


    /**
     * A simple toast
     * @param message message show in screen
     */
    public static void show(String message) {
        Toast.makeText(App.getContext() , message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * A simple toast
     * @param message message show in screen
     * @param duration duration of message show in screen
     */
    public static void show(String message, int duration) {
        Toast.makeText(App.getContext(), message,
                duration).show();
    }
}
