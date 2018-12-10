package com.project.jiamixiu.function.upload;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.bean.ItemTagsResponse;
import com.project.jiamixiu.bean.TabBeanResponse;
import com.project.jiamixiu.function.upload.prenster.TagPrenster;
import com.project.jiamixiu.function.upload.view.ITagView;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.FlowLayout;
import com.project.jiamixiu.widget.LoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectTagTypeActivity extends BaseActivity implements ITagView {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    private String title;
    private TagPrenster prenster;
    private LoadingDialog loadingDialog;

    @Override
    protected void initData() {
        super.initData();
        title = getIntent().getStringExtra("title");
        loadingDialog = new LoadingDialog(this);
        prenster = new TagPrenster(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_upload_select_tag_type);
        ButterKnife.bind(this);
        toolbar.setTitle(title);
        toolbar.setToolbarLisenter(()->finish());
        loadingDialog.show();
        if ("游戏类型".equals(title)){
            prenster.loadType();
        } else {
            prenster.loadTag();
        }
    }

    @Override
    public void loadAllTagsSuccess(List<ItemTagsResponse.TagInfo> tagList) {
        if (tagList != null && tagList.size() > 0){
            for (int i = 0; i < tagList.size(); i++) {
                ItemTagsResponse.TagInfo tagInfo = tagList.get(i);
                TextView button = new TextView(this);
                button.setText(tagInfo.name);
                button.setTextColor(Color.WHITE);
                button.setTextSize(14);
                button.setGravity(Gravity.CENTER);
                button.setBackgroundResource(R.drawable.selector_upload_tag);
                FlowLayout.MarginLayoutParams lp = new FlowLayout.MarginLayoutParams(FlowLayout.MarginLayoutParams.WRAP_CONTENT,
                        FlowLayout.MarginLayoutParams.WRAP_CONTENT);
                lp.leftMargin = 25;
                lp.topMargin = 25;
                button.setPadding(30,20,30,20);
                button.setLayoutParams(lp);
                flowLayout.addView(button);
                button.setOnClickListener(view->{
                    Intent data = new Intent();
                    data.putExtra("name",tagInfo.name);
                    setResult(2, data);
                    finish();
                });
            }
        }
    }

    @Override
    public void loadAllTypeSuccess(List<TabBeanResponse.TabBean> listTab) {
        if (listTab != null && listTab.size() > 0){
            for (int i = 0; i < listTab.size(); i++) {
                TabBeanResponse.TabBean tagInfo = listTab.get(i);
                TextView button = new TextView(this);
                button.setText(tagInfo.name);
                button.setTextColor(Color.WHITE);
                button.setTextSize(14);
                button.setGravity(Gravity.CENTER);
                button.setBackgroundResource(R.drawable.selector_upload_tag);
                FlowLayout.MarginLayoutParams lp = new FlowLayout.MarginLayoutParams(FlowLayout.MarginLayoutParams.WRAP_CONTENT,
                        FlowLayout.MarginLayoutParams.WRAP_CONTENT);
                lp.leftMargin = 25;
                lp.topMargin = 25;
                button.setPadding(30,20,30,20);
                flowLayout.addView(button,lp);
                button.setOnClickListener(view->{
                    Intent data = new Intent();
                    data.putExtra("id",tagInfo.fid);
                    setResult(1, data);
                    finish();
                });
            }
        }
    }

    @Override
    public void onError(String mesg) {
        ToastUtil.showTosat(this,mesg);
    }

    @Override
    public void onFinish() {
        loadingDialog.dismiss();
    }
}
