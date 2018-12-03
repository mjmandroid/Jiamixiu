package com.project.jiamixiu.function.person.activity;

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
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.utils.UrlConst;
import com.project.jiamixiu.widget.CustomerToolbar;

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
    ArrayList<FanBean.FanData> fanData = new ArrayList<>();
    FanPresenter presenter;
    FanAdapter fanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fan);
        ButterKnife.bind(this);
        toolbar.setTitle("粉丝");
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
                presenter.follow(id);
            }
        });
        lvFollow.setAdapter(fanAdapter);
        lvFollow.setVisibility(View.GONE);
        tvNothing.setVisibility(View.VISIBLE);
        presenter = new FanPresenter(this);
        presenter.loadData(UrlConst.fan_url);
    }

    @Override
    public void loadData(FanBean bean) {
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
        presenter.loadData(UrlConst.fan_url);
    }

    @Override
    public void onShowToast(String s) {
        UIUtils.showToast(this,s);
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onCompleted() {

    }
}
