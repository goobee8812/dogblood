package com.dog.ui.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.dog.R;
import com.dog.base.BaseActivity;
import com.dog.model.bean.TopBean;

import butterknife.BindView;

/**
 * Created by cuiyue on 2017/3/22.
 */

public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.wv_news_details)
    WebView wvNewsDetails;
    @BindView(R.id.iv_titlebar)
    ImageView ivTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;

    private TopBean mTopBean;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initView() {
        ivTitlebar.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTopBean = (TopBean) getIntent().getExtras().getSerializable(TopBean.class.getName());
        initTitle();
        initWebView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wvNewsDetails != null) {
            wvNewsDetails.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wvNewsDetails.clearHistory();
            wvNewsDetails.removeAllViews();
            wvNewsDetails.destroy();
            wvNewsDetails = null;
        }
    }

    @Override


    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String msg) {

    }

    private void initTitle() {
        tvTitlebar.setText(mTopBean.getKeywords());

    }

    private void initWebView() {
        setWebViewSettings();
        setWebView();
    }

    private void setWebViewSettings() {
        WebSettings webSettings = wvNewsDetails.getSettings();
        // 打开页面时， 自适应屏幕
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 便页面支持缩放
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setSupportZoom(true); //支持缩放
//        webSettings.setBuiltInZoomControls(true); // 放大和缩小的按钮，容易引发异常 http://blog.csdn.net/dreamer0924/article/details/34082687

        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private void setWebView() {
        wvNewsDetails.loadUrl(mTopBean.getFromurl());
        wvNewsDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar:
                finish();
                break;
        }
    }

}
