package com.reafor.jiamixiu.function.subscrite;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.SubscribeUsersResponse;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.VideoDetailsActivity;
import com.reafor.jiamixiu.function.login.LoginActivity;
import com.reafor.jiamixiu.function.subscrite.adapter.HeadViewAdapter;
import com.reafor.jiamixiu.function.subscrite.adapter.MySubAdapter;
import com.reafor.jiamixiu.function.subscrite.prenster.SubscritePrenster;
import com.reafor.jiamixiu.function.subscrite.view.ISubscriteView;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
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
    private ArrayList<VideoResponse.VideoInfo> original;

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("Send","setUserVisibleHint"+isVisibleToUser);
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
        Intent intent = new Intent(mContext, SubscriteDetailsActivity.class);
        intent.putExtra("id",item.f_id);
        intent.putExtra("page",page);
        intent.putExtra("list", datasCast());
        startActivity(intent);
    }

    private ArrayList<VideoResponse.VideoInfo> datasCast(){
        if (original == null){
            original = new ArrayList<>();
            VideoResponse res = new VideoResponse();
            int i = 0;
            for (AboutUserVideoResponse.VideoSruct sruct : adapter.getDatas()) {
                if (adapter.getItemViewType(i)== 0){
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
                i++;
            }
        }
        return original;
    }
}
