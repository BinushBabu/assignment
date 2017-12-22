package in.museon.assignment.service.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import in.museon.assignment.data.Constants;
import in.museon.assignment.ui.activity.Activity_Home;
import in.museon.assignment.ui.activity.Activity_SignUp;
import in.museon.assignment.ui.animations.ActivityTransitions;
import in.museon.assignment.ui.activity.Activity_UserProfile;


/**
 * @author dev.Cobb
 * @version 1.0
 * @since BaseActivity
 */
public class Navigator {

   private ActivityTransitions transition;
    public Navigator(){
        transition=new ActivityTransitions();
    }

public void goToSignUp(AppCompatActivity sourceClass, int movement) {
        sourceClass.startActivity(new Intent(sourceClass, Activity_SignUp.class));
        sourceClass.finish();
        animationForActivityTransition(sourceClass,movement );
    }
    public void goToHome(AppCompatActivity sourceClass, int movement) {
        sourceClass.startActivity(new Intent(sourceClass, Activity_Home.class));
        sourceClass.finish();
        animationForActivityTransition(sourceClass,movement );
    }
    public void goToSelectedUser(AppCompatActivity sourceClass, int movement) {
        sourceClass.startActivity(new Intent(sourceClass, Activity_UserProfile.class));
        sourceClass.finish();
        animationForActivityTransition(sourceClass,movement );
    }


    private void animationForActivityTransition(AppCompatActivity activity, int movement){
        switch (movement){
                case Constants.MOVEMENT_FRONT :
                transition.activity_front_anim_slide_out(activity);
                break;
            case Constants.MOVEMENT_BACK :
                transition.activity_back_anim_slide_out(activity);
                break;

            default:
                break;
        }
    }

}
