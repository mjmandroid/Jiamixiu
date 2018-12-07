package com.project.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.VidoeRecordResponse;
import com.project.jiamixiu.function.home.view.IRecordVideoView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class RecordVideoPrenster {
    private IRecordVideoView recordVideoView;

    public RecordVideoPrenster(IRecordVideoView recordVideoView) {
        this.recordVideoView = recordVideoView;
    }

    public void loadVideoList(String pageIndex, String pageSize){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.video_record_list, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                VidoeRecordResponse response = new Gson().fromJson(result, VidoeRecordResponse.class);
                recordVideoView.loadSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                recordVideoView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                recordVideoView.requestCompleted();
            }
        });
    }
}
