package com.project.jiamixiu.function.upload.view;

import com.project.jiamixiu.bean.ItemTagsResponse;
import com.project.jiamixiu.bean.TabBeanResponse;

import java.util.List;

public interface ITagView {
    void loadAllTagsSuccess(List<ItemTagsResponse.TagInfo> tagList );
    void loadAllTypeSuccess(List<TabBeanResponse.TabBean> listTab);
    void onError(String mesg);
    void onFinish();
}
