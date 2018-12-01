package com.project.jiamixiu.function.person.inter;

import com.project.jiamixiu.bean.IBaseView;
import com.project.jiamixiu.bean.MyWorkBean;

public interface IMyWorkView extends IBaseView {
    void loadWorkData(MyWorkBean bean);
    void loadMoreWorkData(MyWorkBean bean);
}
