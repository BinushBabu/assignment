package in.museon.assignment.ui.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.widget.ProgressBar;
import in.museon.assignment.R;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * @author dev.cob
 * @since 8 oct 2016
 * @version 1.0
 *
 * The type Progress dialog helper.
 */
public class ProgressDialogHelper {

    private static ProgressBar progressBar;
    private static AppCompatDialog dialog = null;

    /**
     * Show progress dialog.
     *
     * @param context the context
     */
    public static void showProgressDialog(Context context) {
        dialog = new AppCompatDialog(context,
                R.style.ProgressDialogStyle);
        dialog.setContentView(R.layout.dialog_progress);
        progressBar = (ProgressBar) dialog.findViewById(R.id.indeterminate_progress);
        progressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(context));
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * Hide progress dialog.
     */
    public static void hideProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            progressBar = null;
            dialog = null;
        }
    }
}
