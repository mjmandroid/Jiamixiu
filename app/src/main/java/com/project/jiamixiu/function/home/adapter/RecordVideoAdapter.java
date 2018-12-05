package com.project.jiamixiu.function.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.AbsBaseAdapter;
import com.project.jiamixiu.bean.VidoeRecordResponse;
import com.project.jiamixiu.interfaces.RvOncliclListener;

public class RecordVideoAdapter extends AbsBaseAdapter<VidoeRecordResponse.Data> {
    private RvOncliclListener listener;
    public RecordVideoAdapter(Context mContext, RvOncliclListener<VidoeRecordResponse.Data> listener) {
        super(mContext, R.layout.item_home_video_record_layout);
        this.listener = listener;
    }

    @Override
    protected void bindView(Holder holder, VidoeRecordResponse.Data item, int position) {
        LinearLayout ll_parent = holder.getView(R.id.ll_parent);
        ImageView iv_src = holder.getView(R.id.iv_src);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_nick = holder.getView(R.id.tv_nick);
        TextView tv_time = holder.getView(R.id.tv_time);

        Glide.with(mContext).load(item.coverimg).into(iv_src);
        tv_name.setText(item.name);
        tv_nick.setText(item.nick);
        tv_time.setText(item.f_creatortime);
        ll_parent.setOnClickListener((view)-> listener.onCick(holder,item,position));
    }
}
