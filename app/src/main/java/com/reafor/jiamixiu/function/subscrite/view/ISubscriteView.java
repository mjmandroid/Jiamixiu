package com.reafor.jiamixiu.function.subscrite.view;

import com.reafor.jiamixiu.bean.AboutUserVideoResponse;
import com.reafor.jiamixiu.bean.SubscribeUsersResponse;

import java.util.List;

public interface ISubscriteView {

    void loadHeadSuccess(List<SubscribeUsersResponse.Data> dataList);
    void loadFail(String errmsg);
    void requestCompleted();

    void loadVideosSuccess(List<AboutUserVideoResponse.VideoSruct> sructList);
}
