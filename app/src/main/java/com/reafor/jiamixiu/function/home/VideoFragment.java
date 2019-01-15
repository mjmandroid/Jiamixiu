package com.reafor.jiamixiu.function.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reafor.jiamixiu.BaseApplication;
import com.reafor.jiamixiu.MainActivity;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseApi;
import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.bean.VideoCommentResponse;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.adapter.VideoCommentAdapter;
import com.reafor.jiamixiu.function.home.prenster.VideoPrenster;
import com.reafor.jiamixiu.function.home.view.IvideoView;
import com.reafor.jiamixiu.function.login.LoginActivity;
import com.reafor.jiamixiu.utils.DialogUtils;
import com.reafor.jiamixiu.utils.OssUtils;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.utils.UrlConst;
import com.reafor.jiamixiu.widget.RoundButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoFragment extends BaseFragment implements IvideoView {
    @BindView(R.id.video)
    JzvdStd video;
    @BindView(R.id.iv_nick)
    ImageView iv_nick;
    @BindView(R.id.iv_praise)
    ImageView iv_praise;
    @BindView(R.id.tv_praise_num)
    TextView tv_praise_num;
    @BindView(R.id.iv_comment)
    ImageView iv_comment;
    @BindView(R.id.tv_comment_num)
    TextView tv_comment_num;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    @BindView(R.id.tv_share_num)
    TextView tv_share_num;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String url;
    private boolean visibleToUser;
    private boolean isViewCreated;
    private VideoResponse.VideoInfo videoInfo;
    private String index;
    private VideoPrenster prenster;
    private int page = 0;
    private Dialog dialog,commentDialog,inputDialog;
    private final int STATE_REFRESH = 0,STATE_LOADMORE = 1;
    private int load_state = STATE_REFRESH;
    private VideoCommentAdapter commentAdapter;
    private SmartRefreshLayout refreshLayout;

    @Override
    public View initView() {
        Bundle bundle = getArguments();
        if (bundle != null){
            index = bundle.getString("index");
            videoInfo = (VideoResponse.VideoInfo) bundle.getSerializable("info");
            if (videoInfo != null){
                OSS mOss = OssUtils.getInstance().getOss();
                try {
                    url = mOss.presignConstrainedObjectURL("jiamixiu", videoInfo.ossid+".mp4",30 * 60);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }
        View view = View.inflate(mContext, R.layout.fragment_playvideo_layout,null);
        ButterKnife.bind(this,view);
        isViewCreated = true;
        if (videoInfo != null && !TextUtils.isEmpty(url)){
            Glide.with(this)
                    .load(videoInfo.coverimg)
                    .into(video.thumbImageView);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.icon_default_head)
                    .error(R.mipmap.icon_default_head);
            Glide.with(this).load(UrlConst.baseUrl.substring(0,UrlConst.baseUrl.length()-1)+videoInfo.avator).apply(options).into(iv_nick);
            String proxyUrl = BaseApplication.getProxy(mContext).getProxyUrl(url);//加入缓存
            JZDataSource jzDataSource = new JZDataSource(proxyUrl);
            jzDataSource.looping = true;
            video.setUp(jzDataSource, Jzvd.SCREEN_WINDOW_FULLSCREEN);
            Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            Jzvd.SAVE_PROGRESS = true;
        }
        setViewData();
        video.findViewById(R.id.current).setVisibility(View.INVISIBLE);
        video.findViewById(R.id.bottom_seek_progress).setVisibility(View.INVISIBLE);
        video.findViewById(R.id.total).setVisibility(View.INVISIBLE);
        video.findViewById(R.id.clarity).setVisibility(View.INVISIBLE);
        video.findViewById(R.id.fullscreen).setVisibility(View.INVISIBLE);
        video.findViewById(R.id.back).setVisibility(View.INVISIBLE);
        video.findViewById(R.id.battery_time_layout).setVisibility(View.INVISIBLE);
        return view;
    }

    private void setViewData() {
        if (videoInfo != null){
            tv_praise_num.setText(videoInfo.favoritenum);
            tv_comment_num.setText(videoInfo.commentnum);
            tv_share_num.setText(videoInfo.sharenum);
            tv_title.setText(videoInfo.name);
            if (!TextUtils.isEmpty(videoInfo.favoritenum)){
                int fav = Integer.parseInt(videoInfo.favoritenum);
                if (fav > 0){
                    iv_praise.setBackgroundResource(R.mipmap.icon_praise_red);
                }else {
                    iv_praise.setBackgroundResource(R.mipmap.icon_video_praise);
                }
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        visibleToUser = isVisibleToUser;
        if (isVisibleToUser && isViewCreated && videoInfo != null){
            video.startVideo();
        }

    }

    @OnClick({R.id.iv_praise,R.id.iv_comment,R.id.iv_share})
    public void btnOnClick(View view){
        switch (view.getId()){
            case R.id.iv_praise:
                if (UIUtils.isExpireon()){
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    favorite();
                }
                break;
            case R.id.iv_comment:
                if (UIUtils.isExpireon()){
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    page = 0;
                    if (videoInfo != null)
                   prenster.getCommentList(videoInfo.f_id,page+"","20");
                }
                break;
            case R.id.iv_share:
                showShareDialog();
                break;
        }
    }

    /**
     * 收藏
     */
    private void favorite() {
        if (videoInfo != null)
        prenster.favorite(videoInfo.f_id);
    }

    private void showCommentDialog(List<VideoCommentResponse.Data> dataList) {
        if (commentDialog == null){
            View view = View.inflate(mContext,R.layout.dialog_comment_layout,null);
            view.findViewById(R.id.tv_cancel).setOnClickListener(v->{
                commentDialog.dismiss();
            });
            refreshLayout = view.findViewById(R.id.refresh);
            RecyclerView recyclerView = view.findViewById(R.id.rv);
            commentAdapter = new VideoCommentAdapter(mContext);
            commentAdapter.setDatas(dataList);
            commentAdapter.setListener(new VideoCommentAdapter.IitemOnClickListener() {
                @Override
                public void getMoreVideos() {
                    ToastUtil.showToastOnCenter(mContext,"举报成功！");
                }

                @Override
                public void headviewClick(String f_id, String coverimg) {

                }

                @Override
                public void reply(String sourthId) {
                    toComment(sourthId);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(commentAdapter);
            DividerItemDecoration divier = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(divier);
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
                load_state = STATE_LOADMORE;
                prenster.getCommentList(videoInfo.f_id,page+"","20");
            });
            commentDialog = DialogUtils.BottonDialog(mContext, view);
            commentDialog.show();
            commentDialog.setOnDismissListener(dialog -> {
                commentDialog = null;
            });
            if (dataList.size() > 0){
                page ++;
            }
        } else {
            commentAdapter.addAllData(dataList);
            if (load_state == STATE_REFRESH){
                refreshLayout.finishRefresh();
            } else {
                refreshLayout.finishLoadMore();
                if (dataList.size() == 0){
                    refreshLayout.setEnableLoadMore(false);
                } else {
                    page ++;
                }
            }
        }

    }


    private void toComment(String userId) {
        View commentView = View.inflate(mContext,R.layout.dialog_homeview_comment_layout,null);
        final EditText et_content = commentView.findViewById(R.id.et_content);
        final RoundButton btn_commit = commentView.findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(view ->{
            if (TextUtils.isEmpty(et_content.getText().toString().trim())){
                if (inputDialog != null && inputDialog.isShowing())
                    inputDialog.dismiss();
                return;
            }
            prenster.commit(videoInfo.f_id,et_content.getText().toString().trim(),userId);
            inputDialog.dismiss();

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
                    btn_commit.changeColor(-1,0xFFB3B3B3);
                } else {
                    btn_commit.changeColor(-1,0xff00bbac);
                }
            }
        });
        Dialog dialog = DialogUtils.BottonDialog(mContext, commentView);
        inputDialog = dialog;
        dialog.show();
    }

    private void showShareDialog() {
        View view = View.inflate(mContext,R.layout.dialog_home_video_share_layout,null);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        LinearLayout parent = view.findViewById(R.id.ll_share);
        for (int i = 0; i < parent.getChildCount(); i++) {
            parent.getChildAt(i).setOnClickListener(v -> dialog.dismiss());
        }
        dialog = DialogUtils.BottonDialog(mContext, view);
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (visibleToUser){
            video.startVideo();
        }
    }


    @Override
    public void initData() {
        prenster = new VideoPrenster(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (JzvdStd.CURRENT_STATE_PLAYING == video.currentState){
            JzvdStd.goOnPlayOnPause();
        }
    }

    @Override
    public void getCommentList(List<VideoCommentResponse.Data> dataList) {
        showCommentDialog(dataList);
    }

    @Override
    public void loadFail(String errmsg) {
        ToastUtil.showToastOnCenter(mContext,errmsg);
    }

    @Override
    public void loadFinish() {

    }

    @Override
    public void commentSuccess() {
        if (commentAdapter != null){
            commentAdapter.clearData();
            page = 0;
            refreshLayout.setEnableLoadMore(true);
            prenster.getCommentList(videoInfo.f_id,page+"","20");
        }
    }

    @Override
    public void praiseSuccess() {
        iv_praise.setBackgroundResource(R.mipmap.icon_praise_red);
        tv_praise_num.setText("1");
    }
}
