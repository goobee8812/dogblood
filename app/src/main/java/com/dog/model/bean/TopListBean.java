package com.dog.model.bean;

import com.dog.base.BaseBean;

import java.util.ArrayList;


public class TopListBean extends BaseBean {

    private boolean status;
    private ArrayList<TopBean> tngou;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private int total;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<TopBean> getTngou() {
        return tngou;
    }

    public void setTngou(ArrayList<TopBean> tngou) {
        this.tngou = tngou;
    }

}
