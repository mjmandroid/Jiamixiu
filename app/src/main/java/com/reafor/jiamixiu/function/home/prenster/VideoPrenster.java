package com.reafor.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.VideoCommentResponse;
import com.reafor.jiamixiu.function.home.view.IvideoView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoPrenster {
    private IvideoView view;

    public VideoPrenster(IvideoView view) {
        this.view = view;
    }

    public void getCommentList(String id, String pageIndex, String pageSize){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.video_comment_list, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VideoCommentResponse response = new Gson().fromJson(result, VideoCommentResponse.class);
                List<VideoCommentResponse.Data> dataList = new ArrayList<>();
                if (response.data != null && response.data.size() > 0){
                    dataList.addAll(response.data);
                    view.getCommentList(dataList);
                } else {
                    view.getCommentList(dataList);
                }

            }

            @Override
            public void onRequestFail(String result, String code) {
                view.loadFail(result);
            }

            @Override
            public void onCompleted() {
                view.loadFinish();
            }
        });
    }

    public void commit(String v_id,String comment,String sourthId){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",v_id);
        sMap.put("comment",comment);
        sMap.put("sourthId",sourthId);
        HttpManager.sendRequest(UrlConst.video_comment, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                view.commentSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                view.loadFail(result);
            }

            @Override
            public void onCompleted() {
                view.loadFinish();
            }
        });
    }

    public void favorite(String id){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        HttpManager.sendRequest(UrlConst.vido_praise, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                view.praiseSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                view.loadFail(result);
            }

            @Override
            public void onCompleted() {
                view.loadFinish();
            }
        });
    }
}
