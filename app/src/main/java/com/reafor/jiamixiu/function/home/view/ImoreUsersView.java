package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.UserInfoResponse;

import java.util.List;

public interface ImoreUsersView {

    void getUserInfo(UserInfoResponse.UserInfo info);
    void loadFail(String errmsg);
    void requestComlete();
    void getUserVideos(List<AboutUserVideoResponse.VideoSruct> videos);
}
