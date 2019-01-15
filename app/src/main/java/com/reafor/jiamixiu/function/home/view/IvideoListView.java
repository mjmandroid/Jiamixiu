package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.VideoResponse;

import java.util.List;

public interface IvideoListView {
    void laodSuccess(List<VideoResponse.VideoInfo> infos);
    void loadFail(String error_msg);
    void laodFinish();
}
