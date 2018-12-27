package com.reafor.jiamixiu.function.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseActivity;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.adapter.GridSpacingItemDecoration;
import com.reafor.jiamixiu.function.home.adapter.SearchAdapter;
import com.reafor.jiamixiu.function.home.prenster.SearchPrenster;
import com.reafor.jiamixiu.function.home.view.ISearchView;
import com.reafor.jiamixiu.function.login.LoginActivity;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchVideoActivity extends BaseActivity implements ISearchView, RvOncliclListener<VideoResponse.VideoInfo> {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private SearchPrenster prenster;
    private SearchAdapter adapter;

    @Override
    protected void initData() {
        super.initData();
        prenster = new SearchPrenster(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_video_layout);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, UIUtils.dip2px(this,12),false));
        adapter = new SearchAdapter(this,this);
        recyclerView.setAdapter(adapter);
        prenster.loadData("","","0","10");
    }

    @OnClick({R.id.tv_cancel})
    public void butterClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void loadSuccess(List<VideoResponse.VideoInfo> infos) {
        adapter.setDatas(infos);
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
    public void requestFinish() {

    }

    @Override
    public void onCick(RecyclerView.ViewHolder viewHolder, VideoResponse.VideoInfo item, int position) {
        Intent intent = new Intent(this, VideoDetailsActivity.class);
        intent.putExtra("id",item.f_id);
        startActivity(intent);
    }
}
