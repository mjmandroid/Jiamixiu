package com.reafor.jiamixiu.function.message.presenter;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.MyCommentBean;
import com.reafor.jiamixiu.function.message.inter.ICommentView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.HashMap;

public class CommentPresenter {
    ICommentView commentView;
    public CommentPresenter(ICommentView commentView){
        this.commentView = commentView;
    }
    public void getData(int page ){
        HashMap<String ,String> map = new HashMap<>();
        map.put("pageIndex",page+"");
        map.put("pageSize","20");
        HttpManager.sendRequest(UrlConst.my_comment_url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                MyCommentBean bean = new Gson().fromJson(response,MyCommentBean.class);
                commentView.onLoadCommentData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                commentView.onLoadFail();
                commentView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
    public void getData2(int page ){
        HashMap<String ,String> map = new HashMap<>();
        map.put("pageIndex",page+"");
        map.put("pageSize","20");
        HttpManager.sendRequest(UrlConst.comment_me_about, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                MyCommentBean bean = new Gson().fromJson(response,MyCommentBean.class);
                commentView.onLoadCommentData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                commentView.onLoadFail();
                commentView.onShowToast(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
