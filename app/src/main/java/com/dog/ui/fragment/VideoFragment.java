package com.dog.ui.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.dog.R;
import com.dog.base.BaseFragment;
import com.dog.event.BottomTabSelectEvent;
import com.dog.log.LogUtil;
import com.dog.model.bean.VideoBean;
import com.dog.presenter.contract.VideoContract;
import com.dog.presenter.impl.VideoPresenter;
import com.dog.ui.adapter.VideoAdapter;
import com.dog.video.GSYVideoPlayer;
import com.dog.video.listener.StandardVideoAllCallBack;
import com.dog.video.utils.CommonUtil;
import com.dog.video.utils.Debuger;
import com.dog.video.utils.ListVideoUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.xrecyclerview_video)
    XRecyclerView xrecyclerviewVideo;

    @BindView(R.id.video_full_container)
    FrameLayout video_full_container;

    @BindView(R.id.progress)
    View progress;

    private VideoAdapter mAdapter;
    private ListVideoUtil listVideoUtil;

    private LinearLayoutManager mLayoutManager;

    int lastVisibleItem;
    int firstVisibleItem;

    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listVideoUtil.releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
    }

    private void initRecyclerView() {
        if (mAdapter == null) {
            mLayoutManager = new LinearLayoutManager(mActivity);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xrecyclerviewVideo.setLayoutManager(mLayoutManager);
            xrecyclerviewVideo.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            xrecyclerviewVideo.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            xrecyclerviewVideo.setArrowImageView(R.drawable.iconfont_downgrey);
            xrecyclerviewVideo.setLoadingListener(new VideoFragment.MyLoadingListener());
            xrecyclerviewVideo.addOnScrollListener(new MyOnScrollListener());

            mAdapter = new VideoAdapter(mContext, null);

            listVideoUtil = new ListVideoUtil(mContext);
            listVideoUtil.setFullViewContainer(video_full_container);
            listVideoUtil.setHideStatusBar(true);
            listVideoUtil.setVideoAllCallBack(new MyStandardVideoAllCallBack());
            mAdapter.setListVideoUtil(listVideoUtil);

            xrecyclerviewVideo.setAdapter(mAdapter);
            xrecyclerviewVideo.refresh();
        }
    }

    @Override
    public void showVideoList(ArrayList<VideoBean> data) {
        LogUtil.i("abc", "showVideoList");
        xrecyclerviewVideo.refreshComplete();
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        LogUtil.i("abc", "showProgress");
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        LogUtil.i("abc", "hideProgress");
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String msg) {

    }

    public void backFromFull() {
        if (listVideoUtil != null) {
            listVideoUtil.backFromFull();
        }
    }

    /**
     * 收到了用户离开视频Fragment的事件(目前只有从底部切换走，没有其他入口切换走)
     */
    @Subscribe
    public void bottomTabSelectEvent(BottomTabSelectEvent event) {
        if (listVideoUtil != null) {
            listVideoUtil.smallVideoToNormal();
            listVideoUtil.releaseVideoPlayer();
            GSYVideoPlayer.releaseAllVideos();
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MyLoadingListener implements XRecyclerView.LoadingListener {

        @Override
        public void onRefresh() {
            mPresenter.getVideoList();
        }

        @Override
        public void onLoadMore() {
            xrecyclerviewVideo.loadMoreComplete();
        }

    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //使用了XRecyclerView 所以要-1
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition() - 1;
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition() - 1;
            Debuger.printfLog("firstVisibleItem " + firstVisibleItem + " lastVisibleItem " + lastVisibleItem);
            //大于0说明有播放,//对应的播放列表TAG

            if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(VideoAdapter.TAG)) {
                //当前播放的位置
                int position = listVideoUtil.getPlayPosition();
                //不可视的是时候
                if ((position < firstVisibleItem || position > lastVisibleItem)) {
                    //如果是小窗口就不需要处理
                    if (!listVideoUtil.isSmall() && !listVideoUtil.isFull()) {
                        //小窗口
                        int w = CommonUtil.dip2px(mContext, 160);
                        int h = CommonUtil.dip2px(mContext, 120);
                        listVideoUtil.showSmallVideo(new Point(w, h), false, true);
                    }
                } else {
                    if (listVideoUtil.isSmall()) {
                        listVideoUtil.smallVideoToNormal();
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    private class MyStandardVideoAllCallBack implements StandardVideoAllCallBack {

        @Override
        public void onPrepared(String url, Object... objects) {
            Debuger.printfLog("Duration " + listVideoUtil.getDuration() + " CurrentPosition " + listVideoUtil.getCurrentPositionWhenPlaying());
        }

        @Override
        public void onQuitSmallWidget(String url, Object... objects) {
            //大于0说明有播放,//对应的播放列表TAG
            if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(VideoAdapter.TAG)) {
                //当前播放的位置
                int position = listVideoUtil.getPlayPosition();
                //不可视的是时候
                if ((position < firstVisibleItem || position > lastVisibleItem)) {
                    //释放掉视频
                    listVideoUtil.releaseVideoPlayer();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }


        @Override
        public void onClickStartThumb(String url, Object... objects) {

        }

        @Override
        public void onClickBlank(String url, Object... objects) {

        }

        @Override
        public void onClickBlankFullscreen(String url, Object... objects) {

        }

        @Override
        public void onClickStartIcon(String url, Object... objects) {

        }

        @Override
        public void onClickStartError(String url, Object... objects) {

        }

        @Override
        public void onClickStop(String url, Object... objects) {

        }

        @Override
        public void onClickStopFullscreen(String url, Object... objects) {

        }

        @Override
        public void onClickResume(String url, Object... objects) {

        }

        @Override
        public void onClickResumeFullscreen(String url, Object... objects) {

        }

        @Override
        public void onClickSeekbar(String url, Object... objects) {

        }

        @Override
        public void onClickSeekbarFullscreen(String url, Object... objects) {

        }

        @Override
        public void onAutoComplete(String url, Object... objects) {

        }

        @Override
        public void onEnterFullscreen(String url, Object... objects) {

        }

        @Override
        public void onQuitFullscreen(String url, Object... objects) {

        }

        @Override
        public void onEnterSmallWidget(String url, Object... objects) {

        }

        @Override
        public void onTouchScreenSeekVolume(String url, Object... objects) {

        }

        @Override
        public void onTouchScreenSeekPosition(String url, Object... objects) {

        }

        @Override
        public void onTouchScreenSeekLight(String url, Object... objects) {

        }

        @Override
        public void onPlayError(String url, Object... objects) {

        }
    }

}
