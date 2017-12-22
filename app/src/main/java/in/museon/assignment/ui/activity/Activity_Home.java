package in.museon.assignment.ui.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.museon.assignment.R;
import in.museon.assignment.app.App;
import in.museon.assignment.data.Constants;
import in.museon.assignment.data.cash.MasterCash;
import in.museon.assignment.data.domian.User;
import in.museon.assignment.data.rest.entity.response.Response_Profile;
import in.museon.assignment.service.eventbus.EventSelectedUser;
import in.museon.assignment.ui.activity.base.BaseActivity;
import in.museon.assignment.ui.adpater.Adapter_ContactList;
import in.museon.assignment.ui.dialog.ProgressDialogHelper;
import in.museon.assignment.ui.dialog.ToastHelper;
import in.museon.assignment.ui.view.LayoutManager_Vertical;
import in.museon.assignment.ui.view.SpaceItemDecoration;
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
public class Activity_Home extends BaseActivity {


    @BindView(R.id.iv_home_profile)
    ImageView ivHomeProfile;
    @BindView(R.id.rv_common_list)
    RecyclerView rvCommonList;
    @BindView(R.id.rv_common_error_msg)
    TextView rvCommonErrorMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setUpToolbar();
        setToolBarVisibility(true);
        if (MasterCash.userArrayList.size() <= 0) {
            getAllUserFromServer();
        } else {
            App.getUtil_factory().getImageUtil().loadImage(this, getAppPreference().getUserData().getProfile_pic(), ivHomeProfile);

            initializeRecycler();

        }


    }


    public void getUserList() {
        if (MasterCash.userArrayList.size() > 0) {
            if (rvCommonErrorMsg.getVisibility() == View.VISIBLE) {
                rvCommonErrorMsg.setVisibility(View.INVISIBLE);
            }
            initializeRecycler();
        } else {
            rvCommonErrorMsg.setText(getResources().getText(R.string.error_no_mutual));
            rvCommonErrorMsg.setVisibility(View.VISIBLE);
        }

    }

    public void initializeRecycler() {
        rvCommonList.setLayoutManager(new LayoutManager_Vertical(this));
        rvCommonList.setHasFixedSize(true);
        rvCommonList.addItemDecoration(new SpaceItemDecoration(10));
        setAdapter(MasterCash.userArrayList);

    }


    private void getAllUserFromServer() {

        ProgressDialogHelper.showProgressDialog(this);

        Call<Response_Profile> request_profileList = App.getApiClient().getAllProfileList();
        request_profileList.enqueue(new Callback<Response_Profile>() {
            @Override
            public void onResponse(@NonNull Call<Response_Profile> call, @NonNull Response<Response_Profile> response) {
                ProgressDialogHelper.hideProgressDialog();


                if (response.isSuccessful()) {
                    Response_Profile response_profile = response.body();
                    if (response_profile != null) {
                        if (response_profile.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {

                            ToastHelper.show(response_profile.getMsg());
                            setUpProfile(response_profile.getList());
                            setUpUser(response_profile.getList());


                        } else if (response_profile.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            ToastHelper.show(response_profile.getMsg());
                        }
                    } else {
                        ToastHelper.show(Constants.API_MESSAGE_UNABLE_TO_REACH);
                    }


                } else {
                    ToastHelper.show(Constants.API_MESSAGE_UNABLE_TO_REACH);
                }

            }


            @Override
            public void onFailure(@NonNull Call<Response_Profile> call, @NonNull Throwable t) {
                ProgressDialogHelper.hideProgressDialog();
                ToastHelper.show(Constants.API_MESSAGE_UNABLE_TO_REACH);
            }
        });


    }


    private void setUpUser(ArrayList<User> allUser) {
        setUpProfile(allUser);
        ArrayList<String> allCont = getAllContact();
        for (User user : allUser) {
            for (String s : allCont) {
                if (s != null) {
                    if (s.contains(user.getMobile())) {
                        MasterCash.userArrayList.add(user);
                    }
                }

            }
        }
        getUserList();
    }


    private void setUpProfile(ArrayList<User> allUser) {

        User user = getAppPreference().getUserData();
        for (User allUsers : allUser) {
            if (allUsers.getMobile().equalsIgnoreCase(user.getMobile())) {
                getAppPreference().setUserData(allUsers);
                App.getUtil_factory().getImageUtil().loadImage(this, allUsers.getProfile_pic(), ivHomeProfile);
                break;
            }
        }
    }


    private void setAdapter(ArrayList<User> userArrayList) {

        Adapter_ContactList adapter_ContactList = new Adapter_ContactList(userArrayList, this);
        rvCommonList.setAdapter(adapter_ContactList);
        adapter_ContactList.notifyDataSetChanged();
    }


    @Override
    protected AppCompatActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected int getFragmentContainer() {
        return 0;
    }

    public ArrayList<String> getAllContact() {

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER},
                null, null, null);

        ArrayList<String> contacts = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    contacts.add(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)));
                } while (c.moveToNext());
            }
        }


        if (c != null) {
            c.close();
        }
        return contacts;
    }

    @Subscribe
    public void eventGotoHome(EventSelectedUser event) {
        getNavigator().goToSelectedUser(this, Constants.MOVEMENT_FRONT);
    }

    @OnClick(R.id.iv_home_profile)
    public void onViewClicked() {
        getNavigator().goToSelectedUser(this, Constants.MOVEMENT_FRONT);
    }

    @Override
    public void onBackPressed() {
        doubleTapExit();
    }
}
