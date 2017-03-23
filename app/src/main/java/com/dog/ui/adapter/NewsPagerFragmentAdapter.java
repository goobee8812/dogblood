package com.dog.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dog.model.bean.TngouBean;
import com.dog.ui.fragment.NewsTabFragment;

import java.util.List;


public class NewsPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<TngouBean> mTngous;

    public NewsPagerFragmentAdapter(List<TngouBean> tngous, FragmentManager fm) {
        super(fm);
        this.mTngous = tngous;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsTabFragment.newInstance(mTngous.get(position));
    }

    @Override
    public int getCount() {
        return mTngous.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTngous.get(position).getName();
    }
}