package com.dog.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dog.R;
import com.dog.base.SimpleFragment;
import com.dog.ui.adapter.TestAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class LikeFragment extends SimpleFragment {

    @BindView(R.id.xrecyclerview_like)
    RecyclerView xrecyclerviewLike;

    @BindView(R.id.progress)
    View progress;

    private TestAdapter mTestAdapter;

    public static LikeFragment newInstance() {
        Bundle args = new Bundle();
        LikeFragment fragment = new LikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_like;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initData() {

    }

    private void initRecyclerView() {
        if (mTestAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xrecyclerviewLike.setLayoutManager(layoutManager);
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                data.add("1111");
            }
            mTestAdapter = new TestAdapter(data);
            xrecyclerviewLike.setAdapter(mTestAdapter);

        }
    }
}
