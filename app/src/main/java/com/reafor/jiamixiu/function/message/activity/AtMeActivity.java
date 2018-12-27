package com.reafor.jiamixiu.function.message.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.bean.MyCommentBean;
import com.reafor.jiamixiu.function.message.inter.ICommentView;
import com.reafor.jiamixiu.function.message.presenter.CommentPresenter;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.CustomerToolbar;
import com.reafor.jiamixiu.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AtMeActivity extends AppCompatActivity implements ICommentView {
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.lv_at)
    ListView lvAt;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private ArrayList<MyCommentBean.MyCommentData> list = new ArrayList<>();
    private AtCommentAdapter adapter;
    private CommentPresenter presenter;
    private int page;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_me);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        toolbar.setTitle("@我的");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        initPtrFrame();
        adapter = new AtCommentAdapter();
        lvAt.setAdapter(adapter);
        presenter = new CommentPresenter(this);
        loadingDialog.show();
        presenter.getData2(page);
    }

    @Override
    public void onLoadCommentData(MyCommentBean bean) {
        smartRefreshLayout.finishRefresh();
        loadingDialog.dismiss();
        if (bean.data != null && bean.data.size() > 0){
            list.clear();
            list.addAll(bean.data);
            adapter.notifyDataSetChanged();
        }else {
            lvAt.setVisibility(View.GONE);
            tvNothing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowToast(String s) {
        loadingDialog.dismiss();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onCompleted() {

    }
    public class AtCommentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
            AtCommentAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(AtMeActivity.this).inflate(R.layout.adapter_my_comment, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (AtCommentAdapter.ViewHolder) convertView.getTag();
            }
            MyCommentBean.MyCommentData commentData = list.get(position);
            holder.tvName.setText(commentData.nick);
            holder.tvContent.setText(commentData.message);
            holder.tvMe.setText(commentData.nick + "在作品中提到了你");
            holder.tvTime.setText(commentData.f_creatortime);
            if (!TextUtils.isEmpty(commentData.avator)){
                Picasso.with(AtMeActivity.this).load(UIUtils.getImageUrl(commentData.avator)).into(holder.ivUserImg);
            }
            if (!TextUtils.isEmpty(commentData.coverimg)){
                Picasso.with(AtMeActivity.this).load(UIUtils.getImageUrl(commentData.coverimg)).into(holder.tvCover);
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
            @BindView(R.id.tv_me)
            TextView tvMe;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_cover)
            ImageView tvCover;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
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
                page = 0;
                presenter.getData(page);
            }
        });
        smartRefreshLayout.setDisableContentWhenRefresh(true);
    }
}
