package com.project.jiamixiu.function.home.view;

import com.project.jiamixiu.bean.AboutUserVideoResponse;
import com.project.jiamixiu.bean.UserInfoResponse;

import java.util.List;

public interface ImoreUsersView {

    void getUserInfo(UserInfoResponse.UserInfo info);
    void loadFail(String errmsg);
    void requestComlete();
    void getUserVideos(List<AboutUserVideoResponse.VideoSruct> videos);
}
