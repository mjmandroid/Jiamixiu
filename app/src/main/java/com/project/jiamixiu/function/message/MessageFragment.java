package com.project.jiamixiu.function.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.MyCommentBean;
import com.project.jiamixiu.function.login.LoginActivity;
import com.project.jiamixiu.function.message.activity.AtMeActivity;
import com.project.jiamixiu.function.message.activity.MycommentActivity;
import com.project.jiamixiu.function.message.activity.ZanMeActivity;
import com.project.jiamixiu.function.person.activity.MyFanActivity;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.utils.UrlConst;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.lv_msg)
    ListView lv_msg;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    MsgAdapter msgAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_msg, null);
        }
        unbinder = ButterKnife.bind(this, view);
        tvAt.setText("@我的");
        initPtrFrame();
        msgAdapter = new MsgAdapter();
        lv_msg.setAdapter(msgAdapter);
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
                startActivity(new Intent(getContext(), ZanMeActivity.class));
                break;
            case R.id.ll_fan:
                startActivity(new Intent(getContext(), MyFanActivity.class));
                break;
            case R.id.ll_at:
                startActivity(new Intent(getContext(), AtMeActivity.class));
                break;
            case R.id.ll_comment:
                startActivity(new Intent(getContext(), MycommentActivity.class));
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //下拉刷新
    private void initPtrFrame() {
        //设置全局的Header构建器

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setDisableContentWhenRefresh(true);
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setTextSizeTitle(14);
                layout.setPrimaryColorsId(R.color.sf_header_theme, R.color.sf_header_tv);//全局设置主题颜色
                classicsHeader.setDrawableArrowSize(15);
//                FalsifyHeader falsifyHeader = new FalsifyHeader(context);
                return classicsHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
        smartRefreshLayout.setDisableContentWhenRefresh(true);
    }

    public class MsgAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_my_comment, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText("");
            holder.tvContent.setText("");
            holder.tvTime.setText("");
            if (!TextUtils.isEmpty("")){
                Picasso.with(getContext()).load(UIUtils.getImageUrl("")).into(holder.ivUserImg);
            }
            return convertView;
        }

        public class ViewHolder {
            @BindView(R.id.iv_user_img)
            RoundedImageView ivUserImg;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_content)
            TextView tvContent;
            @BindView(R.id.tv_time)
            TextView tvTime;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
