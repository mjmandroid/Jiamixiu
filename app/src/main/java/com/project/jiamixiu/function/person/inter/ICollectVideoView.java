package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.CollectVideoBean;
import com.project.jiamixiu.bean.IBaseView;

public interface ICollectVideoView extends IBaseView{
    void onLoadData(CollectVideoBean b);
    void onLoadMoreData(CollectVideoBean b);
    void onDeleteSuccess();
    void onDeleteFail();
}
