package com.dog.presenter.contract;


import com.dog.base.BasePresenter;
import com.dog.base.BaseView;
import com.dog.model.bean.TopListBean;

public interface NewsTabContract {

    interface View extends BaseView {
        void showTopList(TopListBean topListBean);

        void showRefreshTopList(TopListBean topListBean);

        void showMoreTopList(TopListBean topListBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getTopList(int id);

        void getRefreshTopList(int id);

        void getMoreTopList(int id);
    }

}