package com.dog.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dog.R;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    public ArrayList<String> mData;

    public TestAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void setData(ArrayList<String> data) {
        this.mData = data;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.tv_test_item.setText("test~~" + position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_test_item;

        public ViewHolder(View view) {
            super(view);
            tv_test_item = (TextView) view.findViewById(R.id.tv_test_item);
        }
    }
}
