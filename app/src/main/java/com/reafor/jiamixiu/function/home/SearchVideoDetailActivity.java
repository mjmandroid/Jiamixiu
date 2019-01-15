package com.reafor.jiamixiu.function.home;

import com.reafor.jiamixiu.base.BaseVideoDetailsActivity;
import com.reafor.jiamixiu.utils.UrlConst;

public class SearchVideoDetailActivity extends BaseVideoDetailsActivity {
    @Override
    public String getVideoListUrl() {
        return UrlConst.get_vidoes;
    }
}
