package com.project.jiamixiu.function.home.view;

import com.project.jiamixiu.bean.ItemTagsResponse;
import com.project.jiamixiu.bean.TabBeanResponse;

import java.util.List;

public interface IhomeView {
    void getTabsSuccess(List<TabBeanResponse.TabBean> listTab);
    void getTabsFail(String mess);
    void requestFinish();

}
