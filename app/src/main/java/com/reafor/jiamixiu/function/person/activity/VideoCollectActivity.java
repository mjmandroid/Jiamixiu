package com.reafor.jiamixiu.function.person.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.CollectVideoBean;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.VideoDetailsActivity;
import com.reafor.jiamixiu.function.person.adapter.CollectVideoAdapter;
import com.reafor.jiamixiu.function.person.inter.ICollectVideoView;
import com.reafor.jiamixiu.function.person.presenter.CollectVideoPresenter;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.CustomerToolbar;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoCollectActivity extends AppCompatActivity implements ICollectVideoView {
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.lv_video)
    ListView lvVideo;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    CollectVideoAdapter videoAdapter;
    CollectVideoPresenter videoPresenter;
    ArrayList<CollectVideoBean.VideoData> list = new ArrayList<>();
    LoadingDialog loadingDialog;
    int page = 0;
    private ArrayList<VideoResponse.VideoInfo> original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_collect);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        toolbar.setTitle("我的收藏");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        initPtrFrame();
        videoAdapter = new CollectVideoAdapter(this,list);
        videoAdapter.setVideoListener(new CollectVideoAdapter.VideoListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(VideoCollectActivity.this,VideoCollectDetailActivity.class);
                intent.putExtra("id",list.get(position).f_id);
                intent.putExtra("videoThumbUrl",list.get(position).coverimg);
                intent.putExtra("page",page);
                intent.putExtra("list", datasCast());
                startActivity(intent);
            }

            @Override
            public void onDelete(String id) {
                showDel(id);
            }
        });
        lvVideo.setAdapter(videoAdapter);
        videoPresenter = new CollectVideoPresenter(this);
        loadingDialog.show();
        videoPresenter.loadData(page);
    }

    @Override
    public void onLoadData(CollectVideoBean b) {
        smartRefreshLayout.finishRefresh();
        loadingDialog.dismiss();
        list.clear();
        if (b.data != null && b.data.size() > 0){
            tvNothing.setVisibility(View.GONE);
            lvVideo.setVisibility(View.VISIBLE);
            list.clear();
            list.addAll(b.data);
            videoAdapter.notifyDataSetChanged();
        }else {
            tvNothing.setVisibility(View.VISIBLE);
            lvVideo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMoreData(CollectVideoBean b) {

        if (b.data != null && b.data.size() > 0){
            page ++;
            list.addAll(b.data);
            videoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteSuccess() {
        page = 0;
        videoPresenter.loadData(page);
    }

    @Override
    public void onDeleteFail() {
        UIUtils.showToast(this,"删除成功");
    }

    @Override
    public void onShowToast(String s) {
        smartRefreshLayout.finishRefresh();
        loadingDialog.dismiss();
    }

    @Override
    public void onLoadFail() {

        smartRefreshLayout.finishRefresh();
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

    }

    private void showDel(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否删除该视频？");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoPresenter.delete(id);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.create().show();
    }
    //下拉刷新
    private void initPtrFrame() {
        //设置全局的Header构建器

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setDisableContentWhenRefresh(true);
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setTextSizeTitle(14);
                layout.setPrimaryColorsId(R.color.sf_header_theme, R.color.sf_header_tv);//全局设置主题颜色
                classicsHeader.setDrawableArrowSize(15);
//                FalsifyHeader falsifyHeader = new FalsifyHeader(context);
                return classicsHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                videoPresenter.loadData(page);
            }
        });
        smartRefreshLayout.setDisableContentWhenRefresh(true);
    }

    private ArrayList<VideoResponse.VideoInfo> datasCast(){
        if (original == null){
            original = new ArrayList<>();
            VideoResponse res = new VideoResponse();
            for (CollectVideoBean.VideoData sruct : list) {
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
