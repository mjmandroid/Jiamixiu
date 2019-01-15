package com.reafor.jiamixiu.function.home;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.adapter.VerticalViewPagerAdapter;
import com.reafor.jiamixiu.function.home.prenster.HomeVideoPrenster;
import com.reafor.jiamixiu.function.home.view.IhomeVideoView;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.widget.VerticalViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;

public class HomeVideoFragment extends BaseFragment implements IhomeVideoView {
    @BindView(R.id.view_pager)
    VerticalViewPager vvpBackPlay;
    @BindView(R.id.srl_page)
    SmartRefreshLayout refreshLayout;
    private VerticalViewPagerAdapter pagerAdapter;
    private int page = 0;
    private HomeVideoPrenster prenster;
    private int STATE_REFRESH = 0;
    private int STATE_LOADMORE = 1;
    private int LoadState = STATE_REFRESH;
    

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home_video_layout,null);
        ButterKnife.bind(this,view);
        try {
            //滑动灵敏度调整
            Class clazz = vvpBackPlay.getClass().getSuperclass();
            Field field = clazz.getDeclaredField("mFlingDistance");
            field.setAccessible(true);
            System.out.println("----"+field.get(vvpBackPlay));
            field.set(vvpBackPlay,5);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----"+"error"+e.toString());
        }
        vvpBackPlay.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            LoadState = STATE_LOADMORE;
            prenster.preloadData("","","",page+"","20");
        });
        prenster.preloadData("","","",page+"","20");
        return view;
    }

    @Override
    public void initData() {
        prenster = new HomeVideoPrenster(this);
    }

    @Override
    public void laodSuccess(List<VideoResponse.VideoInfo> infos) {
        if (pagerAdapter == null){
            pagerAdapter = new VerticalViewPagerAdapter(getChildFragmentManager());
            vvpBackPlay.setVertical(true);
            vvpBackPlay.setOffscreenPageLimit(10);
            vvpBackPlay.setAdapter(pagerAdapter);
        }
        if (LoadState == STATE_REFRESH){
            pagerAdapter.setData((ArrayList<VideoResponse.VideoInfo>) infos);
            pagerAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            if (infos != null && infos.size() > 0)
                page++;
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
        ToastUtil.showTosat(mContext,error_msg);
        if (LoadState == STATE_REFRESH){
            refreshLayout.finishRefresh();
        } else if (LoadState == STATE_LOADMORE){
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void laodFinish() {

    }

    @Override
    public void onDestroy() {
        Jzvd.releaseAllVideos();
        super.onDestroy();
    }
}
