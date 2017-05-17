package com.dog.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.dog.BuildConfig;
import com.dog.R;
import com.dog.base.SimpleActivity;
import com.dog.util.Utils;

/**
 * 启动页面
 */

public class LauncherActivity extends SimpleActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(layoutResID);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView() {
        String id = Utils.getChannelID(mContext);
        Log.d("abc", "ChannelID-->" + id);

        if (BuildConfig.DEBUG) {
            Log.d("abc", "测试包-->");
        } else {
            Log.d("abc", "正式包-->");
        }

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }
}
