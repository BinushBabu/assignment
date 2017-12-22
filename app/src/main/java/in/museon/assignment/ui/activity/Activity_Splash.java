package in.museon.assignment.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import in.museon.assignment.R;
import in.museon.assignment.data.Constants;
import in.museon.assignment.service.eventbus.EventSignUp;
import in.museon.assignment.ui.activity.base.BaseActivity;

/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class Activity_Splash extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashFinish();

    }

    public void splashFinish() {
        int splash_time_out = 2 * 1000;
        Handler dd = new Handler();
        dd.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoSignUp(new EventSignUp());
            }
        }, splash_time_out);
    }

    @Subscribe
    public void gotoSignUp(EventSignUp eventSignUp) {
        if (getAppPreference().isLogged()) {
            getNavigator().goToHome(Activity_Splash.this, Constants.MOVEMENT_FRONT);
        } else {
            getNavigator().goToSignUp(Activity_Splash.this, Constants.MOVEMENT_FRONT);
        }


    }

    @Override
    protected AppCompatActivity getCurrentActivity() {
        return Activity_Splash.this;
    }

    @Override
    protected int getFragmentContainer() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        doubleTapExit();
    }
}
