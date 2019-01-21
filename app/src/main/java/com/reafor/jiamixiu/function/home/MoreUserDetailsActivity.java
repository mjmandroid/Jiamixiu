package com.reafor.jiamixiu.function.home;

import com.reafor.jiamixiu.base.BaseVideoDetailsActivity;
import com.reafor.jiamixiu.utils.UrlConst;

public class MoreUserDetailsActivity extends BaseVideoDetailsActivity {
    @Override
    public String getVideoListUrl() {
        return UrlConst.about_user_video;
    }
}
