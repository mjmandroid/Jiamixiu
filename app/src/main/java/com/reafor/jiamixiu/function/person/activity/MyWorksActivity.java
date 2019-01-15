package com.reafor.jiamixiu.function.person.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.MyInfoBean;
import com.reafor.jiamixiu.function.person.VideoFragment;
import com.reafor.jiamixiu.utils.DialogUtils;
import com.reafor.jiamixiu.widget.CustomerToolbar;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private VideoPageAdapter pageAdapter;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);
        loadingDialog = new LoadingDialog(this);
        ButterKnife.bind(this);
        toolbar.setTitle("我的作品");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        MyInfoBean bean = (MyInfoBean)getIntent().getSerializableExtra("info");
        if (bean.data != null){
            if (!TextUtils.isEmpty(bean.data.nick)){
                tvUserName.setText(bean.data.nick);
            }else {
                tvUserName.setText("用户未设置");
            }
            if (!TextUtils.isEmpty(bean.data.avator)){
                Picasso.with(this).load(bean.data.avator).error(R.mipmap.icon_default_head).into(ivUserImg);
            }
            /*tvCollectNum.setText(bean.data.fans);
            tvPlayNum.setText(bean.data.fans);
            tvFollowNum.setText(bean.data.fans);*/
        }

        VideoFragment fragment1 = new VideoFragment();
        VideoFragment fragment2 = new VideoFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        pageAdapter = new VideoPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
    }
    @OnClick({R.id.tv_publish,R.id.tv_no_publish,R.id.tv_video,R.id.iv_share})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_publish:

                break;
            case R.id.tv_no_publish:
                viewPager.setCurrentItem(1);
                tvVideo.setTextColor(ContextCompat.getColor(this, R.color.text_color_33));
                tvNoPublish.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
                break;
            case R.id.tv_video:
                viewPager.setCurrentItem(0);
                tvNoPublish.setTextColor(ContextCompat.getColor(this, R.color.text_color_33));
                tvVideo.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
                break;
            case R.id.iv_share:
                showShareDialog();
                break;

        }
    }
    public class VideoPageAdapter  extends FragmentPagerAdapter{

        public VideoPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private void showShareDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_home_video_share_layout,null);
        final Dialog dialog = DialogUtils.BottonDialog(this,view);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialog.dismiss();
            }
        });

        dialog.show();
    }
}
