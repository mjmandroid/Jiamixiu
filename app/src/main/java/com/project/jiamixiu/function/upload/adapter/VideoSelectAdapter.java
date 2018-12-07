package com.project.jiamixiu.function.upload.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.jiamixiu.R;
import com.project.jiamixiu.function.upload.VideoTrimmerAdapter;
import com.project.jiamixiu.interfaces.RvOncliclListener;
import com.project.jiamixiu.utils.VideoTrimmerUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoSelectAdapter extends RecyclerView.Adapter {
    private List<Bitmap> mBitmaps = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private RvOncliclListener<Bitmap> listener;

    public void setListener(RvOncliclListener<Bitmap> listener) {
        this.listener = listener;
    }

    public VideoSelectAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrimmerViewHolder(mInflater.inflate(R.layout.item_upload_video_select_layout, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TrimmerViewHolder) holder).thumbImageView.setImageBitmap(mBitmaps.get(position));
        ((TrimmerViewHolder) holder).thumbImageView.setOnClickListener(v -> {
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

        TrimmerViewHolder(View itemView) {
            super(itemView);
            thumbImageView = itemView.findViewById(R.id.thumb);
        }
    }
}