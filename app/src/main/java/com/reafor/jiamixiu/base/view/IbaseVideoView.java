package com.reafor.jiamixiu.base.view;

import com.reafor.jiamixiu.bean.VideoResponse;

import java.util.ArrayList;

public interface IbaseVideoView {
    void laodSuccess(ArrayList<VideoResponse.VideoInfo> infos);
    void loadFail(String errmsg);
    void onLoadFinish();
}
