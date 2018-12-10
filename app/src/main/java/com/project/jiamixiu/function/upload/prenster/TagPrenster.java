package com.project.jiamixiu.function.upload.prenster;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.ItemTagsResponse;
import com.project.jiamixiu.bean.TabBeanResponse;
import com.project.jiamixiu.function.upload.view.ITagView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class TagPrenster {

    private ITagView tagView;

    public TagPrenster(ITagView tagView) {
        this.tagView = tagView;
    }

    public void loadTag(){
        HttpManager.sendRequest(UrlConst.get_item_tags, new HashMap<>(), new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                ItemTagsResponse response = new Gson().fromJson(result, ItemTagsResponse.class);
                tagView.loadAllTagsSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                tagView.onError(result);
            }

            @Override
            public void onCompleted() {
                tagView.onFinish();
            }
        });
    }

    public void loadType(){
        HttpManager.sendRequest(UrlConst.get_tabs, new HashMap<>(), new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                TabBeanResponse response = new Gson().fromJson(result, TabBeanResponse.class);
                tagView.loadAllTypeSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                tagView.onError(result);
            }

            @Override
            public void onCompleted() {
                tagView.onFinish();
            }
        });
    }
}
