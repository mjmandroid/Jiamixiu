package com.project.jiamixiu.function.message.inter;

import com.project.jiamixiu.bean.IBaseView;
import com.project.jiamixiu.bean.MyCommentBean;

public interface ICommentView extends IBaseView {
    void onLoadCommentData(MyCommentBean bean);
}
