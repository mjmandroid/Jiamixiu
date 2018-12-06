package com.project.jiamixiu.function.upload;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.function.upload.adapter.VideoSelectAdapter;
import com.project.jiamixiu.interfaces.RvOncliclListener;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.utils.VideoTrimmerUtil;
import com.project.jiamixiu.widget.ZVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import iknow.android.utils.callback.SingleCallback;
import iknow.android.utils.thread.UiThreadExecutor;

public class SelectTitlePageActivity extends BaseActivity {

    private String videoPath;
    @BindView(R.id.rv_selector)
    RecyclerView rv_selector;
    @BindView(R.id.iv_title)
    ImageView iv_title;
    private int duration;
    private VideoSelectAdapter mVideoThumbAdapter;
    private boolean isFirst = false;
    private Uri uriPath;

    @Override
    protected void initData() {
        super.initData();
        videoPath = getIntent().getStringExtra("videoPath");
        if (TextUtils.isEmpty(videoPath)){
            finish();
            return;
        }
        ZVideoView videoView = new ZVideoView(this);
        uriPath = Uri.parse(videoPath);
        videoView.setVideoURI(uriPath);
        duration = videoView.getDuration();
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_upload_select_titlepage);
        ButterKnife.bind(this);
        rv_selector.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mVideoThumbAdapter = new VideoSelectAdapter(this);
        rv_selector.setAdapter(mVideoThumbAdapter);

        startShootVideoThumbs(this, uriPath, 10, 0,duration);
        mVideoThumbAdapter.setListener((viewHolder, item, position) -> {
            iv_title.setImageBitmap(item);
            ToastUtil.showTosat(this,position+"");
        });
    }

    private void startShootVideoThumbs(final Context context, final Uri videoUri, int totalThumbsCount, long startPosition, long endPosition) {
        VideoTrimmerUtil.shootVideoThumbInBackground(context, videoUri, totalThumbsCount, startPosition, endPosition,
                new SingleCallback<Bitmap, Integer>() {
                    @Override public void onSingleCallback(final Bitmap bitmap, final Integer interval) {
                        if (bitmap != null) {
                            UiThreadExecutor.runTask("", new Runnable() {
                                @Override public void run() {
                                    if (!isFirst){
                                        isFirst = true;
                                        iv_title.setImageBitmap(bitmap);
                                    }
                                    mVideoThumbAdapter.addBitmaps(bitmap);
                                }
                            }, 0L);
                        }
                    }
                });
    }
}
