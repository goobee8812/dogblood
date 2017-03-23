package com.dog.engine;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dog.R;

public class ImageLoader {

    public static void load(Activity activity, String imageUrl, @DrawableRes int placeholder, ImageView iv) {
        Glide.with(activity).load(imageUrl).fitCenter().placeholder(placeholder).crossFade().into(iv);
    }

    public static void load(Fragment fragment, String imageUrl, @DrawableRes int placeholder, ImageView iv) {
        Glide.with(fragment).load(imageUrl).fitCenter().placeholder(placeholder).crossFade().into(iv);
    }

    public static void load(Context context, @DrawableRes int imageRes, ImageView view) {
        Glide.with(context).load(imageRes).crossFade().into(view);
    }

    public static void clear(Context context) {
        Glide.get(context).clearMemory();
    }
}
