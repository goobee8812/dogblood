package com.dog.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dog.R;
import com.dog.base.BaseFragment;
import com.dog.model.bean.TngouBean;
import com.dog.model.bean.TopClassifyBean;
import com.dog.presenter.contract.NewsContract;
import com.dog.presenter.impl.NewsPresenter;
import com.dog.ui.adapter.NewsPagerFragmentAdapter;

import java.util.List;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.tab_layout_news)
    TabLayout tabLayoutNews;
    @BindView(R.id.vp_news)
    ViewPager vpNews;

    @BindView(R.id.progress)
    View progress;

    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.getTopClassify();
    }

    @Override
    public void showTopClassify(TopClassifyBean topClassifyBean) {
        List<TngouBean> tngous = topClassifyBean.getTngou();
        NewsPagerFragmentAdapter newsPagerFragmentAdapter = new NewsPagerFragmentAdapter(tngous, getChildFragmentManager());
        vpNews.setAdapter(newsPagerFragmentAdapter);
        vpNews.setOffscreenPageLimit(tngous.size());
        tabLayoutNews.setupWithViewPager(vpNews);
        tabLayoutNews.setTabMode(TabLayout.MODE_SCROLLABLE);
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

}
