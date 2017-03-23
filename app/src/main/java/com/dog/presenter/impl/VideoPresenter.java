package com.dog.presenter.impl;

import com.dog.base.RxPresenter;
import com.dog.model.bean.VideoBean;
import com.dog.presenter.contract.VideoContract;

import java.util.ArrayList;

import javax.inject.Inject;


public class VideoPresenter extends RxPresenter<VideoContract.View> implements VideoContract.Presenter {


    @Inject
    public VideoPresenter() {
    }

    @Override
    public void getVideoList() {
        mView.showProgress();
        ArrayList<VideoBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoBean videoBean = new VideoBean();
            data.add(videoBean);
        }
        mView.hideProgress();
        mView.showVideoList(data);
    }
}
