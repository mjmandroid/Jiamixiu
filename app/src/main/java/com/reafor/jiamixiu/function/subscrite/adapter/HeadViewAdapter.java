package com.reafor.jiamixiu.function.subscrite.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.AbsBaseAdapter;
import com.reafor.jiamixiu.bean.SubscribeUsersResponse;

public class HeadViewAdapter extends AbsBaseAdapter<SubscribeUsersResponse.Data> {
    public HeadViewAdapter(Context mContext) {
        super(mContext, R.layout.item_subscrite_headview);
    }

    @Override
    protected void bindView(Holder holder, SubscribeUsersResponse.Data item, int position) {
        ImageView iv = holder.getView(R.id.iv_head);
        if (TextUtils.isEmpty(item.avator)){
            Glide.with(mContext).load(R.mipmap.icon_default_head).into(iv);
        } else
        Glide.with(mContext).load(item.avator).into(iv);
    }
}
