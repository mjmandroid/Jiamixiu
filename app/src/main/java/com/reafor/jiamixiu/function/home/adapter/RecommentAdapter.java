package com.reafor.jiamixiu.function.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.AbsBaseAdapter;
import com.reafor.jiamixiu.bean.VideoRecommendResponse;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;

public class RecommentAdapter extends AbsBaseAdapter<VideoRecommendResponse.VideoInfo> {
    private RvOncliclListener listener;
    public RecommentAdapter(Context mContext, RvOncliclListener<VideoRecommendResponse.VideoInfo> listener) {
        super(mContext, R.layout.item_home_video_recomment_layout);
        this.listener = listener;
    }

    @Override
    protected void bindView(Holder holder, VideoRecommendResponse.VideoInfo item, int position) {
        VideoRecommendResponse.VideoInfo videoInfo = item;
        RelativeLayout rl_recommend = holder.getView(R.id.rl_recommend);
        Glide.with(mContext).load(videoInfo.coverimg).into((ImageView) holder.getView(R.id.iv_rec));
        TextView tv_rec_title = holder.getView(R.id.tv_rec_title);
        TextView tv_rec_name = holder.getView(R.id.tv_rec_name);
        TextView tv_rec_playnum = holder.getView(R.id.tv_rec_playnum);
        tv_rec_title.setText(videoInfo.name);
        tv_rec_name.setText(videoInfo.nick);
        tv_rec_playnum.setText(videoInfo.viewnum+"次播放量");
        rl_recommend.setOnClickListener(view->{
            if(listener != null)
                listener.onCick(holder,item,position);
        });
    }
}
