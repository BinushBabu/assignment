package in.museon.assignment.ui.animations;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import in.museon.assignment.R;


/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */

public class ActivityTransitions {

    public  void activity_front_anim_slide_in(AppCompatActivity activity) {

        //activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void activity_back_anim_slide_in(AppCompatActivity activity) {

        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void activity_front_anim_push_down(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    public void activity_back_anim_push_down(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    public void activity_front_anim_push_up(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

    }

    public void activity_back_anim_push_up(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    public void activity_front_anim_slide_in(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void activity_back_anim_slide_in(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void activity_front_anim_slide_out(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void activity_back_anim_slide_out(Activity activity) {
        // TODO Auto-generated method stub
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
