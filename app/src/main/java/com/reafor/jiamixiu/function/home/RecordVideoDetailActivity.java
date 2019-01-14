package com.reafor.jiamixiu.function.home;

import com.reafor.jiamixiu.base.BaseVideoDetailsActivity;
import com.reafor.jiamixiu.utils.UrlConst;

public class RecordVideoDetailActivity extends BaseVideoDetailsActivity {
    @Override
    public String getVideoListUrl() {
        return UrlConst.video_record_list;
    }
}
