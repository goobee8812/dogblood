package com.dog.base;

/**
 * View基类
 */
public interface BaseView {

    void showProgress();

    void hideProgress();

    void showEmpty();

    void showError(String msg);

}
