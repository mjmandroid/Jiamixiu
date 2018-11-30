package com.project.jiamixiu.function.home;

import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.function.home.prenster.VideoDetailPrenster;
import com.project.jiamixiu.function.home.view.IvideoDetailView;
import com.project.jiamixiu.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoDetailsActivity extends BaseActivity implements IvideoDetailView {

    private String id;
    private String f_creatoruserid;//发布人id
    private VideoDetailPrenster prenster;
    private String videoThumbUrl;
    private VideoDetailResponse.Data responseDetail;
    @BindView(R.id.video)
    JzvdStd video;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.iv_nick)
    ImageView iv_nick;
    @BindView(R.id.tv_aboutnum)
    TextView tv_aboutnum;
    @BindView(R.id.btn_about)
    Button btn_about;
    @BindView(R.id.tv_playnum)
    TextView tv_playnum;
    @BindView(R.id.tv_comment_num)
    TextView tv_comment_num;
    @BindView(R.id.tv_praise_num)
    TextView tv_praise_num;
    @BindView(R.id.tv_favorite_num)
    TextView tv_favorite_num;
    @BindView(R.id.tv_share_num)
    TextView tv_share_num;

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
        responseDetail = data;
        tv_title.setText(data.name);
        tv_nickname.setText(data.nick);
        tv_aboutnum.setText(data.follow+"关注量");
        if (data.isfollow.equals("true")){
            btn_about.setText("关注");
            btn_about.setTextColor(0xffffffff);
            btn_about.setBackgroundResource(R.mipmap.icon_aboutme);
        } else {
            btn_about.setText("查看更多");
            btn_about.setTextColor(0xff666666);
            btn_about.setBackgroundResource(R.mipmap.icon_check_more);
        }
        tv_playnum.setText(data.f_creatortime+" "+data.viewnum+"次播放量");
        tv_comment_num.setText(data.commentnum);
        tv_praise_num.setText(data.likenum);
        tv_favorite_num.setText(data.favoritenum);
        tv_share_num.setText(data.sharenum);
        f_creatoruserid = data.f_creatoruserid;
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher);
        Glide.with(this).load(data.avator).apply(options).into(iv_nick);
        video.setUp(data.url,data.name , Jzvd.SCREEN_WINDOW_NORMAL);
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        Jzvd.SAVE_PROGRESS = false;
        video.startVideo();//开始播放
    }

    @Override
    public void laodDetailFail(String errmsg) {
        ToastUtil.showTosat(this,errmsg);
    }

    @Override
    public void loadFinish() {

    }

    @Override
    public void aboutSuccess() {
        btn_about.setText("查看更多");
        btn_about.setTextColor(0xff666666);
        btn_about.setBackgroundResource(R.mipmap.icon_check_more);
    }

    @OnClick({R.id.iv_back,R.id.btn_about})
    public void btnOnclick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_about:
                if (btn_about.getText().toString().equals("关注")){
                    about();
                } else {
                    checkMore();
                }
                break;
        }
    }
    //关注
    private void about() {
        if (!TextUtils.isEmpty(f_creatoruserid))
        prenster.about(f_creatoruserid);
    }
    //查看更多
    private void checkMore() {

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
