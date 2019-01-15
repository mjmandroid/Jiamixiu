package com.reafor.jiamixiu.function.person.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.CollectVideoBean;
import com.reafor.jiamixiu.bean.MyWorkBean;
import com.reafor.jiamixiu.function.message.activity.AtMeActivity;
import com.reafor.jiamixiu.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends BaseAdapter {

    private ArrayList<MyWorkBean.MyWorkData> list;
    private Context context;

    public VideoAdapter(Context context, ArrayList<MyWorkBean.MyWorkData> list) {
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
        VideoViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_lv_video, null);
            holder = new VideoViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (VideoViewHolder)convertView.getTag();
        }
        holder.tvVideo.setText(list.get(position).description);
        holder.tvMsgNum.setText(list.get(position).commentnum);
        holder.tvZanNum.setText(list.get(position).likenum);
        holder.tvShareNum.setText(list.get(position).sharenum);
        if (!TextUtils.isEmpty(list.get(position).coverimg)){
            Picasso.with(context).load(UIUtils.getImageUrl(list.get(position).coverimg)).into(holder.ivVideo);
        }

        return convertView;
    }

    static class VideoViewHolder {
        @BindView(R.id.iv_video)
        ImageView ivVideo;
        @BindView(R.id.tv_video)
        TextView tvVideo;
        @BindView(R.id.iv_msg)
        ImageView ivMsg;
        @BindView(R.id.tv_msg_num)
        TextView tvMsgNum;
        @BindView(R.id.iv_zan)
        ImageView ivZan;
        @BindView(R.id.tv_zan_num)
        TextView tvZanNum;
        @BindView(R.id.iv_share)
        ImageView ivShare;
        @BindView(R.id.tv_share_num)
        TextView tvShareNum;
        VideoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}