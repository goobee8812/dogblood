package com.dog.presenter.impl;

import com.dog.base.RxPresenter;
import com.dog.model.bean.TopClassifyBean;
import com.dog.presenter.contract.NewsContract;
import com.dog.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;


public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {


    @Inject
    public NewsPresenter() {
    }

    @Override
    public void getTopClassify() {
        mView.showProgress();
        Subscription rxSubscription = mRetrofitHelper.getTopClassify()
                .compose(RxUtil.<TopClassifyBean>rxSchedulerHelper())
                .subscribe(new Action1<TopClassifyBean>() {
                    @Override
                    public void call(final TopClassifyBean sectionListBean) {
                        mView.hideProgress();
                        mView.showTopClassify(sectionListBean);
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
