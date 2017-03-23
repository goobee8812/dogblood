package com.dog.presenter.contract;


import com.dog.base.BasePresenter;
import com.dog.base.BaseView;
import com.dog.model.bean.TopClassifyBean;

public interface NewsContract {

    interface View extends BaseView {
        void showTopClassify(TopClassifyBean topClassifyBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getTopClassify();
    }

}