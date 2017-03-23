package com.dog.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.dog.R;
import com.dog.app.App;
import com.dog.base.SimpleActivity;
import com.dog.event.BottomTabReselectEvent;
import com.dog.event.BottomTabSelectEvent;
import com.dog.event.LandscapeEvent;
import com.dog.event.PortraitEvent;
import com.dog.model.bean.TabEntity;
import com.dog.ui.fragment.LikeFragment;
import com.dog.ui.fragment.MeFragment;
import com.dog.ui.fragment.NewsFragment;
import com.dog.ui.fragment.VideoFragment;
import com.dog.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SimpleActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    @BindView(R.id.iv_bottom_shadow)
    ImageView iv_bottom_shadow;


    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private long mExitTime;

    private String[] mTitles = {"首页", "视频", "关注", "我的"};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select, R.drawable.tab_video_select,
            R.drawable.tab_like_select, R.drawable.tab_me_select};
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect, R.drawable.tab_video_unselect,
            R.drawable.tab_like_unselect, R.drawable.tab_me_unselect};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private Random mRandom = new Random();

    private SupportFragment[] mFragments = new SupportFragment[4];
    private int mTabPosition;

    private boolean isFull;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initTab();
        initFragment(null);
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化底部Tab栏
     */
    private void initTab() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }


        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showHideFragment(mFragments[position], mFragments[mTabPosition]);
                if (mTabPosition == 1 && position != 1) {//说明用户从视频Fragment切换到其他Fragment
                    EventBus.getDefault().post(new BottomTabSelectEvent());
                }
                mTabPosition = position;
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    EventBus.getDefault().post(new BottomTabReselectEvent());
                }
            }
        });

        //两位数
        tabLayout.showMsg(0, 55);
        tabLayout.setMsgMargin(0, -5, 5);

        //三位数
//        tabLayout.showMsg(1, 100);
//        tabLayout.setMsgMargin(1, -5, 5);

        //设置未读消息红点
//        tabLayout.showDot(2);
//        MsgView rtv_2_2 = tabLayout.getMsgView(2);
//        if (rtv_2_2 != null) {
//            UnreadMsgUtils.setSize(rtv_2_2, Utils.dp2px(MainActivity.this, 7.5f));
//        }

        //设置未读消息背景
//        tabLayout.showMsg(3, 5);
//        tabLayout.setMsgMargin(3, 0, 5);
//        MsgView rtv_2_3 = tabLayout.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }
    }

    /**
     * 初始化4个Fragment
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = NewsFragment.newInstance();
            mFragments[SECOND] = VideoFragment.newInstance();
            mFragments[THIRD] = LikeFragment.newInstance();
            mFragments[FOURTH] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(NewsFragment.class);
            mFragments[SECOND] = findFragment(VideoFragment.class);
            mFragments[THIRD] = findFragment(LikeFragment.class);
            mFragments[FOURTH] = findFragment(MeFragment.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isFull) {
                VideoFragment videoFragment = (VideoFragment) mFragments[SECOND];
                if (videoFragment != null) {
                    EventBus.getDefault().post(new PortraitEvent());
                    videoFragment.backFromFull();
                }
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    ToastUtil.show("再按一次返回键退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    App.getInstance().exitApp();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 收到了视频全屏的事件，隐藏掉底部tab栏
     */
    @Subscribe
    public void onLandscapeEvent(LandscapeEvent event) {
        VideoFragment videoFragment = (VideoFragment) mFragments[SECOND];
        if (videoFragment != null) {
            isFull = true;
            tabLayout.setVisibility(View.GONE);
            iv_bottom_shadow.setVisibility(View.GONE);
        }
    }

    /**
     * 收到了视频全屏的事件，显示底部tab栏
     */
    @Subscribe
    public void onPortraitEvent(PortraitEvent event) {
        VideoFragment videoFragment = (VideoFragment) mFragments[SECOND];
        if (videoFragment != null) {
            isFull = false;
            tabLayout.setVisibility(View.VISIBLE);
            iv_bottom_shadow.setVisibility(View.VISIBLE);
        }
    }

}
