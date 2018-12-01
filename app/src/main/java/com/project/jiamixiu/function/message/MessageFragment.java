package com.project.jiamixiu.function.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.function.login.LoginActivity;
import com.project.jiamixiu.function.person.activity.MyFanActivity;
import com.project.jiamixiu.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.project.jiamixiu.utils.Const.LOGIN_SUCCESS_CODE;

public class MessageFragment extends Fragment implements View.OnClickListener {
    View view;
    @BindView(R.id.ll_fan)
    LinearLayout llFan;
    @BindView(R.id.ll_zan)
    LinearLayout llZan;
    @BindView(R.id.tv_at)
    TextView tvAt;
    @BindView(R.id.ll_at)
    LinearLayout llAt;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_msg, null);
        }
        unbinder = ButterKnife.bind(this, view);
        tvAt.setText("@我的");
        return view;
    }
    @OnClick({R.id.ll_fan,R.id.ll_zan,R.id.ll_at,R.id.ll_comment})
    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
            startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_SUCCESS_CODE);
            return;
        }
        switch (v.getId()) {
            case R.id.ll_zan:
                break;
            case R.id.ll_fan:
                startActivity(new Intent(getContext(), MyFanActivity.class));
                break;
            case R.id.ll_at:

                break;
            case R.id.ll_comment:
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
