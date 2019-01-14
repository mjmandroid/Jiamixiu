package com.reafor.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.VideoResponse;
import com.reafor.jiamixiu.function.home.view.IvideoListView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class VideoListPrenster {

    private IvideoListView view;

    public VideoListPrenster(IvideoListView view) {
        this.view = view;
    }

    public void acquireList(String keyword,String tag,String categoryid,String pageIndex,String pageSize){
        Map<String, String> sMap = new HashMap<>();
        sMap.put("keyword",keyword);
        sMap.put("tag",tag);
        sMap.put("categoryid",categoryid);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.get_vidoes, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String content) {
                VideoResponse response = new Gson().fromJson(content, VideoResponse.class);
                view.laodSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                view.loadFail(result);
            }

            @Override
            public void onCompleted() {
                view.laodFinish();
            }
        });
    }
}
