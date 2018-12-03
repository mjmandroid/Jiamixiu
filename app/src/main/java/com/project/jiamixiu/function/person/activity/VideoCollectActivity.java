package com.project.jiamixiu.function.person.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.CollectVideoBean;
import com.project.jiamixiu.function.home.VideoDetailsActivity;
import com.project.jiamixiu.function.person.adapter.CollectVideoAdapter;
import com.project.jiamixiu.function.person.inter.ICollectVideoView;
import com.project.jiamixiu.function.person.presenter.CollectVideoPresenter;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.LoadingDialog;

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

    CollectVideoAdapter videoAdapter;
    CollectVideoPresenter videoPresenter;
    ArrayList<CollectVideoBean.VideoData> list = new ArrayList<>();
    LoadingDialog loadingDialog;
    int page = 0;
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
        videoAdapter = new CollectVideoAdapter(this,list);
        videoAdapter.setVideoListener(new CollectVideoAdapter.VideoListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(VideoCollectActivity.this,VideoDetailsActivity.class);
                intent.putExtra("id",list.get(position).f_id);
                intent.putExtra("videoThumbUrl",list.get(position).coverimg);
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
        loadingDialog.dismiss();
        list.clear();
        if (b.data != null && b.data.size() > 0){
            tvNothing.setVisibility(View.GONE);
            lvVideo.setVisibility(View.VISIBLE);
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
        loadingDialog.dismiss();
    }

    @Override
    public void onLoadFail() {
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
}
