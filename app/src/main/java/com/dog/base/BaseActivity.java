package com.dog.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dog.app.App;
import com.dog.dagger.componet.ActivityComponent;
import com.dog.dagger.componet.DaggerActivityComponent;
import com.dog.dagger.module.ActivityModule;
import com.dog.event.DummyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * MVP的Activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        App.getInstance().addActivity(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        App.getInstance().removeActivity(this);
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initInject();

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
