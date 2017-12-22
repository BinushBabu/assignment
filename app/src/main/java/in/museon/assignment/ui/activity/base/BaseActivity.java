package in.museon.assignment.ui.activity.base;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.museon.assignment.R;
import in.museon.assignment.app.App;
import in.museon.assignment.data.Constants;
import in.museon.assignment.data.cash.MasterCash;
import in.museon.assignment.data.preferences.AppPreference;
import in.museon.assignment.data.rest.ApiClient;
import in.museon.assignment.service.navigation.Navigator;
import in.museon.assignment.ui.dialog.DialogCallback;
import in.museon.assignment.ui.dialog.DialogParams;
import in.museon.assignment.ui.dialog.DialogType;
import in.museon.assignment.ui.dialog.ToastHelper;


/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 *
 *  Abstract android activity common for all activities. Every activity extends
 * this abstract class. A common Tool bar is designed in this activity. Common
 * functions for activities can be created here.
 *
 */
public  abstract  class BaseActivity extends AppCompatActivity implements Base{

    protected abstract AppCompatActivity getCurrentActivity();


    private boolean doubleBackToExitPressedOnce = false;
    private Toolbar toolbar;
    private Menu menu = null;
    private Unbinder unbinder;
    private AppCompatActivity activity;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private  ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();


    @Override
    public void setUpToolbar() {
        toolbar =  findViewById(R.id.toolbar_id);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
    /**
     * Gets the current fragment.
     * <p/>
     * the fragment container id
     *
     * @return the current fragment
     */
    protected android.support.v4.app.Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(
                getFragmentContainer());
    }
    @Override
    public void setLogoInToolbar(int resourceId) {
        toolbar.setLogo(resourceId);
    }

    @Override
    public void setToolbarColor(int color) {
        toolbar.setBackgroundColor(color);
    }
    protected void replaceFragment(Fragment fragment, boolean addToBackStack, int animationMod) {
        // If the required fragment is already shown - do nothing
        // if (isFragmentShown(fragment)) {
        // return;
        // }


        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        switch (animationMod){

            case Constants.ANIM_UP:
                fragmentTransaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
                break;
            case  Constants.ANIM_DOWN:
                fragmentTransaction.setCustomAnimations(R.anim.push_down_in,R.anim.push_down_out);
                break;
            case Constants.ANIM_RIGHT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case  Constants.ANIM_LEFT:
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

            default:
                break;

        }

        if (addToBackStack) {
            // Add this transaction to the back stack

            fragmentTransaction.addToBackStack(fragment
                    .getClass().getSimpleName());
            fragmentTransaction.replace(getFragmentContainer(), fragment, fragment
                    .getClass().getSimpleName());
        }
        else{
            fragmentTransaction.replace(getFragmentContainer(), fragment, fragment
                    .getClass().getSimpleName());
        }
        fragmentTransaction.commit();


        // Change to a new fragment

    }

    @Override
    public void setToolBarVisibility(boolean visibility) {
        try {
            if (getSupportActionBar()!=null){
            if (visibility){
                getSupportActionBar().show();
            }else {
                getSupportActionBar().hide();
            }
            }

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public void setToolBarTitle(String title) {
        try {
            if (getSupportActionBar()!=null){
        getSupportActionBar().setTitle(title);}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAppTitleVisibility(boolean visibility) {

        try {
            if (getSupportActionBar()!=null){
                getSupportActionBar().setDisplayShowTitleEnabled(visibility);}
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void toggleActionBarMenuVisibility(boolean visible) {
        if (menu != null) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(visible);
            }
        }
    }

    @Override
    public void injectViews(AppCompatActivity activity) {
        unbinder = ButterKnife.bind(activity);
    }

    @Override
    public void destroyViews() {
        unbinder.unbind();
    }

    @Override
    public void showAlertDialog(final DialogParams params,
                                   final DialogCallback dgCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(params.getTitle());
        builder.setMessage(params.getMessage());
        builder.setCancelable(params.isCancelable());
        builder.setPositiveButton(params.getPositive(),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (dgCallback != null) {
                            dgCallback.onButtonPositive(params.getDialogId());
                        }
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(params.getNegative(),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (dgCallback != null) {
                            dgCallback.onButtonNegative(params.getDialogId());
                        }
                        dialog.dismiss();
                    }
                });

        builder.setNeutralButton(params.getNeutral(),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (dgCallback != null) {
                            dgCallback.onButtonNeutral(params.getDialogId());
                        }
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();


        if (params.getDgType() == DialogType.DG_POS_NEG) {
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setVisibility(
                    View.INVISIBLE);
        } else if (params.getDgType() == DialogType.DG_POS_ONLY) {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(
                    View.INVISIBLE);
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setVisibility(
                    View.INVISIBLE);
        }
    }


    //Exiting Application

    @Override
    public void doubleTapExit() {
        App.getMasterCash().clearAllMasterCash();
        if (doubleBackToExitPressedOnce) {
            //  dataProcessController.getMasterDataCache().clearCache();
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        ToastHelper.show(getResources().getString(R.string.msg_exit));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //permission checking

    public boolean hasPermission(String... permissions) {
        if (canAskPermission()) {
            for(String permission:permissions){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
                }}
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:

                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (activity.shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            String msg = "These permissions are mandatory for the application. Please allow access.";
                            showMessageOKCancel(msg,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void checkPermissionGraded(String... request_permissions){

        activity=getCurrentActivity();
        ArrayList<String> wanted=new ArrayList<>();
        for (String request_permission : request_permissions) {
            if (!hasPermission(request_permission)) {
                wanted.add(request_permission);
            }
        }
        permissionsToRequest=wanted;

         if (permissionsToRequest.size() > 0) {
            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                    ALL_PERMISSIONS_RESULT);
        }
    }

    public MasterCash getMasterCash(){
        return  App.getMasterCash();
    };
    public Navigator getNavigator() {
        return App.getNavigator();
    }
    public ApiClient getAPIClient() {
        return App.getApiClient();
    }
    public AppPreference getAppPreference() {
        return App.getAppPreference();
    }


    protected  void  startEventBus(){
        if(!EventBus.getDefault().isRegistered(getCurrentActivity())){
            EventBus.getDefault().register(getCurrentActivity());}
    }
    protected  void  stopEventBus() {


        EventBus.getDefault().unregister(getCurrentActivity());
    }
    protected  void  postEvent( Object event) {
        EventBus.getDefault().post(event);
    }
    protected abstract int getFragmentContainer();
    @Override
    protected void onResume() {
        startEventBus();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopEventBus();
        super.onDestroy();
    }

    protected void showBackButton(boolean enable) {


        if (enable) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                   } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }
    }
}