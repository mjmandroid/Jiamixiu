package com.project.jiamixiu.function.home.prenster;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.ItemBannerResponse;
import com.project.jiamixiu.bean.ItemTagsResponse;
import com.project.jiamixiu.bean.VideoResponse;
import com.project.jiamixiu.function.home.view.IitemFragmentView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class ItemFragmentPresenter {

    private IitemFragmentView itemView;
    public ItemFragmentPresenter(IitemFragmentView itemView){
        this.itemView = itemView;
    }


    public void preloadData(String keyword,String tag,String pageIndex,String pageSize){

        Map<String, String> sMap = new HashMap<>();
        sMap.put("keyword",keyword);
        sMap.put("tag",tag);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.get_vidoes, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String content) {
                VideoResponse response = new Gson().fromJson(content, VideoResponse.class);
                itemView.laodSuccess(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                itemView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                itemView.laodFinish();
            }
        });
    }

    public void loadTags(String id){
        Map<String, String> sMap = new HashMap<>();
        if (!TextUtils.isEmpty(id)){
            sMap.put("categoryid",id);
        }
        HttpManager.sendRequest(UrlConst.get_item_tags, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String res) {
                ItemTagsResponse response = new Gson().fromJson(res, ItemTagsResponse.class);
                itemView.getItemTags(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                itemView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                itemView.laodFinish();
            }
        });

    }

    public void loadBannerList(String id){
        Map<String, String> sMap = new HashMap<>();
        if (!TextUtils.isEmpty(id)){
            sMap.put("categoryId",id);
        }
        HttpManager.sendRequest(UrlConst.get_item_banner, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                ItemBannerResponse response = new Gson().fromJson(result, ItemBannerResponse.class);
                itemView.getItemBanner(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                itemView.getItemBanner(null);
            }

            @Override
            public void onCompleted() {
                itemView.laodFinish();
            }
        });
    }
}
