package com.project.jiamixiu.function.person.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWorksActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.tv_play_num)
    TextView tvPlayNum;
    @BindView(R.id.tv_praise_num)
    TextView tvPraiseNum;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectNum;
    @BindView(R.id.tv_follow_num)
    TextView tvFollowNum;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_no_publish)
    TextView tvNoPublish;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);
        ButterKnife.bind(this);
        toolbar.setTitle("我的作品");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }
    @OnClick({R.id.tv_publish,R.id.tv_no_publish,R.id.tv_video})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_publish:
                break;
            case R.id.tv_no_publish:
                break;
            case R.id.tv_video:
                break;

        }
    }
}
