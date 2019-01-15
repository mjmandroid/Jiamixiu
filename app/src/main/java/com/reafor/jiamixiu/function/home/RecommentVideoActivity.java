package com.reafor.jiamixiu.function.home;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseActivity;
import com.reafor.jiamixiu.bean.VideoRecommendResponse;
import com.reafor.jiamixiu.function.home.adapter.RecommentAdapter;
import com.reafor.jiamixiu.function.home.prenster.RecommentPrenster;
import com.reafor.jiamixiu.function.home.view.IRecommentView;
import com.reafor.jiamixiu.function.login.LoginActivity;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.CustomerToolbar;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommentVideoActivity extends BaseActivity implements IRecommentView, RvOncliclListener<VideoRecommendResponse.VideoInfo> {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    RefreshLayout refresh;
    private String id;
    private int page = 0,LOAD_STATE = 0,STATE_REFRESH = 0,STATE_LOADMORE = 1;
    private LoadingDialog loadingDialog;
    private RecommentPrenster prenster;
    private RecommentAdapter adapter;

    @Override
    protected void initData() {
        super.initData();
        id = getIntent().getStringExtra("id");
        adapter = new RecommentAdapter(this, this);
        prenster = new RecommentPrenster(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_home_video_recomment_layout);
        ButterKnife.bind(this);
        toolbar.setTitle("推荐视频");
        toolbar.setToolbarLisenter(()-> finish());
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        prenster.loadRecommentList(id,page+"","20");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divier = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divier);
        recyclerView.setAdapter(adapter);
        initEvent();
    }

    private void initEvent() {
        refresh.setOnRefreshListener((refreshLayout -> {
            LOAD_STATE = STATE_REFRESH;
            page = 0;
            prenster.loadRecommentList(id,page+"","20");
        }));
        refresh.setOnLoadMoreListener((refreshLayout -> {
            LOAD_STATE = STATE_LOADMORE;
            prenster.loadRecommentList(id,page+"","20");
        }));
    }

    @Override
    public void loadRecommentSuccess(List<VideoRecommendResponse.VideoInfo> list) {
        if (LOAD_STATE == STATE_REFRESH){
            adapter.setDatas(list);
            if (list != null && list.size() > 0){
                page ++;
            }
            refresh.finishRefresh();
        } else {
            if (list != null && list.size() > 0){
                adapter.addAllData(list);
                page ++;
            }
            refresh.finishLoadMore();
        }
    }

    @Override
    public void loadRecommentFail(String errorMsg) {
        if (errorMsg.contains("用户未登")){
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            ToastUtil.showTosat(this,errorMsg);
        }
    }

    @Override
    public void requestComplete() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCick(RecyclerView.ViewHolder viewHolder, VideoRecommendResponse.VideoInfo item, int position) {
        Intent intent = new Intent(this, VideoDetailsActivity.class);
        intent.putExtra("id",item.f_id);
        startActivity(intent);
    }
}
