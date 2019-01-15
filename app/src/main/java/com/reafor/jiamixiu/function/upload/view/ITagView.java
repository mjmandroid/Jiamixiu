package com.reafor.jiamixiu.function.upload.view;

import com.reafor.jiamixiu.bean.ItemTagsResponse;
import com.reafor.jiamixiu.bean.TabBeanResponse;

import java.util.List;

public interface ITagView {
    void loadAllTagsSuccess(List<ItemTagsResponse.TagInfo> tagList );
    void loadAllTypeSuccess(List<TabBeanResponse.TabBean> listTab);
    void onError(String mesg);
    void onFinish();
}
