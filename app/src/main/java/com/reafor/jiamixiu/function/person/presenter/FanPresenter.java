package com.reafor.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.FanBean;
import com.reafor.jiamixiu.bean.MyWorkBean;
import com.reafor.jiamixiu.function.person.inter.IFanView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;

import static com.reafor.jiamixiu.utils.UrlConst.follow_user_url;

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
    public void follow(String id){
        HashMap<String ,String> map = new HashMap<>();
        map.put("id",id);
        map.put("note","");
        HttpManager.sendRequest(follow_user_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                fanView.onFollowSuccess();
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
