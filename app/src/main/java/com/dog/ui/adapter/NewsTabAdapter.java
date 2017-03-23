package com.dog.ui.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dog.R;
import com.dog.engine.ImageLoader;
import com.dog.model.bean.TopBean;
import com.dog.ui.fragment.NewsTabFragment;

import java.util.ArrayList;

public class NewsTabAdapter extends RecyclerView.Adapter<NewsTabAdapter.ViewHolder> {
    public Fragment mFragment;
    public ArrayList<TopBean> mTopBeans;
    private Handler mHandler;

    public NewsTabAdapter(NewsTabFragment fragment, ArrayList<TopBean> topBeans, Handler handler) {
        this.mFragment = fragment;
        this.mTopBeans = topBeans;
        this.mHandler = handler;
    }

    public void setData(ArrayList<TopBean> topBeans) {
        this.mTopBeans = topBeans;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TopBean topBean = mTopBeans.get(position);
        String imageUrl = topBean.getImg();
        ImageLoader.load(mFragment, imageUrl, R.drawable.item_news_iv_placeholder, viewHolder.iv_news_item);
        viewHolder.tv_news_item.setText(topBean.getTitle());


        viewHolder.rl_root_news_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = mHandler.obtainMessage();
                message.what = NewsTabFragment.MSG_GOTO_DETAILS;
                message.obj = position;
                mHandler.sendMessage(message);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mTopBeans == null) {
            return 0;
        }
        return mTopBeans.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_root_news_item;
        public ImageView iv_news_item;
        public TextView tv_news_item;

        public ViewHolder(View view) {
            super(view);
            rl_root_news_item = (RelativeLayout) view.findViewById(R.id.rl_root_news_item);
            iv_news_item = (ImageView) view.findViewById(R.id.iv_news_item);
            tv_news_item = (TextView) view.findViewById(R.id.tv_news_item);
        }
    }
}
