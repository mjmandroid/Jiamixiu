package com.reafor.jiamixiu.function.person.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.FanBean;
import com.reafor.jiamixiu.function.person.adapter.FanAdapter;
import com.reafor.jiamixiu.function.person.inter.IFanView;
import com.reafor.jiamixiu.function.person.presenter.FanPresenter;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.utils.UrlConst;
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

public class MyFanActivity extends AppCompatActivity implements IFanView{

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.lv_follow)
    ListView lvFollow;
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
        setContentView(R.layout.activity_my_fan);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        toolbar.setTitle("粉丝");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        fanAdapter = new FanAdapter(this,fanData);
        fanAdapter.setType("2");
        fanAdapter.setCallBack(new FanAdapter.FollowCallBack() {
            @Override
            public void onFollow(String id) {
                loadingDialog.show();
                presenter.follow(id);
            }
        });
        initPtrFrame();
        lvFollow.setAdapter(fanAdapter);
        lvFollow.setVisibility(View.GONE);
        tvNothing.setVisibility(View.VISIBLE);
        presenter = new FanPresenter(this);
        loadingDialog.show();
        presenter.loadData(UrlConst.fan_url);
    }

    @Override
    public void loadData(FanBean bean) {
        loadingDialog.dismiss();
        smartRefreshLayout.finishRefresh();
        if (bean.data != null && bean.data.size() > 0){
            lvFollow.setVisibility(View.VISIBLE);
            tvNothing.setVisibility(View.GONE);
            fanData.clear();
            fanData.addAll(bean.data);
            fanAdapter.notifyDataSetChanged();
        }else {
            lvFollow.setVisibility(View.GONE);
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
        loadingDialog.dismiss();
        smartRefreshLayout.finishRefresh();
        UIUtils.showToast(this,s);
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
