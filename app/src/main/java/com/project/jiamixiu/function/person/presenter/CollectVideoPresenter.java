package com.project.jiamixiu.function.person.presenter;

import com.google.gson.Gson;
import com.project.jiamixiu.bean.CollectVideoBean;
import com.project.jiamixiu.bean.MyInfoBean;
import com.project.jiamixiu.function.person.inter.ICollectVideoView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class CollectVideoPresenter {
    ICollectVideoView iCollectVideoView;
    public CollectVideoPresenter(ICollectVideoView iCollectVideoView){
        this.iCollectVideoView = iCollectVideoView;
    }
    public void loadData(final int page){
        HashMap<String ,String> map = new HashMap<>();
        map.put("pageIndex",page+"");
        map.put("pageSize","20");
        HttpManager.sendRequest(UrlConst.collect_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                CollectVideoBean bean = new Gson().fromJson(response,CollectVideoBean.class);
                if (page > 0){
                    iCollectVideoView.onLoadMoreData(bean);
                }else {
                    iCollectVideoView.onLoadData(bean);
                }
            }

            @Override
            public void onRequestFail(String result, String code) {
                iCollectVideoView.onLoadFail();
                iCollectVideoView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void delete(String id){
        HashMap<String ,String> map = new HashMap<>();
        map.put("id","id");
        HttpManager.sendRequest(UrlConst.del_collect_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iCollectVideoView.onDeleteSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iCollectVideoView.onDeleteFail();
                iCollectVideoView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
