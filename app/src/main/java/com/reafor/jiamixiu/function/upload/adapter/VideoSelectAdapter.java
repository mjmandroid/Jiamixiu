package com.reafor.jiamixiu.function.upload.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.function.upload.VideoTrimmerAdapter;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.VideoTrimmerUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoSelectAdapter extends RecyclerView.Adapter {
    private List<Bitmap> mBitmaps = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private RvOncliclListener<Bitmap> listener;
    private int selectIdx = 0;

    public void setListener(RvOncliclListener<Bitmap> listener) {
        this.listener = listener;
    }

    public VideoSelectAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setSelectIdx(int selectIdx) {
        this.selectIdx = selectIdx;
    }

    @NonNull
    @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrimmerViewHolder(mInflater.inflate(R.layout.item_upload_video_select_layout, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TrimmerViewHolder myHolder = (TrimmerViewHolder) holder;
        if (position == selectIdx){
            myHolder.tv_win.setBackgroundResource(R.drawable.shape_white2);
        } else {
            myHolder.tv_win.setBackgroundColor(Color.TRANSPARENT);
        }
        myHolder.thumbImageView.setImageBitmap(mBitmaps.get(position));
        myHolder.thumbImageView.setOnClickListener(v -> {
            if (listener != null){
                listener.onCick(holder,mBitmaps.get(position),position);
            }
        });
    }

    @Override public int getItemCount() {
        return mBitmaps.size();
    }

    public void addBitmaps(Bitmap bitmap) {
        mBitmaps.add(bitmap);
        notifyDataSetChanged();
    }

    private final class TrimmerViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbImageView;
        public FrameLayout fl_parent;
        public TextView tv_win;

        TrimmerViewHolder(View itemView) {
            super(itemView);
            thumbImageView = itemView.findViewById(R.id.thumb);
            fl_parent = itemView.findViewById(R.id.fl_parent);
            tv_win = itemView.findViewById(R.id.tv_win);
        }
    }
}