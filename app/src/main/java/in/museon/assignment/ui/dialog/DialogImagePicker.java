package in.museon.assignment.ui.dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import org.greenrobot.eventbus.EventBus;

import in.museon.assignment.data.Constants;
import in.museon.assignment.service.eventbus.EventCamera;


public class DialogImagePicker {

	public static void selectImage(final Context context ) {
		Constants.BITMAP=null;
		Constants.UploadFilePath=null;
		final CharSequence[] items = { "Take Photo", "Choose from Gallery",
				"Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					EventBus.getDefault().post(new EventCamera(Constants.REQUEST_CAMERA));
					dialog.dismiss();
				} else if (items[item].equals("Choose from Gallery")) {
					EventBus.getDefault().post(new EventCamera(Constants.REQUEST_SELECT_FILE));
					dialog.dismiss();
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();

	}

}
