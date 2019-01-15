package com.reafor.jiamixiu.function.subscrite.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.AbsBaseAdapter;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.SubscribeUsersResponse;
import com.reafor.jiamixiu.function.subscrite.SubscriteFragment;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.CircleImageView;

import java.util.List;

public class MySubAdapter extends AbsBaseAdapter<AboutUserVideoResponse.VideoSruct> {
    private RvOncliclListener listener;
    public MySubAdapter(Context mContext, RvOncliclListener<AboutUserVideoResponse.VideoSruct> listener) {
        super(mContext, R.layout.item_subscrite_videos_layout,R.layout.item_subscrite_title_layout,R.layout.item_subscrite_headview);
        this.listener = listener;
    }

    @Override
    protected void bindView(Holder holder, AboutUserVideoResponse.VideoSruct item, int position) {
        if (getItemViewType(position) == 0){
            ImageView iv_img = holder.getView(R.id.iv_img);
            ImageView iv_nick = holder.getView(R.id.iv_nick);
            TextView tv_nickname = holder.getView(R.id.tv_nickname);
            TextView tv_comment_num = holder.getView(R.id.tv_comment_num);
            TextView tv_praise_num = holder.getView(R.id.tv_praise_num);
            TextView tv_share_num = holder.getView(R.id.tv_share_num);

            Glide.with(mContext).load(item.coverimg).into(iv_img);
            if (TextUtils.isEmpty(item.avator)){
                Glide.with(mContext).load(SubscriteFragment.headUrl).into(iv_nick);
            } else {
                Glide.with(mContext).load("http://jiamixiu.uniondevice.com"+item.avator).into(iv_nick);
            }
            if (TextUtils.isEmpty(item.nick)){
                tv_nickname.setText(SubscriteFragment.headName);
            } else {
                tv_nickname.setText(item.nick);
            }
            tv_comment_num.setText(item.commentnum);
            tv_praise_num.setText(item.likenum);
            tv_share_num.setText(item.sharenum);
            holder.getView(R.id.fl_video).setOnClickListener(v -> listener.onCick(holder,item,position));
        } else if (getItemViewType(position) == 2){
            List<SubscribeUsersResponse.Data> hData = item.headView;
            if (hData != null ){
                LinearLayout parent = holder.getView(R.id.ll_parent);
                parent.removeAllViews();
                for (int i = 0; i < hData.size(); i++) {
                    SubscribeUsersResponse.Data itemData = hData.get(i);
                    CircleImageView headView = new CircleImageView(mContext);
                    headView.setBorderWidth(UIUtils.dip2px(mContext,2));
                    headView.setBorderColor(0xFF8FEAEC);
                    if (TextUtils.isEmpty(itemData.avator)){
                        Glide.with(mContext).load(R.mipmap.icon_default_head).into(headView);
                    } else {
                        Glide.with(mContext).load(itemData.avator).into(headView);
                    }
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(UIUtils.dip2px(mContext, 60),
                            UIUtils.dip2px(mContext, 60));
                    if (i != 0){
                        lp.leftMargin = UIUtils.dip2px(mContext,10);
                    }
                    parent.addView(headView,lp);
                }
            }
        }
    }
}
