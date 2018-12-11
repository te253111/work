package com.myproject.repaircar.manager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.myproject.repaircar.models.form.LoginApiForm;
import com.myproject.repaircar.net.HttpManager;
import com.myproject.repaircar.net.MyApi;
import com.myproject.repaircar.utils.FileChooserUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;

/**
 * Created by Semicolon07 on 2/17/2017 AD.
 */

public class ApiManager extends BaseDataManager {
    private MyApi api;

    public ApiManager(Context context) {
        super(context);
        api = HttpManager.getInstance().getService();
    }

    public Subscription login(final LoginApiForm.Request form, DefaultSubscriber<LoginApiForm.Response> subscriber) {
        return execute(api.login(form),subscriber);
    }


    private MultipartBody.Part createFilePart(Uri uri, String name){
        MultipartBody.Part picturePart = null;
        if(uri != null){
            Log.d("ApiManager","Uri  = "+uri);
            String picturePath = FileChooserUtils.getPath(context, uri);
            String mimeType = FileChooserUtils.getMimeType(context, uri);
            File pictureFile = new File(picturePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), pictureFile);
            picturePart = MultipartBody.Part.createFormData(name, pictureFile.getName(), requestFile);
        }
        return picturePart;
    }
}
