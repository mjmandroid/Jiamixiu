package com.reafor.jiamixiu.base.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.base.view.IbaseVideoView;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class BaseVideoPrenster {
    private IbaseVideoView videoView;

    public BaseVideoPrenster(IbaseVideoView videoView) {
        this.videoView = videoView;
    }

    public void onLoadSubscrite(String id,String pageIndex,String pageSize,String url){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(url, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoResponse response = new Gson().fromJson(result, VideoResponse.class);
                videoView.laodSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                videoView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                videoView.onLoadFinish();
            }
        });
    }
}
