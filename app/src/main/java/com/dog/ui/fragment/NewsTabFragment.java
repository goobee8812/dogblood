package com.dog.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.dog.R;
import com.dog.base.BaseFragment;
import com.dog.event.BottomTabReselectEvent;
import com.dog.model.bean.TngouBean;
import com.dog.model.bean.TopBean;
import com.dog.model.bean.TopListBean;
import com.dog.presenter.contract.NewsTabContract;
import com.dog.presenter.impl.NewsTabPresenter;
import com.dog.ui.activity.NewsDetailsActivity;
import com.dog.ui.adapter.NewsTabAdapter;
import com.dog.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

public class NewsTabFragment extends BaseFragment<NewsTabPresenter> implements NewsTabContract.View {

    @BindView(R.id.xrecyclerview_news_tab)
    XRecyclerView xrecyclerview_news_tab;

    @BindView(R.id.progress)
    View progress;

    private TngouBean mTngouBean;
    private ArrayList<TopBean> mTopBeanList;
    private NewsTabAdapter mAdapter;

    public static final int MSG_GOTO_DETAILS = 1;

    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_GOTO_DETAILS:
                    int position = (int) message.obj;
                    gotoNewsDetailsActivity(position);
                    break;
            }
        }
    };

    public static NewsTabFragment newInstance(TngouBean tngouBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TngouBean.class.getName(), tngouBean);
        NewsTabFragment fragment = new NewsTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_tab;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initData() {
        mTngouBean = (TngouBean) getArguments().getSerializable(TngouBean.class.getName());
        mPresenter.getTopList(mTngouBean.getId());
    }

    private void initRecyclerView() {

        if (mAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xrecyclerview_news_tab.setLayoutManager(layoutManager);
            xrecyclerview_news_tab.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            xrecyclerview_news_tab.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            xrecyclerview_news_tab.setArrowImageView(R.drawable.iconfont_downgrey);
            xrecyclerview_news_tab.setLoadingListener(new MyLoadingListener());
//            View header = LayoutInflater.from(mActivity).inflate(R.layout.recyclerview_header, null, false);
//            xrecyclerview_news_tab.addHeaderView(header);
            mAdapter = new NewsTabAdapter(this, null, mHandler);
            xrecyclerview_news_tab.setAdapter(mAdapter);
        }
    }


    @Override
    public void showTopList(TopListBean topListBean) {
        mTopBeanList = topListBean.getTngou();
        mAdapter.setData(mTopBeanList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRefreshTopList(TopListBean topListBean) {
        if (mTopBeanList != null) {
            mTopBeanList.clear();
        }
        mTopBeanList.addAll(topListBean.getTngou());
        mAdapter.setData(mTopBeanList);
        mAdapter.notifyDataSetChanged();
        xrecyclerview_news_tab.refreshComplete();
    }

    @Override
    public void showMoreTopList(TopListBean topListBean) {
        mTopBeanList.addAll(topListBean.getTngou());
        mAdapter.setData(mTopBeanList);
        mAdapter.notifyDataSetChanged();
        xrecyclerview_news_tab.loadMoreComplete();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String msg) {

    }

    /**
     * 跳转到新闻详情页面
     *
     * @param position
     */
    private void gotoNewsDetailsActivity(int position) {
        Intent intent = new Intent(mContext, NewsDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TopBean.class.getName(), mTopBeanList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class MyLoadingListener implements XRecyclerView.LoadingListener {

        @Override
        public void onRefresh() {
            mPresenter.getRefreshTopList(mTngouBean.getId());
        }

        @Override
        public void onLoadMore() {
            if (mTopBeanList.size() == 100) {
                ToastUtil.shortShow("没有更多条目");
                xrecyclerview_news_tab.loadMoreComplete();
            } else {
                mPresenter.getMoreTopList(mTngouBean.getId());
            }
        }

    }

    /**
     * 收到了用户重复点击第一个Tab栏的事件
     */
    @Subscribe
    public void bottomTabReselectEvent(BottomTabReselectEvent event) {
        if (getUserVisibleHint()) { //只刷新当前显示的Fragment
            xrecyclerview_news_tab.refresh();
        }
    }

}
