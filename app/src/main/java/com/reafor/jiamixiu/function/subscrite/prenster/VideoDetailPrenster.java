package com.reafor.jiamixiu.function.subscrite.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.SubscribeUsersResponse;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.subscrite.view.ISubscriteView;
import com.reafor.jiamixiu.function.subscrite.view.IvideoDetailView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoDetailPrenster {
    private IvideoDetailView view;

    public VideoDetailPrenster(IvideoDetailView view) {
        this.view = view;
    }

    public void onLoadSubscrite(String id,String pageIndex,String pageSize){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.about_user_video, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoResponse response = new Gson().fromJson(result, VideoResponse.class);
                view.laodSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                view.loadFail(result);
            }

            @Override
            public void onCompleted() {
                view.onLoadFinish();
            }
        });
    }
}
