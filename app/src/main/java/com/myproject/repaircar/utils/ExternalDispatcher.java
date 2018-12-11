package com.myproject.repaircar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.myproject.repaircar.R;
import com.unigainfo.android.meview.permission.PermissionEnum;
import com.unigainfo.android.meview.permission.PermissionManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Semicolon07 on 8/20/2017 AD.
 */

public final class ExternalDispatcher {
    private static final int REQUEST_CODE_TAKE_PICTURE = 10001;
    private static final int REQUEST_CODE_GALLERY = 10002;

    private Uri fileUri;
    private Context context;
    private Uri takePictureFileUri;
    private ExternalDispatcher.PictureSourceCallback pictureSourceCallback;
    private String mCurrentTakePhotoPath;
    private final Object caller;

    public static ExternalDispatcher of(Fragment fragment) {
        return new ExternalDispatcher(fragment);
    }

    public static ExternalDispatcher of(Activity activity) {
        return new ExternalDispatcher(activity);
    }

    private ExternalDispatcher(Object caller) {
        this.caller = caller;
        if (this.caller instanceof Fragment) {
            Context ctx = ((Fragment) this.caller).getContext();
            this.context = ctx;
        }

        if (this.caller instanceof Activity) {
            this.context = (Context) this.caller;
        }

    }


    public final void intentTakePicture(ExternalDispatcher.PictureSourceCallback callback) {
        this.pictureSourceCallback = callback;
        PermissionManager permissionManager = PermissionManager.Builder()
                .key(REQUEST_CODE_TAKE_PICTURE)
                .permission(PermissionEnum.CAMERA, PermissionEnum.WRITE_EXTERNAL_STORAGE);
        if (this.caller instanceof Fragment) {
            permissionManager.callback(allPermissionsGranted -> {
                if(allPermissionsGranted) intentTakePictureAfterRequestPermission();
            }).ask((Fragment) this.caller);
        }

        if (this.caller instanceof Activity) {
            permissionManager.callback(allPermissionsGranted -> {
                if(allPermissionsGranted) intentTakePictureAfterRequestPermission();
            }).ask((Activity) this.caller);
        }

    }

    private final void intentTakePictureAfterRequestPermission() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = this.createImageFile();
            } catch (IOException var4) {
                var4.printStackTrace();
            }

            if (photoFile != null) {
                this.takePictureFileUri = Uri.parse(this.mCurrentTakePhotoPath);
                StringBuilder sb = new StringBuilder();
                Uri photoURI = FileProvider.getUriForFile(context, sb.append(context.getApplicationContext().getPackageName()).append(".provider").toString(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                this.start(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
            }
        }
    }


    public final void intentGallery(ExternalDispatcher.PictureSourceCallback callback) {
        this.pictureSourceCallback = callback;
        PermissionManager permissionManager = PermissionManager.Builder()
                .key(REQUEST_CODE_GALLERY)
                .permission(PermissionEnum.READ_EXTERNAL_STORAGE);
        if (this.caller instanceof Fragment) {
            permissionManager.callback(it -> {
                if (it) {
                    ExternalDispatcher.this.intentGalleryAfterRequestPermission();
                }

            }).ask((Fragment) this.caller);
        }

        if (this.caller instanceof Activity) {
            permissionManager.callback(it -> {
                if (it) {
                    ExternalDispatcher.this.intentGalleryAfterRequestPermission();
                }

            }).ask((Activity) this.caller);
        }

    }

    private final void intentGalleryAfterRequestPermission() {
        Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);

        try {
            selectFileIntent.setType("image/*");
            selectFileIntent.putExtra("CONTENT_TYPE", "image/*");
            selectFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            Intent intent = Intent.createChooser(selectFileIntent, "Select File");
            this.start(intent, REQUEST_CODE_GALLERY);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public final void createPictureSourcePopup(Context context, final ExternalDispatcher.PictureSourceCallback callback) {
        List<String> mSources = new ArrayList<>();
        mSources.add(context.getString(R.string.menu_popup_camera));
        mSources.add(context.getString(R.string.menu_popup_gallery));

        //Create sequence of items
        final CharSequence[] Animals = mSources.toArray(new String[mSources.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.title_choose_picture_from));
        dialogBuilder.setItems(Animals, (dialogInterface, index) -> {
            if(index == 0) ExternalDispatcher.this.intentTakePicture(callback);
            if(index == 1) ExternalDispatcher.this.intentGallery(callback);
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public final void createChoosePictureOrViewPopup(Context context, String url, final ExternalDispatcher.PictureSourceCallback callback){
        List<String> mViewOrChooseList = new ArrayList<>();
        mViewOrChooseList.add(context.getString(R.string.menu_popup_view_picture));
        mViewOrChooseList.add(context.getString(R.string.menu_popup_choose_picture));

        //Create sequence of items
        final CharSequence[] Animals = mViewOrChooseList.toArray(new String[mViewOrChooseList.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.title_picture_action_title));
        dialogBuilder.setItems(Animals, (dialogInterface, index) -> {
            if(index == 0) this.openPictureViewer(context,url);
            if(index == 1) this.createPictureSourcePopup(context,callback);
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    private final void openPictureViewer(Context context, String url){
        //Navigator.getInstance().navigateToFullScreenPicture(context,url);
    }

    public final void processResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        ExternalDispatcher.PictureSourceCallback callback;
        if (requestCode == REQUEST_CODE_GALLERY) {
            this.fileUri = data != null ? data.getData() : null;
            callback = this.pictureSourceCallback;
            if (this.pictureSourceCallback != null) {
                callback.onCompleted(this.fileUri);
            }
        }

        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            this.fileUri = this.takePictureFileUri;
            callback = this.pictureSourceCallback;
            if (this.pictureSourceCallback != null) {
                callback.onCompleted(this.takePictureFileUri);
            }
        }
    }


    private final void start(Intent intent, int requestCode) {
        if (this.caller instanceof Fragment) {
            ((Fragment) this.caller).startActivityForResult(intent, requestCode);
        }
        if (this.caller instanceof Activity) {
            ((Activity) caller).startActivityForResult(intent, requestCode);
        }

    }

    private final File createImageFile() throws IOException {
        String timeStamp = (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        this.mCurrentTakePhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public interface PictureSourceCallback {
        void onCompleted(Uri uri);

        void onRemove();
    }

}
