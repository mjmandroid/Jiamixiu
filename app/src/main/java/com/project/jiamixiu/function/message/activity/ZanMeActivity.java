package com.project.jiamixiu.function.message.activity;

import android.os.Bundle;
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
        if (bean.data != null && bean.data.size() > 0){
            lvZan.setVisibility(View.VISIBLE);
            tvNothing.setVisibility(View.GONE);
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
        loadingDialog.dismiss();
    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

    }
}
