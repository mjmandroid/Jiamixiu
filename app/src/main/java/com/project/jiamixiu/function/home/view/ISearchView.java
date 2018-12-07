package com.project.jiamixiu.function.home.view;

import com.project.jiamixiu.bean.VideoResponse;

import java.util.List;

public interface ISearchView {
    void loadSuccess(List<VideoResponse.VideoInfo> infos);
    void loadFail(String errmsg);
    void requestFinish();
}
