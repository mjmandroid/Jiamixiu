package com.reafor.jiamixiu.function.home;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseActivity;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.adapter.VerticalViewPagerAdapter;
import com.reafor.jiamixiu.function.home.prenster.HomeVideoPrenster;
import com.reafor.jiamixiu.function.home.prenster.VideoListPrenster;
import com.reafor.jiamixiu.function.home.view.IvideoListView;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.VerticalViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JzvdStd;

public class VideoDetailsActivity_v2 extends BaseActivity implements IvideoListView {
    @BindView(R.id.view_pager)
    VerticalViewPager verticalViewPager;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    private VerticalViewPagerAdapter pagerAdapter;
    private int page = 0;
    private int STATE_REFRESH = 0;
    private int STATE_LOADMORE = 1;
    private int LoadState = STATE_REFRESH;
    private String id;
    private ArrayList<VideoResponse.VideoInfo> dataList;
    private int currentIndex = 0;
    private String categoryid = "";
    private VideoListPrenster prenster;

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null){
            id = intent.getStringExtra("id");
            page = intent.getIntExtra("page",0);
            categoryid = intent.getStringExtra("categoryid");
            dataList = (ArrayList<VideoResponse.VideoInfo>) intent.getSerializableExtra("list");
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).f_id.equals(id)){
                    currentIndex = i;
                    break;
                }
            }
        }
        prenster = new VideoListPrenster(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_video_details_v2);
        ButterKnife.bind(this);
        try {
            //滑动灵敏度调整
            Class clazz = verticalViewPager.getClass().getSuperclass();
            Field field = clazz.getDeclaredField("mFlingDistance");
            field.setAccessible(true);
            field.set(verticalViewPager,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pagerAdapter = new VerticalViewPagerAdapter(getSupportFragmentManager());
        if (dataList != null){
            pagerAdapter.setData(dataList);
        }
        refreshLayout.setEnableRefresh(false);
        verticalViewPager.setVertical(true);
        verticalViewPager.setOffscreenPageLimit(10);
        verticalViewPager.setAdapter(pagerAdapter);
        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == pagerAdapter.getDataSize() - 1) {
                    refreshLayout.setEnableAutoLoadMore(true);
                    refreshLayout.setEnableLoadMore(true);
                } else {
                    refreshLayout.setEnableAutoLoadMore(false);
                    refreshLayout.setEnableLoadMore(false);
                }
                if (position == 0){
                    refreshLayout.setEnableRefresh(true);
                } else {
                    refreshLayout.setEnableRefresh(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        verticalViewPager.setCurrentItem(currentIndex);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            LoadState = STATE_LOADMORE;
            prenster.acquireList("","",categoryid,page+"","20");
        });
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            LoadState = STATE_REFRESH;
            page = 0;
            prenster.acquireList("","",categoryid,page+"","20");
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void laodSuccess(List<VideoResponse.VideoInfo> infos) {

        if (LoadState == STATE_REFRESH){
            if (infos != null && infos.size() > 0){
                pagerAdapter.setData((ArrayList<VideoResponse.VideoInfo>) infos);
                currentIndex = 0;
                verticalViewPager.setCurrentItem(currentIndex);
                pagerAdapter.notifyDataSetChanged();
                page ++;
            }
            refreshLayout.finishRefresh();
        } else {
            if (infos != null && infos.size() > 0){
                pagerAdapter.addData((ArrayList<VideoResponse.VideoInfo>) infos);
                pagerAdapter.notifyDataSetChanged();
                page ++;
            }
            refreshLayout.finishLoadMore();
        }

    }

    @Override
    public void loadFail(String error_msg) {
        ToastUtil.showTosat(this,error_msg);
        if (LoadState == STATE_REFRESH){
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void laodFinish() {

    }
}
