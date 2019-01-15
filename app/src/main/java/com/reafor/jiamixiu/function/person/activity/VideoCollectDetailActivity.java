package com.reafor.jiamixiu.function.person.activity;

import com.reafor.jiamixiu.base.BaseVideoDetailsActivity;
import com.reafor.jiamixiu.utils.UrlConst;

public class VideoCollectDetailActivity extends BaseVideoDetailsActivity {
    @Override
    public String getVideoListUrl() {
        return UrlConst.collect_url;
    }
}
