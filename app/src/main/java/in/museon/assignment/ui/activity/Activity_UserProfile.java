package in.museon.assignment.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.museon.assignment.R;
import in.museon.assignment.app.App;
import in.museon.assignment.data.Constants;
import in.museon.assignment.data.domian.User;
import in.museon.assignment.data.rest.entity.response.Response_Upload;
import in.museon.assignment.service.eventbus.EventCamera;
import in.museon.assignment.service.eventbus.EventSignUp;
import in.museon.assignment.ui.activity.base.BaseActivity;
import in.museon.assignment.ui.dialog.DialogImagePicker;
import in.museon.assignment.ui.dialog.ProgressDialogHelper;
import in.museon.assignment.ui.dialog.ToastHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * The Class WizardOneFragment.
 *
 * @author Nidhin joy
 * @version 1.0
 * @since 2016-04-15
 */
public class Activity_UserProfile extends BaseActivity {


    @BindView(R.id.iv_selected_user_profile)
    ImageView ivSelectedUserProfile;
    @BindView(R.id.tv_selected_user_name)
    TextView tvSelectedUserName;
    @BindView(R.id.tv_selected_user_contact)
    TextView tvSelectedUserContact;
    /* @BindView(R.id.rv_common_list)
     RecyclerView rvCommonList;
     @BindView(R.id.rv_common_error_msg)
     TextView rvCommonErrorMsg;*/
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selected_user);
        ButterKnife.bind(this);
        setUpToolbar();
        setToolBarVisibility(true);
        initializeUi();
    }

    protected void initializeUi() {
        User user;
        if (Constants.IS_FROM_USER) {
            Constants.IS_FROM_USER = false;
            floatingActionButton.setVisibility(View.INVISIBLE);
            user = Constants.SELECTED_USER;

        } else {
            user = getAppPreference().getUserData();
        }
        tvSelectedUserName.setText(user.getName());
        tvSelectedUserContact.setText(user.getMobile());
        App.getUtil_factory().getImageUtil().loadImage(this, user.getProfile_pic(), ivSelectedUserProfile);
    }


    @Override
    protected AppCompatActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected int getFragmentContainer() {
        return 0;
    }

    @Subscribe
    public void DemoEvent(EventSignUp eventSignUp) {
        ToastHelper.show("sample Subscribe");
    }

    @Override
    public void onBackPressed() {
        getNavigator().goToHome(Activity_UserProfile.this, Constants.MOVEMENT_BACK);
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        DialogImagePicker.selectImage(Activity_UserProfile.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe
    public void goTo(EventCamera eventCamera) {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (eventCamera.getRequestCamera() == Constants.REQUEST_CAMERA) {
                App.getUtil_factory().getImageUtil().imageTakeFromCamera(Activity_UserProfile.this);
            } else {
                App.getUtil_factory().getImageUtil().imageTakeFromGallery(Activity_UserProfile.this);
            }
        } else {
            check_SD_CARD_Permission();
        }
    }

    /*check read and write permission */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void check_SD_CARD_Permission() {
        checkPermissionGraded(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CAMERA || requestCode == Constants.REQUEST_SELECT_FILE) {
            if (App.getUtil_factory().getImageUtil().imageResult(requestCode, data)) {
                if (Constants.BITMAP != null) {

                    upLoadFile();
                }
            }
        }
    }

    private void upLoadFile() {
        ProgressDialogHelper.showProgressDialog(this);
        Call<Response_Upload> response_uploadCall = getAPIClient().uploadImage(getRequestFile(), getAppPreference().getUserData().getMobile());
        response_uploadCall.enqueue(new Callback<Response_Upload>() {
            @Override
            public void onResponse(@NonNull Call<Response_Upload> call, @NonNull Response<Response_Upload> response) {
                ProgressDialogHelper.hideProgressDialog();

                if (response.isSuccessful()) {
                    Response_Upload response_upload = response.body();
                    if (response_upload != null) {
                        if (response_upload.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {

                            ToastHelper.show(response_upload.getMsg());

                        } else if (response_upload.getStatus().equalsIgnoreCase(Constants.EXIST)) {
                            ToastHelper.show(response_upload.getMsg());

                        }
                    } else {
                        ToastHelper.show("Registration Failed ..try again");
                    }

                } else {
                    ToastHelper.show("Registration Failed ..try again");
                }

            }

            @Override
            public void onFailure(@NonNull Call<Response_Upload> call, @NonNull Throwable t) {
                ProgressDialogHelper.hideProgressDialog();
                ToastHelper.show("Uploading Failed ..try again");
            }
        });
    }

    private MultipartBody.Part getRequestFile() {
        File file = App.getUtil_factory().getFileUtil().getFile(Constants.UploadFilePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), reqFile);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_id_logout:
                getAppPreference().isLogged(false);
                getAppPreference().setUserData(new User());
                getNavigator().goToSignUp(Activity_UserProfile.this, Constants.MOVEMENT_BACK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
