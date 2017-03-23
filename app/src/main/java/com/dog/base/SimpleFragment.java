package com.dog.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dog.event.DummyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 无MVP的Fragment基类
 * Fragment加载数据规则：
 * App一启动会依次执行首页四个Fragment的onCreate(),并且执行第一个Fragment的onLazyInitView().
 * 当用户点击其他Tab,才会执行其他Fragment的onLazyInitView(),该方法只会在首页的四个Fragment执行一次.
 * 在Viewpager里面的Fragment中。会加载第一页和下一页的Fragment的onCreate(),并且执行第一个Fragment的onLazyInitView().
 * 当用户滑动到了第二页会执行第三页的onCreate()，执行第二页的onLazyInitView().
 * 注意的是，在Viewpager中的Fragment中的onLazyInitView()会重复执行.
 */
public abstract class SimpleFragment extends SupportFragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 该方法不执行，只是让Event编译通过
     */
    @Subscribe
    public void dummy(DummyEvent event) {
    }
}
