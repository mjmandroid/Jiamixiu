package com.project.jiamixiu.function.subscrite.view;

import com.project.jiamixiu.bean.AboutUserVideoResponse;
import com.project.jiamixiu.bean.SubscribeUsersResponse;

import java.util.List;

public interface ISubscriteView {

    void loadHeadSuccess(List<SubscribeUsersResponse.Data> dataList);
    void loadFail(String errmsg);
    void requestCompleted();

    void loadVideosSuccess(List<AboutUserVideoResponse.VideoSruct> sructList);
}
