package com.reafor.jiamixiu.function.home;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseActivity;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.MyWorkBean;
import com.reafor.jiamixiu.bean.UserInfoResponse;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.adapter.MoreUsersAdapter;
import com.reafor.jiamixiu.function.home.prenster.MoreUsersPrenster;
import com.reafor.jiamixiu.function.home.view.ImoreUsersView;
import com.reafor.jiamixiu.function.login.LoginActivity;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.DialogUtils;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.CustomerToolbar;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查看更多关注用户
 */
public class MoreUsersActivity extends BaseActivity implements ImoreUsersView, RvOncliclListener<AboutUserVideoResponse.VideoSruct> {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.refresh)
    RefreshLayout refresh;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private MoreUsersPrenster prenster;
    private String id;
    private LoadingDialog loadingDialog;
    private int page = 0;
    private int load_state = 0,state_refresh = 0,state_loadmore = 1;
    private MoreUsersAdapter adapter;
    private String avator,nick;


    @Override
    protected void initData() {
        super.initData();
        id = getIntent().getStringExtra("id");
        avator = getIntent().getStringExtra("avator");
        nick = getIntent().getStringExtra("nick");
        prenster = new MoreUsersPrenster(this);
        loadingDialog = new LoadingDialog(this);
        adapter = new MoreUsersAdapter(this);
        adapter.setListener(this);
        adapter.setImgUrlDefault(avator);
        adapter.setNick(nick);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_home_more_users_layout);
        ButterKnife.bind(this);
        toolbar.setTitle("个人中心");
        toolbar.setToolbarLisenter(()-> finish());
        toolbar.getIv_menu().setBackgroundResource(R.mipmap.icon_share);
        toolbar.getIv_menu().setOnClickListener((view)->{
            showShareDialog();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setListener();
        loadingDialog.show();
        prenster.getUserInfo();
    }
    Dialog dialog = null;
    private void showShareDialog() {
        View view = View.inflate(this,R.layout.dialog_home_video_share_layout,null);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        LinearLayout parent = view.findViewById(R.id.ll_share);
        for (int i = 0; i < parent.getChildCount(); i++) {
            parent.getChildAt(i).setOnClickListener(v -> dialog.dismiss());
        }
        dialog = DialogUtils.BottonDialog(this, view);
        dialog.show();
    }

    private void setListener() {
        refresh.setOnRefreshListener((refreshLayout -> {
            page = 0;
            load_state = state_refresh;
            prenster.getUserInfo();
        }));
        refresh.setOnLoadMoreListener((refreshLayout -> {
            load_state = state_loadmore;
            prenster.getAboutUserVideos(null,id,page+"","20",false);
        }));
    }

    @Override
    public void getUserInfo(UserInfoResponse.UserInfo info) {
        prenster.getAboutUserVideos(info,id,"0","20",true);
    }

    @Override
    public void loadFail(String errmsg) {
        if (errmsg.contains("用户未登")){
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            ToastUtil.showTosat(this,errmsg);
        }
    }

    @Override
    public void requestComlete() {
        loadingDialog.dismiss();
    }

    @Override
    public void getUserVideos(List<AboutUserVideoResponse.VideoSruct> videos) {
        if (load_state == state_refresh){
            adapter.setDatas(videos);
            if (videos!= null && videos.size() > 0)
                page = 1;
            refresh.finishRefresh();
        } else {
            if (videos!= null && videos.size() > 0){
                adapter.addAllData(videos);
                page ++;
            }
            refresh.finishLoadMore();
        }


    }

    @Override
    public void onCick(RecyclerView.ViewHolder viewHolder, AboutUserVideoResponse.VideoSruct item, int position) {
        Intent intent = new Intent(this, MoreUserDetailsActivity.class);
        intent.putExtra("id",item.f_id);
        intent.putExtra("page",page);
        intent.putExtra("list", datasCast());
        startActivity(intent);
    }

    ArrayList<VideoResponse.VideoInfo> original;
    private ArrayList<VideoResponse.VideoInfo> datasCast(){
        if (original == null){
            original = new ArrayList<>();
            VideoResponse res = new VideoResponse();
            List<AboutUserVideoResponse.VideoSruct> datas = adapter.getDatas();
            int idx = 0;
            for (AboutUserVideoResponse.VideoSruct sruct : datas) {
                if (adapter.getItemViewType(idx) == 0){
                    VideoResponse.VideoInfo info = res.new VideoInfo();
                    info.f_id = sruct.f_id;
                    info.viewnum = sruct.viewnum;
                    info.commentnum = sruct.commentnum;
                    info.sharenum = sruct.sharenum;
                    info.favoritenum = sruct.favoritenum;
                    info.likenum = sruct.likenum;
                    info.name = sruct.name;
                    info.description = sruct.description;
                    info.thumbnail = sruct.thumbnail;
                    info.coverimg = sruct.coverimg;
                    info.f_creatoruserid = sruct.f_creatoruserid;
                    info.nick = sruct.nick;
                    info.avator = sruct.avator;
                    info.ossid = sruct.ossid;
                    info.f_creatortime = sruct.f_creatortime;
                    original.add(info);
                }
                idx++;
            }
        }
        return original;
    }
}
