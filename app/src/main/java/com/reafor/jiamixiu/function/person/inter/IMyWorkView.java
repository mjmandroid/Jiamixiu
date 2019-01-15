package com.reafor.jiamixiu.function.person.inter;

import com.reafor.jiamixiu.bean.IBaseView;
import com.reafor.jiamixiu.bean.MyWorkBean;

public interface IMyWorkView extends IBaseView {
    void loadWorkData(MyWorkBean bean);
    void loadMoreWorkData(MyWorkBean bean);
}
