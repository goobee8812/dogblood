package com.dog.base;

/**
 * Presenter基类
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
