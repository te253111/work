package com.myproject.repaircar.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Semicolon07 on 1/24/2017 AD.
 */

public class GalleryDispatcher {
    private Uri photoUri;
    private Context context;
    public static final int REQUEST_CODE_CAMERA = 1001;
    public static final int REQUEST_CODE_GALLERY = 1002;

    public GalleryDispatcher(Context context) {
        this.context = context;
    }

    public void dispatchTakePictureIntent(Fragment fragment, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName()+ ".provider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                fragment.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }
    public void dispatchTakePictureIntent(Activity activity, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri _providerUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()+ ".provider",photoFile);
                photoUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, _providerUri);
                activity.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }
    public void dispatchGalleryIntent(Activity activity, int requestCode) {
        /*Intent selectFileIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
        Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);

        //if (selectFileIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
        try {
            selectFileIntent.setType("image/*");
            selectFileIntent.putExtra("CONTENT_TYPE", "image/*");
            selectFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            activity.startActivityForResult(
                    Intent.createChooser(selectFileIntent, "Select File"),
                    requestCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //}
    }

    public void dispatchGalleryIntent(Fragment fragment, int requestCode) {
        /*Intent selectFileIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
        Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);

        //if (selectFileIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
        try {
            selectFileIntent.setType("image/*");
            selectFileIntent.putExtra("CONTENT_TYPE", "image/*");
            selectFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            fragment.startActivityForResult(
                    Intent.createChooser(selectFileIntent, "Select File"),
                    requestCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //}
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Repair a file: path for use with ACTION_VIEW intents
        return image;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public Bitmap getPhotoBitmap() {
        Uri resultUri = photoUri;
        context.getContentResolver().notifyChange(resultUri, null);
        ContentResolver cr = context.getContentResolver();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, resultUri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
