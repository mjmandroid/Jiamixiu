package com.project.jiamixiu.function.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.AbsBaseAdapter;
import com.project.jiamixiu.bean.VideoCommentResponse;
import com.project.jiamixiu.bean.VideoRecommendResponse;

public class VideoCommentAdapter extends AbsBaseAdapter<VideoCommentResponse.Data> {

    private IitemOnClickListener listener;

    public void setListener(IitemOnClickListener listener) {
        this.listener = listener;
    }

    public VideoCommentAdapter(Context mContext) {
        super(mContext, R.layout.item_homeview_comment_layout,R.layout.homeview_comment_headview_layout);
    }

    @Override
    protected void bindView(Holder holder, VideoCommentResponse.Data item, int position) {
        if (getItemViewType(position) == 0){
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.icon_default_head);
            Glide.with(mContext).load(item.avator).apply(options).into((ImageView) holder.getView(R.id.iv_head));
            TextView tv_mess = holder.getView(R.id.tv_comment);
            TextView tv_time = holder.getView(R.id.tv_time);
            tv_mess.setText(item.message);
            tv_time.setText(item.f_creatortime);
            holder.getView(R.id.tv_reply).setOnClickListener(view ->{
                if (listener != null)
                    listener.reply(item.sourthid);
            });
        } else if (getItemViewType(position) == 1){
            if (item.recInfo == null){

            } else {
                VideoRecommendResponse.VideoInfo videoInfo = item.recInfo;
                RelativeLayout rl_recommend = holder.getView(R.id.rl_recommend);
                TextView tv_more = holder.getView(R.id.tv_more);
                holder.getView(R.id.line).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(videoInfo.coverimg).into((ImageView) holder.getView(R.id.iv_rec));
                TextView tv_rec_title = holder.getView(R.id.tv_rec_title);
                TextView tv_rec_name = holder.getView(R.id.tv_rec_name);
                TextView tv_rec_playnum = holder.getView(R.id.tv_rec_playnum);
                TextView tv_all_comment = holder.getView(R.id.tv_all_comment);
                if (datas.size() > 1){
                    tv_all_comment.setVisibility(View.VISIBLE);
                }
                rl_recommend.setVisibility(View.VISIBLE);
                tv_more.setVisibility(View.VISIBLE);
                tv_rec_title.setText(videoInfo.name);
                tv_rec_name.setText(videoInfo.nick);
                tv_rec_playnum.setText(videoInfo.viewnum+"次播放量");
                tv_more.setOnClickListener(view ->{
                    if (listener != null)
                        listener.getMoreVideos();
                });
                rl_recommend.setOnClickListener(view->{
                    if (listener != null)
                        listener.headviewClick(videoInfo.f_id,videoInfo.coverimg);
                });
            }
        }
    }

    public interface IitemOnClickListener{
        void getMoreVideos();
        void headviewClick(String f_id,String coverimg);
        void reply(String sourthId);
    }
}
