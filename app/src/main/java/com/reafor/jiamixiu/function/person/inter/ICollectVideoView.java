package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.CollectVideoBean;
import com.reafor.jiamixiu.bean.IBaseView;

public interface ICollectVideoView extends IBaseView{
    void onLoadData(CollectVideoBean b);
    void onLoadMoreData(CollectVideoBean b);
    void onDeleteSuccess();
    void onDeleteFail();
}
