package com.reafor.jiamixiu.function.home.view;

import com.reafor.jiamixiu.bean.ItemTagsResponse;
import com.reafor.jiamixiu.bean.TabBeanResponse;

import java.util.List;

public interface IhomeView {
    void getTabsSuccess(List<TabBeanResponse.TabBean> listTab);
    void getTabsFail(String mess);
    void requestFinish();

}
