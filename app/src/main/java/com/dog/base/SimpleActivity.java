package com.dog.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dog.app.App;
import com.dog.event.DummyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 无MVP的Activity基类
 */
public abstract class SimpleActivity extends SupportActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        mUnBinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        App.getInstance().addActivity(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        EventBus.getDefault().unregister(this);
        App.getInstance().removeActivity(this);
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 该方法不执行，只是让Event编译通过
     */
    @Subscribe
    public void dummy(DummyEvent event) {
    }
}
