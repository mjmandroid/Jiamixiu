package com.project.jiamixiu.function.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.AbsBaseAdapter;
import com.project.jiamixiu.bean.VideoResponse;
import com.project.jiamixiu.interfaces.RvOncliclListener;

public class SearchAdapter extends AbsBaseAdapter<VideoResponse.VideoInfo> {
    private RvOncliclListener<VideoResponse.VideoInfo> listener;
    public SearchAdapter(Context mContext,RvOncliclListener<VideoResponse.VideoInfo> listener) {
        super(mContext, R.layout.item_home_serach_lauout);
        this.listener = listener;
    }

    @Override
    protected void bindView(Holder holder, VideoResponse.VideoInfo item, int position) {

        ImageView iv_pic = holder.getView(R.id.iv_picture);
        TextView tv_name = holder.getView(R.id.tv_name);
        tv_name.setText(item.nick);
        Glide.with(mContext).load(item.coverimg).into(iv_pic);
        iv_pic.setOnClickListener(view->{
            if (listener != null){
                listener.onCick(holder,item,position);
            }
        });
    }
}
