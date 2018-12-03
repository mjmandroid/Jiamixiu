package com.project.jiamixiu.function.message.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.MyCommentBean;
import com.project.jiamixiu.function.message.inter.ICommentView;
import com.project.jiamixiu.function.message.presenter.CommentPresenter;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MycommentActivity extends AppCompatActivity implements ICommentView {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.lv_comment)
    ListView lvComment;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    private ArrayList<MyCommentBean.MyCommentData> list = new ArrayList<>();
    private CommentAdapter adapter;
    private CommentPresenter presenter;
    private int page;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycomment);
        loadingDialog = new LoadingDialog(this);
        ButterKnife.bind(this);
        toolbar.setTitle("评论");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });

        adapter = new CommentAdapter();
        lvComment.setAdapter(adapter);
        presenter = new CommentPresenter(this);
        loadingDialog.show();
        presenter.getData(page);
    }

    @Override
    public void onLoadCommentData(MyCommentBean bean) {
        loadingDialog.dismiss();
        if (bean.data != null && bean.data.size() > 0){
            list.addAll(bean.data);
            adapter.notifyDataSetChanged();
        }else {
            lvComment.setVisibility(View.GONE);
            tvNothing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowToast(String s) {
        loadingDialog.dismiss();
    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

    }


    public class CommentAdapter extends BaseAdapter {

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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MycommentActivity.this).inflate(R.layout.adapter_my_comment, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            MyCommentBean.MyCommentData commentData = list.get(position);
            holder.tvName.setText(commentData.nick);
            holder.tvContent.setText(commentData.message);
            holder.tvMe.setText("你评论了" + commentData.videousername + "的作品");
            holder.tvTime.setText(commentData.f_creatortime);
            Picasso.with(MycommentActivity.this).load(commentData.avator).into(holder.ivUserImg);
            Picasso.with(MycommentActivity.this).load(commentData.coverimg).into(holder.tvCover);
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
}
