package in.museon.assignment.ui.adpater.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.museon.assignment.R;
import in.museon.assignment.data.Constants;
import in.museon.assignment.data.domian.User;
import in.museon.assignment.service.eventbus.EventSelectedUser;

/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class Holder_ContactList extends RecyclerView.ViewHolder {


    public User user;
    @BindView(R.id.tv_aduser_name)
    public TextView tvAduserName;
    @BindView(R.id.tv_aduser_number)
    public TextView tvAduserNumber;


    @BindView(R.id.tv_user_pic)
    public CircleImageView tvUserPic;



    public Holder_ContactList(final View itemView, final Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Constants.IS_FROM_USER=true;
                Constants.SELECTED_USER=user;
                EventBus.getDefault().post(new EventSelectedUser(user));
            }
        });


    }


}
