package com.dog.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.dog.R;
import com.dog.base.BaseActivity;
import com.dog.base.SimpleActivity;
import com.dog.model.bean.TopBean;

import butterknife.BindView;

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
