package com.project.jiamixiu.function.person.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.FanBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FanAdapter extends BaseAdapter {
    ArrayList<FanBean.FanData> fanData;
    Context context;
    String type;
    public FanAdapter(Context context,ArrayList<FanBean.FanData> fanData){
        this.context = context;
        this.fanData = fanData;
    }
    @Override
    public int getCount() {
        return fanData.size();
    }

    public void setType(String type) {
        this.type = type;
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
        FanViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_fan, null);
            holder = new FanViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (FanViewHolder)convertView.getTag();
        }
        FanBean.FanData f = fanData.get(position);
        holder.tv_name.setText(f.username);
        holder.tv_time.setText(f.laston);
        if (f.isfollow){
            holder.tv_follow.setText("相互关注");
            holder.tv_follow.setOnClickListener(null);
            holder.tv_follow.setTag("");
            holder.tv_follow.setBackgroundResource(R.drawable.shape_btn_follow);
            holder.tv_follow.setTextColor(Color.parseColor("#333333"));
        }else {
            holder.tv_follow.setText("关注");
            holder.tv_follow.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_follow.setBackgroundResource(R.drawable.shape_btn_ok);
            holder.tv_follow.setTag(fanData.get(position).f_id);
            holder.tv_follow.setOnClickListener(clickListener);
        }
        Picasso.with(context).load(f.avator).into(holder.iv_user_img);
        if ("1".equals(type)){
            holder.tv_content.setVisibility(View.GONE);
        }else {
            holder.tv_content.setText("关注了你");
            holder.tv_content.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    static class FanViewHolder {
        @BindView(R.id.iv_user_img)
        ImageView iv_user_img;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_follow)
        TextView tv_follow;
        @BindView(R.id.tv_content)
        TextView tv_content;
        FanViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
