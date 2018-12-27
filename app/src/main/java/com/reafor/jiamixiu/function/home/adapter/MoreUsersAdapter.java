package com.reafor.jiamixiu.function.home.adapter;

import android.content.Context;
import android.support.constraint.Placeholder;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.AbsBaseAdapter;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;

import cn.jzvd.JzvdStd;

public class MoreUsersAdapter extends AbsBaseAdapter<AboutUserVideoResponse.VideoSruct> {
    private RvOncliclListener<AboutUserVideoResponse.VideoSruct> listener;
    private String imgUrlDefault,nick;

    public void setImgUrlDefault(String imgUrlDefault) {
        this.imgUrlDefault = imgUrlDefault;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setListener(RvOncliclListener<AboutUserVideoResponse.VideoSruct> listener) {
        this.listener = listener;
    }

    public MoreUsersAdapter(Context mContext) {
        super(mContext, R.layout.item_home_about_user_videos,R.layout.item_home_about_uers_head);
    }

    @Override
    protected void bindView(Holder holder, AboutUserVideoResponse.VideoSruct item, int position) {
        if (getItemViewType(position) == 1){
            if (item.userInfo != null){
                ImageView iv_head = holder.getView(R.id.iv_head);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_videos = holder.getView(R.id.tv_videos);
                TextView tv_abouts = holder.getView(R.id.tv_abouts);
                if (TextUtils.isEmpty(item.userInfo.avator)){
                    if (TextUtils.isEmpty(imgUrlDefault)){
                        Glide.with(mContext).load(R.mipmap.icon_default_head).into(iv_head);
                    } else {
                        Glide.with(mContext).load(imgUrlDefault).into(iv_head);
                    }
                } else {
                    Glide.with(mContext).load(item.userInfo.avator).into(iv_head);
                }
                if (TextUtils.isEmpty(item.userInfo.nick)){
                    tv_name.setText(nick);
                } else {
                    tv_name.setText(item.userInfo.nick);
                }
                tv_videos.setText(item.userInfo.videos);
                tv_abouts.setText(item.userInfo.follow);
            }
        } else {
            ImageView iv_video = holder.getView(R.id.iv_video);
            Glide.with(mContext).load(item.coverimg).into(iv_video);
            TextView tv_video_name = holder.getView(R.id.tv_video_name);
            TextView tv_comment_num = holder.getView(R.id.tv_comment_num);
            TextView tv_praise_num = holder.getView(R.id.tv_praise_num);
            tv_video_name.setText(item.name);
            tv_comment_num.setText(item.commentnum);
            tv_praise_num.setText(item.likenum);
            holder.getView(R.id.fl_video).setOnClickListener(v -> {
                if (listener != null){
                    listener.onCick(holder,item,position);
                }
            });
        }
    }
}
