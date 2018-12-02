package com.project.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.FanBean;
import com.project.jiamixiu.bean.MyWorkBean;
import com.project.jiamixiu.function.person.inter.IFanView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class FanPresenter {
    IFanView fanView;
    public FanPresenter(IFanView fanView){
        this.fanView = fanView;
    }
    public void loadData(String url){
        HashMap<String ,String> map = new HashMap<>();
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                FanBean bean = new Gson().fromJson(response,FanBean.class);
                fanView.loadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                fanView.onShowToast(result);
                fanView.onLoadFail();
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
