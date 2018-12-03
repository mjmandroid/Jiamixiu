package com.project.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.VideoRecommendResponse;
import com.project.jiamixiu.function.home.view.IRecommentView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class RecommentPrenster {
    private IRecommentView recommentView;
    public RecommentPrenster(IRecommentView recommentView){
        this.recommentView = recommentView;
    }

    public void loadRecommentList(String id,String pageIndex,String pageSize){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.video_recommend, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoRecommendResponse response = new Gson().fromJson(result, VideoRecommendResponse.class);
                recommentView.loadRecommentSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                recommentView.loadRecommentFail(result);
            }

            @Override
            public void onCompleted() {
                recommentView.requestComplete();
            }
        });
    }
}
