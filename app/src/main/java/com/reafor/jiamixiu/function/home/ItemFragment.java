package com.reafor.jiamixiu.function.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.bean.ItemBannerResponse;
import com.reafor.jiamixiu.bean.ItemTagsResponse;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.adapter.GridSpacingItemDecoration;
import com.reafor.jiamixiu.function.home.adapter.ItemFragmentAdapter;
import com.reafor.jiamixiu.function.home.prenster.HomePrenster;
import com.reafor.jiamixiu.function.home.prenster.ItemFragmentPresenter;
import com.reafor.jiamixiu.function.home.view.IitemFragmentView;
import com.reafor.jiamixiu.interfaces.RvOncliclListener;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFragment extends BaseFragment implements IitemFragmentView {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tb_sub)
    TabLayout tb_sub;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    private int STATE_REFRESH = 0;
    private int STATE_LOADMORE = 1;
    private int LoadState = STATE_REFRESH;

    private ItemFragmentAdapter adapter;
    private ItemFragmentPresenter presenter;
    private String fid;//分类id
    private int page = 0;
    private String categoryid = "";

    public void setTag(String tag) {
        this.categoryid = tag;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home_item_layout,null);
        ButterKnife.bind(this,view);
        initBanner();
        initRefresh();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, UIUtils.dip2px(mContext,12),false));
        adapter = new ItemFragmentAdapter(mContext);
        recyclerView.setAdapter(adapter);
        presenter = new ItemFragmentPresenter(this);
        presenter.preloadData("","",categoryid,page+"","20");
        initEvent();
        return view;
    }

    private void initEvent() {
        adapter.setListener(new RvOncliclListener<VideoResponse.VideoInfo>() {
            @Override
            public void onCick(RecyclerView.ViewHolder viewHolder, VideoResponse.VideoInfo item, int position) {
                Intent intent = new Intent(mContext, VideoDetailsActivity_v2.class);
                intent.putExtra("id",item.f_id);
                intent.putExtra("videoThumbUrl",item.coverimg);
                intent.putExtra("page",page);
                intent.putExtra("categoryid",categoryid);
                intent.putExtra("list", (ArrayList) adapter.getDatas());
                startActivity(intent);
            }
        });
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                LoadState = STATE_REFRESH;
                presenter.preloadData("","",categoryid,page+"","20");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LoadState = STATE_LOADMORE;
                presenter.preloadData("","",categoryid,page+"","20");
            }
        });

    }

    private void initBanner() {
        banner.setIndicatorGravity(BannerConfig.RIGHT);
    }

    @Override
    public void initData() {

    }

    @Override
    public void laodSuccess(List<VideoResponse.VideoInfo> infos) {
        if (LoadState == STATE_REFRESH){
            adapter.setDatas(infos);
            refreshLayout.finishRefresh();
            if (infos != null && infos.size() > 0)
                page++;
        } else {
            if (infos != null && infos.size() > 0){
                adapter.addDatas(infos);
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
    public void getItemTags(List<ItemTagsResponse.TagInfo> tagList) {
        for (ItemTagsResponse.TagInfo info : tagList) {
            tb_sub.addTab(tb_sub.newTab().setText(info.name));
        }
        HomePrenster.setTabWidth(tb_sub,15);
    }

    @Override
    public void getItemBanner(List<ItemBannerResponse.BannerInfo> infos) {
        banner.setImageLoader(new GlideImageLoader());
        if (infos == null){
            //没有获取到图片显示默认图片
            ArrayList<Integer> img = new ArrayList<>();
            img.add(R.mipmap.icon_banner_default);
            banner.setImages(img);
            banner.start();
            return;
        }
        List<String> imageUrl = new ArrayList<>();
        for (ItemBannerResponse.BannerInfo info : infos) {
            imageUrl.add(info.imgurl);
        }
        banner.setImages(imageUrl);
        banner.start();
    }

    @Override
    public void updateList(String fid) {
        if (this.fid != null){
            return;
        }
        this.fid = fid;
        presenter.loadTags("");
        presenter.loadBannerList(fid);
    }
}
