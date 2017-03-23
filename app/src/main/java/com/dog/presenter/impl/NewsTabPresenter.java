package com.dog.presenter.impl;

import com.dog.base.RxPresenter;
import com.dog.log.LogUtil;
import com.dog.model.bean.TopListBean;
import com.dog.presenter.contract.NewsTabContract;
import com.dog.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;


public class NewsTabPresenter extends RxPresenter<NewsTabContract.View> implements NewsTabContract.Presenter {


    private static final int NUM_OF_PAGE = 20;
    private int mCurrentPage = 1;


    @Inject
    public NewsTabPresenter() {
    }

    @Override
    public void getTopList(int id) {
        mCurrentPage = 1;
        mView.showProgress();
        Subscription rxSubscription = mRetrofitHelper.getTopList(id, mCurrentPage, NUM_OF_PAGE)
                .compose(RxUtil.<TopListBean>rxSchedulerHelper())
                .subscribe(new Action1<TopListBean>() {
                    @Override
                    public void call(final TopListBean topListBean) {
                        mView.hideProgress();
                        mView.showTopList(topListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载数据失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRefreshTopList(int id) {
        mCurrentPage = 1;
        Subscription rxSubscription = mRetrofitHelper.getTopList(id, mCurrentPage, NUM_OF_PAGE)
                .compose(RxUtil.<TopListBean>rxSchedulerHelper())
                .subscribe(new Action1<TopListBean>() {
                    @Override
                    public void call(final TopListBean topListBean) {
                        mView.showRefreshTopList(topListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载数据失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getMoreTopList(int id) {
        mCurrentPage++;
        LogUtil.i("abc", "mCurrentPage-->" + mCurrentPage);
        Subscription rxSubscription = mRetrofitHelper.getTopList(id, mCurrentPage, NUM_OF_PAGE)
                .compose(RxUtil.<TopListBean>rxSchedulerHelper())
                .subscribe(new Action1<TopListBean>() {
                    @Override
                    public void call(final TopListBean topListBean) {
                        mView.showMoreTopList(topListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载数据失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
