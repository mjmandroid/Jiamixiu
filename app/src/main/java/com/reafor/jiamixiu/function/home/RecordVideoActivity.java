package com.reafor.jiamixiu.function.home;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseActivity;
import com.reafor.jiamixiu.bean.CollectVideoBean;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.bean.VidoeRecordResponse;
import com.reafor.jiamixiu.function.home.adapter.RecordVideoAdapter;
import com.reafor.jiamixiu.function.home.prenster.RecordVideoPrenster;
import com.reafor.jiamixiu.function.home.view.IRecordVideoView;
import com.reafor.jiamixiu.function.login.LoginActivity;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordVideoActivity extends BaseActivity implements IRecordVideoView, RvOncliclListener<VidoeRecordResponse.Data> {

    @BindView(R.id.refresh)
    RefreshLayout refresh;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    View iv_back;
    private RecordVideoPrenster prenster;
    private RecordVideoAdapter adapter;
    private LoadingDialog loadingDialog;
    private int page = 0;
    private int STATE_REFRESH = 0,STATE_LOADMORE = 2;
    private int load_state = 0;
    private ArrayList<VideoResponse.VideoInfo> original;

    @Override
    protected void initData() {
        super.initData();
        prenster = new RecordVideoPrenster(this);
        loadingDialog = new LoadingDialog(this);
        adapter = new RecordVideoAdapter(this, this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_record_vodeo_layout);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divier = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divier);
        recyclerView.setAdapter(adapter);
        iv_back.setOnClickListener(view->finish());
        initListener();
        loadingDialog.show();
        prenster.loadVideoList(page+"","20");
    }

    private void initListener() {
        refresh.setOnRefreshListener(refreshLayout -> {
            page = 0;
            load_state = STATE_REFRESH;
            prenster.loadVideoList(page+"","20");
        });
        refresh.setOnLoadMoreListener(refreshLayout -> {
            load_state = STATE_LOADMORE;
            prenster.loadVideoList(page+"","20");
        });
    }

    @Override
    public void loadSuccess(List<VidoeRecordResponse.Data> dataList) {
        if(load_state == STATE_REFRESH){
            adapter.setDatas(dataList);
            refresh.finishRefresh();
            if (dataList != null && dataList.size() > 0)
                page ++;
        } else {
            if (dataList != null && dataList.size() > 0){
                adapter.addAllData(dataList);
                page ++;
            }
            refresh.finishLoadMore();
        }
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
    public void requestCompleted() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCick(RecyclerView.ViewHolder viewHolder, VidoeRecordResponse.Data item, int position) {
        Intent intent = new Intent(this, RecordVideoDetailActivity.class);
        intent.putExtra("id",item.f_id);
        intent.putExtra("page",page);
        intent.putExtra("list", datasCast());
        startActivity(intent);
    }

    private ArrayList<VideoResponse.VideoInfo> datasCast(){
        if (original == null){
            original = new ArrayList<>();
            VideoResponse res = new VideoResponse();
            for (VidoeRecordResponse.Data sruct : adapter.getDatas()) {
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
        }
        return original;
    }
}
