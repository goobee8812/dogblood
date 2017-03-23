package com.dog.model.bean;

import com.dog.base.BaseBean;

import java.util.ArrayList;


public class TopClassifyBean extends BaseBean {

    private boolean status;
    private ArrayList<TngouBean> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<TngouBean> getTngou() {
        return tngou;
    }

    public void setTngou(ArrayList<TngouBean> tngou) {
        this.tngou = tngou;
    }

}
