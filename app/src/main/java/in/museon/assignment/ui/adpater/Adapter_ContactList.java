package in.museon.assignment.ui.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.museon.assignment.R;
import in.museon.assignment.app.App;
import in.museon.assignment.data.domian.User;
import in.museon.assignment.ui.adpater.holder.Holder_ContactList;


/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class Adapter_ContactList extends RecyclerView.Adapter<Holder_ContactList> {

    private ArrayList<User> currentList;
  private   Context context;

    public Adapter_ContactList(ArrayList<User> currentList, Context context) {
        this.currentList = currentList;
        this.context = context;
    }

    @Override
    public Holder_ContactList onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.holder_user, parent, false);
        return new Holder_ContactList(itemView, context);

    }

    @Override
    public void onBindViewHolder(Holder_ContactList holder, int position) {
        User user = currentList.get(position);
        holder.user = user;
        holder.tvAduserName.setText(user.getName());
        holder.tvAduserNumber.setText(user.getMobile());
        try {
            App.getUtil_factory().getImageUtil().loadImage(context,user.getProfile_pic(),   holder.tvUserPic);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }
}
