package com.reafor.jiamixiu.function.subscrite.view;

import com.reafor.jiamixiu.bean.VideoResponse;

import java.util.ArrayList;
import java.util.List;

public interface IvideoDetailView {
    void laodSuccess(ArrayList<VideoResponse.VideoInfo> infos);
    void loadFail(String errmsg);
    void onLoadFinish();
}
