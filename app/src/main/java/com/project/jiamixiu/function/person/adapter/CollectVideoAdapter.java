package com.project.jiamixiu.function.person.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.CollectVideoBean;
import com.project.jiamixiu.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectVideoAdapter extends BaseAdapter {

    private ArrayList<CollectVideoBean.VideoData> list;
    private Context context;
    public CollectVideoAdapter(Context context, ArrayList<CollectVideoBean.VideoData> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectVideoViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_video_collect, null);
            holder = new CollectVideoViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CollectVideoViewHolder) convertView.getTag();
        }
        holder.tvVideo.setText(list.get(position).name);
        holder.tvTime.setText(list.get(position).f_creatortime);
        holder.tvName.setText(list.get(position).nick);
        if (!TextUtils.isEmpty(list.get(position).coverimg)){
            Picasso.with(context).load(UIUtils.getImageUrl(list.get(position).coverimg)).into(holder.ivVideo);
        }
        final String id = list.get(position).f_id;
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoListener != null){
                    videoListener.onDelete(id);
                }
            }
        });

        holder.ll_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoListener != null){
                    videoListener.onClick(position);
                }
            }
        });
        return convertView;
    }

    static class CollectVideoViewHolder {
        @BindView(R.id.tv_del)
        TextView tvDel;
        @BindView(R.id.iv_video)
        ImageView ivVideo;
        @BindView(R.id.tv_video)
        TextView tvVideo;
        @BindView(R.id.iv_msg)
        ImageView ivMsg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_video)
        LinearLayout ll_video;

        CollectVideoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    VideoListener videoListener;

    public void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }

    public interface VideoListener{
        void onClick(int i);
        void onDelete(String id);
    }
}
