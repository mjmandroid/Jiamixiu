package com.reafor.jiamixiu.function.subscrite.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.SubscribeUsersResponse;
import com.reafor.jiamixiu.function.subscrite.view.ISubscriteView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscritePrenster {

    private ISubscriteView subscriteView;

    public SubscritePrenster(ISubscriteView subscriteView) {
        this.subscriteView = subscriteView;
    }

    public void loadHeadData(){
        Map<String,String> sMap = new HashMap<>();
        HttpManager.sendRequest(UrlConst.video_subscrite_users, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                SubscribeUsersResponse response = new Gson().fromJson(result, SubscribeUsersResponse.class);
                subscriteView.loadHeadSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                subscriteView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                //subscriteView.requestCompleted();
            }
        });
    }

    public void loadVideosDatas(String id,String pageIndex,String pageSize,boolean isAddHead,List<SubscribeUsersResponse.Data> hData){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.about_user_video, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                AboutUserVideoResponse response = new Gson().fromJson(result, AboutUserVideoResponse.class);
                List<AboutUserVideoResponse.VideoSruct> list = new ArrayList<>();
                if (isAddHead){
                    AboutUserVideoResponse.VideoSruct headItem = response.new VideoSruct();
                    headItem.type = 2;
                    headItem.headView = hData;
                    list.add(headItem);
                    AboutUserVideoResponse.VideoSruct titleItem = response.new VideoSruct();
                    titleItem.type = 1;
                    list.add(titleItem);
                    if(response.data != null && response.data.size() > 0){
                        list.addAll(response.data);
                    }
                } else {
                    if(response.data != null && response.data.size() > 0){
                        list.addAll(response.data);
                    }
                }
                subscriteView.loadVideosSuccess(list);
            }

            @Override
            public void onRequestFail(String result, String code) {
                subscriteView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                subscriteView.requestCompleted();
            }
        });
    }
}
