package com.project.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.VideoDetailResponse;
import com.project.jiamixiu.function.home.view.IvideoDetailView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class VideoDetailPrenster {

    private IvideoDetailView ivideoDetailView;

    public  VideoDetailPrenster(IvideoDetailView ivideoDetailView) {
        this.ivideoDetailView = ivideoDetailView;
    }

    public void loadDetail(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.video_detail, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoDetailResponse response = new Gson().fromJson(result, VideoDetailResponse.class);
                ivideoDetailView.loadDetailSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                ivideoDetailView.laodDetailFail(result);
            }

            @Override
            public void onCompleted() {
                ivideoDetailView.loadFinish();
            }
        });
    }
}
