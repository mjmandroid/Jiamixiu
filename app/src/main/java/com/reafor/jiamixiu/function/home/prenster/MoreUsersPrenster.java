package com.reafor.jiamixiu.function.home.prenster;

import com.google.gson.Gson;
import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.UserInfoResponse;
import com.reafor.jiamixiu.function.home.view.ImoreUsersView;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.UrlConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreUsersPrenster {
    private ImoreUsersView usersView;

    public MoreUsersPrenster(ImoreUsersView usersView) {
        this.usersView = usersView;
    }

    public void getUserInfo(){
        Map<String,String> sMap = new HashMap<>();
        String userId = SharedPreferencesUtil.getUserId();
        sMap.put("userid", userId);
        HttpManager.sendRequest(UrlConst.video_userinfo, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                UserInfoResponse response = new Gson().fromJson(result, UserInfoResponse.class);
                usersView.getUserInfo(response.data);
            }

            @Override
            public void onRequestFail(String result, String code) {
                usersView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                usersView.requestComlete();
            }
        });
    }

    public void getAboutUserVideos(UserInfoResponse.UserInfo info,String id,String pageIndex,
                                   String pageSize,boolean isAddHead){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("id",id);
        sMap.put("pageIndex",pageIndex);
        sMap.put("pageSize",pageSize);
        HttpManager.sendRequest(UrlConst.about_user_video, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                AboutUserVideoResponse response = new Gson().fromJson(result, AboutUserVideoResponse.class);
                List<AboutUserVideoResponse.VideoSruct> dataList = new ArrayList<>();
                if (response.data != null){
                    if (isAddHead){
                        AboutUserVideoResponse.VideoSruct head = response.new VideoSruct();
                        head.type = 1;
                        head.userInfo = info;
                        dataList.add(head);
                        dataList.addAll(response.data);
                    } else {
                        dataList.addAll(response.data);
                    }
                }
                usersView.getUserVideos(dataList);
            }

            @Override
            public void onRequestFail(String result, String code) {
                usersView.loadFail(result);
            }

            @Override
            public void onCompleted() {
                usersView.requestComlete();
            }
        });

    }
}
