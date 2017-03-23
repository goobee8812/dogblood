package com.dog.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dog.R;
import com.dog.model.bean.VideoBean;
import com.dog.video.utils.ListVideoUtil;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    public final static String TAG = "VideoAdapter";
    private Context mContext;
    private ArrayList<VideoBean> mData;
    private ListVideoUtil listVideoUtil;

    public VideoAdapter(Context context, ArrayList<VideoBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        ViewHolder holder = new ViewHolder(mContext, view);
        return holder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        //增加封面
        viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.imageView.setImageResource(R.drawable.headimg);

        listVideoUtil.addVideoPlayer(position, viewHolder.imageView, TAG, viewHolder.listItemContainer, viewHolder.listItemBtn);

        viewHolder.listItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);

                String url = "";
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                if (position == 0) {
                    url = "http://117.144.248.49/HDhnws.m3u8?authCode=07110409322147352675&amp;stbId=006001FF0018120000060019F0D496A1&amp;Contentid=6837496099179515295&amp;mos=jbjhhzstsl&amp;livemode=1&amp;channel-id=wasusyt";
                } else {
                    url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                }
                url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                listVideoUtil.startPlay(url);
            }
        });
    }

    public void setData(ArrayList<VideoBean> data) {
        this.mData = data;
    }

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil = listVideoUtil;
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
        Context mContext;
        ImageView imageView;
        FrameLayout listItemContainer;
        ImageView listItemBtn;

        public ViewHolder(Context context, View view) {
            super(view);
            this.mContext = context;
            listItemContainer = (FrameLayout) view.findViewById(R.id.list_item_container);
            listItemBtn = (ImageView) view.findViewById(R.id.list_item_btn);
            imageView = new ImageView(context);
        }
    }
}
