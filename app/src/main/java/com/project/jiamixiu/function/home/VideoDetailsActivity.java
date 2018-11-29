package com.project.jiamixiu.function.home;

import android.content.pm.ActivityInfo;
import android.view.View;

import com.bumptech.glide.Glide;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.function.home.prenster.VideoDetailPrenster;
import com.project.jiamixiu.function.home.view.IvideoDetailView;
import com.project.jiamixiu.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoDetailsActivity extends BaseActivity implements IvideoDetailView {

    private String id;
    private VideoDetailPrenster prenster;
    private String videoThumbUrl;
    @BindView(R.id.video)
    JzvdStd video;

    @Override
    protected void initData() {
        super.initData();
        id = getIntent().getStringExtra("id");
        videoThumbUrl = getIntent().getStringExtra("videoThumbUrl");
        prenster = new VideoDetailPrenster(this);
        loadDetail();
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_video_detail_layout);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(videoThumbUrl)
                .into(video.thumbImageView);
    }

    private void loadDetail(){
        prenster.loadDetail(id);
    }

    @Override
    public void loadDetailSuccess(VideoDetailResponse.Data data) {
        video.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4",
                data.name , Jzvd.SCREEN_WINDOW_NORMAL);
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        video.startVideo();//开始播放
    }



    @Override
    public void laodDetailFail(String errmsg) {
        ToastUtil.showTosat(this,errmsg);
    }

    @Override
    public void loadFinish() {

    }
    @OnClick({R.id.iv_back})
    public void btnOnclick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        //Change these two variables back
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
}
