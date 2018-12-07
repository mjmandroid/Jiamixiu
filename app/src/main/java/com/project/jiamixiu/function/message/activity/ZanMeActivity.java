package com.project.jiamixiu.function.message.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.FanBean;
import com.project.jiamixiu.function.person.adapter.FanAdapter;
import com.project.jiamixiu.function.person.inter.IFanView;
import com.project.jiamixiu.function.person.presenter.FanPresenter;
import com.project.jiamixiu.utils.UrlConst;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZanMeActivity extends AppCompatActivity implements IFanView {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.lv_zan)
    ListView lvZan;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    ArrayList<FanBean.FanData> fanData = new ArrayList<>();
    FanPresenter presenter;
    FanAdapter fanAdapter;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan_me);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        toolbar.setTitle("赞");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        initPtrFrame();
        fanAdapter = new FanAdapter(this,fanData);
        fanAdapter.setType("2");
        fanAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = v.getTag().toString();
                loadingDialog.show();
                presenter.follow(id);
            }
        });
        lvZan.setAdapter(fanAdapter);
        lvZan.setVisibility(View.GONE);
        tvNothing.setVisibility(View.VISIBLE);
        presenter = new FanPresenter(this);
        loadingDialog.show();
        presenter.loadData(UrlConst.fan_url);
    }

    @Override
    public void loadData(FanBean bean) {
        smartRefreshLayout.finishRefresh();
        if (bean.data != null && bean.data.size() > 0){
            lvZan.setVisibility(View.VISIBLE);
            tvNothing.setVisibility(View.GONE);
            fanData.clear();
            fanData.addAll(bean.data);
            fanAdapter.notifyDataSetChanged();
        }else {
            lvZan.setVisibility(View.GONE);
            tvNothing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFollowSuccess() {
        loadingDialog.dismiss();
        presenter.loadData(UrlConst.fan_url);
    }

    @Override
    public void onShowToast(String s) {

        smartRefreshLayout.finishRefresh();loadingDialog.dismiss();
    }

    @Override
    public void onLoadFail() {
        smartRefreshLayout.finishRefresh();
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

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
                presenter.loadData(UrlConst.fan_url);
            }
        });
        smartRefreshLayout.setDisableContentWhenRefresh(true);
    }
}
