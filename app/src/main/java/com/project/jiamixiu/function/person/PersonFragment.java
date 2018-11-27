package com.project.jiamixiu.function.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonFragment extends BaseFragment implements View.OnClickListener {
    View view;
    @BindView(R.id.ll_no_login)
    LinearLayout llNoLogin;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.ll_qianbao_my)
    LinearLayout llQianbaoMy;
    @BindView(R.id.ll_qianbao_jiami)
    LinearLayout llQianbaoJiami;
    @BindView(R.id.ll_my_income)
    LinearLayout llMyIncome;
    @BindView(R.id.ll_video_collect)
    LinearLayout llVideoCollect;
    @BindView(R.id.ll_my_work)
    LinearLayout llMyWork;
    @BindView(R.id.ll_my_fan)
    LinearLayout llMyFan;
    @BindView(R.id.ll_my_concern)
    LinearLayout llMyConcern;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_person, null);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_login,R.id.ll_no_login,R.id.tv_login,R.id.tv_register,R.id.ll_qianbao_my,R.id.ll_qianbao_jiami,R.id.ll_my_income,
            R.id.ll_video_collect,R.id.ll_my_work,R.id.ll_my_fan,R.id.ll_my_concern})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_no_login:
                break;
            case R.id.ll_login:
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_register:
                break;
            case R.id.ll_qianbao_my:
                break;
            case R.id.ll_qianbao_jiami:
                break;
            case R.id.ll_my_income:
                break;
            case R.id.ll_video_collect:
                break;
            case R.id.ll_my_work:
                break;
            case R.id.ll_my_fan:
                break;
            case R.id.ll_my_concern:
                break;
        }
    }
}
