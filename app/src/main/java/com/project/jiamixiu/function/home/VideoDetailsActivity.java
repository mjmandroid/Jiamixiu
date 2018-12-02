package com.project.jiamixiu.function.home;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.bean.VideoCommentResponse;
import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.bean.VideoRecommendResponse;
import com.project.jiamixiu.function.home.adapter.VideoCommentAdapter;
import com.project.jiamixiu.function.home.prenster.VideoDetailPrenster;
import com.project.jiamixiu.function.home.view.IvideoDetailView;
import com.project.jiamixiu.utils.DialogUtils;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.widget.RoundButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoDetailsActivity extends BaseActivity implements IvideoDetailView, VideoCommentAdapter.IitemOnClickListener {

    private String id;
    private String f_creatoruserid;//发布人id
    private VideoDetailPrenster prenster;
    private String videoThumbUrl;
    private VideoDetailResponse.Data responseDetail;
    private Dialog commentDialog;
    private int page = 0;
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
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private VideoCommentAdapter commentAdapter;

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
        commentAdapter = new VideoCommentAdapter(this);
        commentAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);
        DividerItemDecoration divier = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divier);
        refresh.setEnableRefresh(false);
        setListener();
        Glide.with(this)
                .load(videoThumbUrl)
                .into(video.thumbImageView);
    }

    private void setListener() {
        refresh.setOnLoadMoreListener(refreshLayout-> {
            System.out.println("refreshLayout");
            prenster.getCommentList(id,page+"","20",null);
        });
    }

    private void loadDetail(){
        prenster.loadDetail(id);
        prenster.getRecommendVideo(id);
    }

    @Override
    public void loadDetailSuccess(VideoDetailResponse.Data data) {
        responseDetail = data;
        tv_title.setText(data.name);
        tv_nickname.setText(data.nick);
        tv_aboutnum.setText(data.follow+"关注量");
        if (data.isfollow.equals("false")){
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
        if (Integer.parseInt(data.commentnum) > 0){
            //tv_all_comment.setVisibility(View.VISIBLE);
        }
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

    @Override
    public void commentSuccess() {
        ToastUtil.showTosat(this,"评论成功！");
    }

    @Override
    public void praiseSuccess() {
        ToastUtil.showTosat(this,"点赞成功！");
    }

    @Override
    public void favoriteSuccess() {
        ToastUtil.showTosat(this,"收藏成功！");
    }

    @Override
    public void shareSuccess() {
        ToastUtil.showTosat(this,"分享成功！");
    }

    @Override
    public void getVideoRecommend(List<VideoRecommendResponse.VideoInfo> infos) {
        if (infos != null && infos.size() > 0){
            prenster.getCommentList(id,"0","20",infos.get(0));
            VideoRecommendResponse.VideoInfo videoInfo = infos.get(0);
        } else {
            prenster.getCommentList(id,"0","20",null);
        }
    }

    @Override
    public void loadRecommendFail(String errmesg) {
        ToastUtil.showTosat(this,errmesg);
        prenster.getCommentList(id,page+"","20",null);
    }

    @Override
    public void getVideoCommentList(List<VideoCommentResponse.Data> list) {
        if (list.size() > 0){
            page ++;
            commentAdapter.addAllData(list);
        } else {
            refresh.setEnableLoadMore(false);
        }
        refresh.finishLoadMore();
    }

    @OnClick({R.id.iv_back,R.id.btn_about,R.id.fl_comment,R.id.fl_praise,R.id.fl_favorite,R.id.fl_share})
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
            case R.id.fl_comment:
                if (!TextUtils.isEmpty(f_creatoruserid))
                    toComment(f_creatoruserid);
                break;
            case R.id.fl_praise://点赞
                praise();
                break;
            case R.id.fl_favorite://收藏
                favorite();
                break;
            case R.id.fl_share://分享
                share();
                break;
        }
    }

    private void share() {
        if (responseDetail != null)
            prenster.share(responseDetail.f_id);
    }

    private void favorite() {
        if (responseDetail != null)
            prenster.favorite(responseDetail.f_id);
    }

    private void praise() {
        if (responseDetail != null)
        prenster.praise(responseDetail.f_id);
    }

    private void toComment(String userId) {
        View commentView = View.inflate(this,R.layout.dialog_homeview_comment_layout,null);
        final EditText et_content = commentView.findViewById(R.id.et_content);
        final RoundButton btn_commit = commentView.findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(view ->{
            if (TextUtils.isEmpty(et_content.getText().toString().trim())){
                if (commentDialog != null && commentDialog.isShowing())
                    commentDialog.dismiss();
                return;
            }
            if(responseDetail != null){
                commentDialog.dismiss();
                prenster.commit(responseDetail.f_id,et_content.getText().toString().trim(),userId);
            }

        });
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_content.getText().toString().trim())){
                    btn_commit.changeColor(-1,0xff524E4E);
                } else {
                    btn_commit.changeColor(-1,0xff00bbac);
                }
            }
        });
        Dialog dialog = DialogUtils.BottonDialog(this, commentView);
        commentDialog = dialog;
        dialog.show();
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

    /**
     * 更多
     */
    @Override
    public void getMoreVideos() {
        ToastUtil.showTosat(this,"getMoreVideos");
    }

    /**
     * 推荐视频
     * @param f_id
     */
    @Override
    public void headviewClick(String f_id) {

    }

    @Override//回复
    public void reply(String sourthId) {
        toComment(sourthId);
    }
}
