package com.project.jiamixiu.function.person.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.FanBean;
import com.project.jiamixiu.function.person.adapter.FanAdapter;
import com.project.jiamixiu.function.person.inter.IFanView;
import com.project.jiamixiu.function.person.presenter.FanPresenter;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.utils.UrlConst;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFollowActivity extends AppCompatActivity implements IFanView {
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.lv_follow)
    ListView lvFollow;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.container)
    RelativeLayout container;
    ArrayList<FanBean.FanData> fanData = new ArrayList<>();
    FanPresenter presenter;
    FanAdapter fanAdapter;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        loadingDialog = new LoadingDialog(this);
        ButterKnife.bind(this);
        toolbar.setTitle("我的关注");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        fanAdapter = new FanAdapter(this,fanData);
        fanAdapter.setType("1");
        lvFollow.setAdapter(fanAdapter);
        lvFollow.setVisibility(View.GONE);
        tvNothing.setVisibility(View.VISIBLE);
        presenter = new FanPresenter(this);
        loadingDialog.show();
        presenter.loadData(UrlConst.follow_url);
    }

    @Override
    public void loadData(FanBean bean) {
        loadingDialog.dismiss();
        if (bean.data != null && bean.data.size() > 0){
            lvFollow.setVisibility(View.VISIBLE);
            tvNothing.setVisibility(View.GONE);
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
        UIUtils.showToast(this,s);
    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

    }
}
