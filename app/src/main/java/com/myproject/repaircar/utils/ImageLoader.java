package com.myproject.repaircar.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.myproject.repaircar.R;

public class ImageLoader {

    private static RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.loading)
            .fitCenter()
            .error(R.drawable.photo_notfound)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);

    public static void load(Context context,String url, ImageView target){
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }

    public static void load(Context context, Uri uri, ImageView target){
        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(target);
    }

    public static void load(Context context, @DrawableRes int drawableResId, ImageView target){
        Glide.with(context)
                .load(drawableResId)
                .apply(options)
                .into(target);
    }
}
