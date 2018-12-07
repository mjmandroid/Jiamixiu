package com.project.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.VideoResponse;
import com.project.jiamixiu.function.home.view.ISearchView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class SearchPrenster {
    private ISearchView searchView;

    public SearchPrenster(ISearchView searchView) {
        this.searchView = searchView;
    }

    public void loadData(String keyword, String tag, String pageIndex, String pageSize){

        Map<String, String> sMap = new HashMap<>();
        sMap.put("keyword",keyword);
        sMap.put("tag",tag);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.get_vidoes, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String content) {
                VideoResponse response = new Gson().fromJson(content, VideoResponse.class);
                searchView.loadSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                searchView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                searchView.requestFinish();
            }
        });
    }
}
