package in.museon.assignment.data;

import android.graphics.Bitmap;

import in.museon.assignment.data.domian.User;

/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class Constants {


    //date Constants
    public static final String DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss a z";
    //image capturing Constants
    public static final int REQUEST_CAMERA = 101;
    public static final int REQUEST_SELECT_FILE = 102;
    public static Bitmap BITMAP;
    public static String UploadFilePath;
    //Fragment Animation Constants
    public static final int MOVEMENT_FRONT = 1 ;
    public static final int MOVEMENT_BACK = 0;
    public static final int ANIM_UP=1;
    public static final int ANIM_LEFT=2;
    public static final int ANIM_DOWN=3;
    public static final int ANIM_RIGHT=4;
    // msg
    public static final String API_MESSAGE_UNABLE_TO_REACH = "Unable to communicate with server, please verify your connection";
  // app flags
    public static boolean IS_FROM_USER=false;
    public static User SELECTED_USER=null;
    public static final String SUCCESS="202";
    public static final String EXIST="204";
}
