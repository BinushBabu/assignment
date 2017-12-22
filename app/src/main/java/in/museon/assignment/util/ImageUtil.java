package in.museon.assignment.util;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import in.museon.assignment.R;
import in.museon.assignment.app.App;
import in.museon.assignment.data.Constants;


public class ImageUtil {


    public void changeDrawableColor(ImageView imageView, String color,int drawableRecourse){
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor(color),
                PorterDuff.Mode.SRC_ATOP);
        imageView.setColorFilter(porterDuffColorFilter);
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); // bitmap is the
        // bitmap object
        byte[] bArray = baos.toByteArray();
        return bArray;
    }

    public String convertByteArrayToBase64String(byte[] byteArray) {
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap convertBase64StringToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
        return bitmap;
    }

    public Bitmap decodeSampledBitmapFromResource(String imagePath,
                                                  int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Uri uri = Uri.parse(imagePath);

        BitmapFactory.decodeFile(uri.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = true;
        return BitmapFactory.decodeFile(uri.getPath(), options);
    }
    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 3;
            }
        }
        return inSampleSize;
    }
    public String getImagePathFromURI(Uri uri) {


        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(App.getContext(),
                uri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);


        return selectedImagePath;
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        try {

            Picasso.with(context).load(url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).into(imageView);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveInToSdCard(ByteArrayOutputStream bytes) {

        File destination = new File(App.getUtil_factory().getFileUtil().getWorkImageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Constants.UploadFilePath = destination.getAbsolutePath();
        FileOutputStream fo;

        try {
            boolean result=destination.createNewFile();
            if(result){
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            }else {
                //todo inform issue to user
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean imageResult(int requestCode, Intent data) {
        boolean isDone = true;
        try {

            if (requestCode == Constants.REQUEST_CAMERA) {
                Bitmap thumbnail = null;
                if (data.getExtras() != null) {
                    thumbnail = (Bitmap) data.getExtras().get("data");
                } else {
                    //todo inform issue to user
                }

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                if (thumbnail != null) {
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    Constants.BITMAP = thumbnail;
                    saveInToSdCard(bytes);
                }

            } else if (requestCode == Constants.REQUEST_SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                final int REQUIRED_SIZE = 200;
                Constants.UploadFilePath = getImagePathFromURI(selectedImageUri);
                Constants.BITMAP = decodeSampledBitmapFromResource(Constants.UploadFilePath, REQUIRED_SIZE, REQUIRED_SIZE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Constants.BITMAP.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            }
        } catch (Exception e) {
            isDone = false;
        }
        return isDone;

    }

    public void imageTakeFromGallery(AppCompatActivity activity) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"),
                Constants.REQUEST_SELECT_FILE);

    }

    public void imageTakeFromCamera(AppCompatActivity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }
}
