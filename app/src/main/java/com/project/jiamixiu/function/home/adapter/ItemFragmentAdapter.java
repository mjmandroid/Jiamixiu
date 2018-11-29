package com.project.jiamixiu.function.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.VideoResponse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFragmentAdapter extends RecyclerView.Adapter<ItemFragmentAdapter.RViewHolder> {

    private Context mContext;
    public ItemFragmentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private List<VideoResponse.VideoInfo> datas;

    public void setDatas(List<VideoResponse.VideoInfo> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public void addDatas(List<VideoResponse.VideoInfo> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_home_video_layout,null);
        RViewHolder holder = new RViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RViewHolder holder, int position) {
        VideoResponse.VideoInfo info = datas.get(position);
        holder.tv_name.setText(info.name);
//        Glide.with(mContext).load(info.coverimg).thumbnail(Glide.with(mContext).load(info.thumbnail))
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.iv_video.setImageResource(R.mipmap.ic_launcher);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                }).into(holder.iv_video);
        Glide.with(mContext).load(info.coverimg).into(holder.iv_video);

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class RViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_video)
        ImageView iv_video;
        @BindView(R.id.tv_name)
        TextView tv_name;
        public RViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
