package com.project.jiamixiu.function.person;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseFragment;
import com.project.jiamixiu.bean.MyInfoBean;
import com.project.jiamixiu.function.login.LoginActivity;
import com.project.jiamixiu.function.login.RegisterActivity;
import com.project.jiamixiu.function.person.activity.MyFanActivity;
import com.project.jiamixiu.function.person.activity.MyFollowActivity;
import com.project.jiamixiu.function.person.activity.MyWorksActivity;
import com.project.jiamixiu.function.person.activity.PersonInfoActivity;
import com.project.jiamixiu.function.person.activity.VideoCollectActivity;
import com.project.jiamixiu.function.person.inter.IPersonView;
import com.project.jiamixiu.function.person.presenter.PersonPresenter;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.project.jiamixiu.utils.Const.LOGIN_SUCCESS_CODE;

public class PersonFragment extends BaseFragment implements View.OnClickListener,IPersonView {
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
    PersonPresenter personPresenter;
    MyInfoBean infoBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        personPresenter = new PersonPresenter(this);
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_person, null);
        }
        unbinder = ButterKnife.bind(this, view);
        onRefresh();
        return view;
    }
    public void onRefresh(){
        if ( unbinder == null){
            return;
        }
        if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llNoLogin.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        }else {
            llNoLogin.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
            personPresenter.loadPersonInfo();
        }
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
                startActivityForResult(new Intent(getContext(),PersonInfoActivity.class),11);
                break;
            case R.id.tv_login:
                startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                break;
            case R.id.tv_register:
                Intent intent = new Intent(getContext(),RegisterActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.ll_qianbao_my:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }
                break;
            case R.id.ll_qianbao_jiami:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }
                break;
            case R.id.ll_my_income:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }

                break;
            case R.id.ll_video_collect:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }
                startActivity(new Intent(getContext(),VideoCollectActivity.class));
                break;
            case R.id.ll_my_work:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }
                Intent wi = new Intent(getContext(),MyWorksActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("info",infoBean);*/
                wi.putExtra("info",infoBean);
                startActivity(wi);
                break;
            case R.id.ll_my_fan:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }
                startActivity(new Intent(getContext(),MyFanActivity.class));
                break;
            case R.id.ll_my_concern:
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),LOGIN_SUCCESS_CODE);
                    return;
                }
                startActivity(new Intent(getContext(),MyFollowActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK){
            if (requestCode == LOGIN_SUCCESS_CODE){
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    llNoLogin.setVisibility(View.VISIBLE);
                    llLogin.setVisibility(View.GONE);
                }else {
                    llNoLogin.setVisibility(View.GONE);
                    llLogin.setVisibility(View.VISIBLE);
                    personPresenter.loadPersonInfo();
                }
            }
            if (requestCode == 11){
                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    llNoLogin.setVisibility(View.VISIBLE);
                    llLogin.setVisibility(View.GONE);
                }else {
                    personPresenter.loadPersonInfo();
                }

            }
        }
    }

    @Override
    public void getPersonInfoSuccessed(MyInfoBean bean) {
        this.infoBean = bean;
        llLogin.setVisibility(View.VISIBLE);
        llNoLogin.setVisibility(View.GONE);
        if (bean.data != null){
            if (!TextUtils.isEmpty(bean.data.nick)){
                tvUserName.setText(bean.data.nick);
            }else {
                tvUserName.setText("用户未设置");
            }
            if (!TextUtils.isEmpty(bean.data.avator)){
                Picasso.with(getContext()).load(bean.data.avator).error(R.mipmap.icon_default_head).into(ivUserImg);
            }
            SharedPreferencesUtil.saveUserId(bean.data.f_id);
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onShowToast(String s) {

    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onCompleted() {

    }
}
