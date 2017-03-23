package com.dog.presenter.contract;


import com.dog.base.BasePresenter;
import com.dog.base.BaseView;
import com.dog.model.bean.VideoBean;

import java.util.ArrayList;

public interface VideoContract {

    interface View extends BaseView {
        void showVideoList(ArrayList<VideoBean> data);

    }

    interface Presenter extends BasePresenter<View> {
        void getVideoList();
    }

}