package com.reafor.jiamixiu.function.upload.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.ItemTagsResponse;
import com.reafor.jiamixiu.bean.TabBeanResponse;
import com.reafor.jiamixiu.function.upload.view.ITagView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

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
