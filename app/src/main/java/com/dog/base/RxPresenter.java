package com.dog.base;


import com.dog.model.http.RetrofitHelper;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected RetrofitHelper mRetrofitHelper;


    public RxPresenter() {
        mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }


    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }


}
