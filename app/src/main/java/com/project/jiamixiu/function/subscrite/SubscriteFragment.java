package com.project.jiamixiu.function.subscrite;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseFragment;
import com.project.jiamixiu.bean.AboutUserVideoResponse;
import com.project.jiamixiu.bean.SubscribeUsersResponse;
import com.project.jiamixiu.function.home.VideoDetailsActivity;
import com.project.jiamixiu.function.login.LoginActivity;
import com.project.jiamixiu.function.subscrite.adapter.HeadViewAdapter;
import com.project.jiamixiu.function.subscrite.adapter.MySubAdapter;
import com.project.jiamixiu.function.subscrite.prenster.SubscritePrenster;
import com.project.jiamixiu.function.subscrite.view.ISubscriteView;
import com.project.jiamixiu.interfaces.RvOncliclListener;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscriteFragment extends BaseFragment implements ISubscriteView, RvOncliclListener<AboutUserVideoResponse.VideoSruct> {

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    private SubscritePrenster prenster;
    private LoadingDialog loadingDialog;
    private HeadViewAdapter headViewAdapter;
    private int page = 0;
    private String fid;
    private int STATE_REFRESH = 0,STATE_LOADMORO = 1;
    private int loadState = STATE_REFRESH;
    private MySubAdapter adapter;
    public static String headUrl="",headName="";
    private boolean isUpdate;

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_subscrite_layout,null);
        ButterKnife.bind(this,view);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        refresh.setOnRefreshListener(refreshLayout -> {
            page = 0;
            loadState = STATE_REFRESH;
            prenster.loadHeadData();
        });
        refresh.setOnLoadMoreListener(refreshLayout -> {
            loadState = STATE_LOADMORO;
            prenster.loadVideosDatas(fid,page+"","20",false,null);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void updateList(String fid) {
        if (!isUpdate){
            isUpdate = true;
            adapter.clearData();
            loadingDialog.show();
            prenster.loadHeadData();
        }
    }

    @Override
    public void initData() {
        headViewAdapter = new HeadViewAdapter(mContext);
        adapter = new MySubAdapter(mContext,this);
        prenster = new SubscritePrenster(this);
        loadingDialog = new LoadingDialog(mContext);
    }

    @Override
    public void loadHeadSuccess(List<SubscribeUsersResponse.Data> dataList) {
        headViewAdapter.setDatas(dataList);
        if (dataList.size() > 0){
            fid = dataList.get(0).f_id;
            headUrl = dataList.get(0).avator;
            headName = dataList.get(0).nick;
            prenster.loadVideosDatas(fid,page+"","20",true,dataList);
        }
    }

    @Override
    public void loadFail(String errmsg) {
        if (errmsg.contains("用户未登")){
            isUpdate = false;
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            ToastUtil.showTosat(mContext,errmsg);
        }
        loadingDialog.dismiss();
    }

    @Override
    public void requestCompleted() {
        loadingDialog.dismiss();
    }

    @Override
    public void loadVideosSuccess(List<AboutUserVideoResponse.VideoSruct> sructList) {
        if (loadState == STATE_REFRESH){
            refresh.finishRefresh();
            adapter.setDatas(sructList);
        } else {
            refresh.finishLoadMore();
            adapter.addAllData(sructList);
        }
        if (sructList.size() > 0)
            page ++;
    }

    @Override
    public void onCick(RecyclerView.ViewHolder viewHolder, AboutUserVideoResponse.VideoSruct item, int position) {
        Intent intent = new Intent(mContext, VideoDetailsActivity.class);
        intent.putExtra("id",item.f_id);
        startActivity(intent);
    }
}
