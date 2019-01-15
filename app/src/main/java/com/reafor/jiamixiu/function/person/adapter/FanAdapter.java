package com.reafor.jiamixiu.function.person.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.FanBean;
import com.reafor.jiamixiu.utils.UIUtils;
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
        holder.tv_name.setText(f.nick);
        holder.tv_time.setText(f.laston);
        if ("1".equals(type)){
            holder.tv_follow.setText("相互关注");
            holder.tv_follow.setOnClickListener(null);
            holder.tv_follow.setTag("");
            holder.tv_follow.setBackgroundResource(R.drawable.shape_btn_follow);
            holder.tv_follow.setTextColor(Color.parseColor("#333333"));
        }else {
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
                final String id = fanData.get(position).f_id;
                holder.tv_follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callBack != null){
                            callBack.onFollow(id);
                        }
                    }
                });
            }
        }
        if (!TextUtils.isEmpty(f.avator)){
            Picasso.with(context).load(UIUtils.getImageUrl(f.avator)).into(holder.iv_user_img);
        }

        if ("1".equals(type)){
            holder.tv_content.setVisibility(View.GONE);
        }else {
            holder.tv_content.setText("关注了你");
            holder.tv_content.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public interface FollowCallBack{
        void onFollow(String id);
    }
    private FollowCallBack callBack;

    public void setCallBack(FollowCallBack callBack) {
        this.callBack = callBack;
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
