package in.museon.assignment.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import in.museon.assignment.R;
import in.museon.assignment.app.App;
import in.museon.assignment.data.Constants;
import in.museon.assignment.data.domian.User;
import in.museon.assignment.data.rest.entity.response.Response_Reg;
import in.museon.assignment.service.eventbus.EventCamera;
import in.museon.assignment.service.eventbus.EventSignUp;
import in.museon.assignment.ui.activity.base.BaseActivity;
import in.museon.assignment.ui.dialog.DialogImagePicker;
import in.museon.assignment.ui.dialog.ProgressDialogHelper;
import in.museon.assignment.ui.dialog.ToastHelper;
import in.museon.assignment.ui.view.TextInputLayoutAdapter;
import in.museon.assignment.util.CredentialValidator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_SignUp extends BaseActivity implements Validator.ValidationListener {

    @NotEmpty(messageResId = R.string.null_input)
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @NotEmpty(messageResId = R.string.null_input)
    @BindView(R.id.til_mobile)
    TextInputLayout tilMobile;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.iv_profile_image)
    ImageView ivProfileImage;

    private String name, mobile;
    private Validator validator = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setUpToolbar();
        injectViews(this);
        doTask();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void doTask() {
        check_SD_CARD_Permission();
        Constants.BITMAP = null;
        setUpValidator();
        App.getUtil_factory().getFileUtil().createDirectory();
    }

    /*set up input  validator */
    private void setUpValidator() {
        validator = new Validator(this);
        validator.registerAdapter(TextInputLayout.class, new TextInputLayoutAdapter());
        validator.setValidationListener(this);
    }

    /*check read and write permission */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void check_SD_CARD_Permission() {
        checkPermissionGraded(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected AppCompatActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected int getFragmentContainer() {
        return 0;
    }

    /*set up onclick */
    @OnClick({R.id.bt_start, R.id.iv_profile_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                getInputData();
                validator.validate();
                break;
            case R.id.iv_profile_image:
                DialogImagePicker.selectImage(Activity_SignUp.this);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe
    public void goTo(EventCamera eventCamera) {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (eventCamera.getRequestCamera() == Constants.REQUEST_CAMERA) {
                App.getUtil_factory().getImageUtil().imageTakeFromCamera(Activity_SignUp.this);
            } else {
                App.getUtil_factory().getImageUtil().imageTakeFromGallery(Activity_SignUp.this);
            }
        } else {
            check_SD_CARD_Permission();
        }
    }

    ///////////////validation callback start
    @Override
    public void onValidationSucceeded() {
        boolean isValid = validateInputs();
        if (isValid) {
            wb_startRegisterService();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError validationError : errors) {
            View view = validationError.getView();
            String errorMessage = validationError.getCollatedErrorMessage(this);
            if (view instanceof TextInputLayout) {
                CredentialValidator.setTextInputError((TextInputLayout) view, errorMessage);
            }
        }
    }

    /*   find input from views*/
    private void getInputData() {
        name = tilName.getEditText().getText().toString();
        mobile = tilMobile.getEditText().getText().toString();
    }

    /*  validate name mobile and profile pic */
    private boolean validateInputs() {
        boolean isValid = true;
        if (!CredentialValidator.isValidName(tilName)) {
            isValid = false;
        }
        if (!CredentialValidator.isValidPhoneNumber(tilMobile)) {
            isValid = false;
        }
        if (Constants.BITMAP == null) {
            isValid = false;
            ToastHelper.show("please add a profile image");
        }
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CAMERA || requestCode == Constants.REQUEST_SELECT_FILE) {
            if (App.getUtil_factory().getImageUtil().imageResult(requestCode, data)) {
                if (Constants.BITMAP != null) {
                    File file = new File(Constants.UploadFilePath);
                    Uri uri = Uri.fromFile(file);
                    ivProfileImage.setImageURI(uri);
                    // ivProfileImage.setImageBitmap(Constants.BITMAP);
                }
            }
        }
    }

    private void wb_startRegisterService() {

        ProgressDialogHelper.showProgressDialog(this);

        Call<Response_Reg> request_userSignUpCall = getAPIClient().regUser(getRequestFile(), name, mobile);

        request_userSignUpCall.enqueue(new Callback<Response_Reg>() {
            @Override
            public void onResponse(@NonNull Call<Response_Reg> call, @NonNull Response<Response_Reg> response) {
                ProgressDialogHelper.hideProgressDialog();


                if (response.isSuccessful()) {
                    Response_Reg response_reg = response.body();
                    if (response_reg != null) {
                        if (response_reg.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {

                            ToastHelper.show(response_reg.getMsg());
                            getAppPreference().isLogged(true);
                            User user = new User();
                            user.setName(name);
                            user.setMobile(mobile);
                            getAppPreference().setUserData(user);

                            getNavigator().goToHome(getCurrentActivity(), Constants.MOVEMENT_FRONT);
                        } else if (response_reg.getStatus().equalsIgnoreCase(Constants.EXIST)) {
                            ToastHelper.show(response_reg.getMsg());

                        }
                    } else {
                        ToastHelper.show("Registration Failed ..try again");
                    }

                } else {
                    ToastHelper.show("Registration Failed ..try again");
                }

            }

            @Override
            public void onFailure(@NonNull Call<Response_Reg> call, @NonNull Throwable t) {
                ProgressDialogHelper.hideProgressDialog();
                ToastHelper.show("Registration Failed ..try again");
            }
        });


    }

    private MultipartBody.Part getRequestFile() {
        File file = App.getUtil_factory().getFileUtil().getFile(Constants.UploadFilePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), reqFile);

    }

    @Subscribe
    public void demo(EventSignUp eventSignUp) {

    }

    @Override
    public void onBackPressed() {
        doubleTapExit();
        //    super.onBackPressed();
    }
}