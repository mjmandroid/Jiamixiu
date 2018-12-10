package com.project.jiamixiu.function.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.function.upload.prenster.PublishPrenster;
import com.project.jiamixiu.function.upload.view.IpublishView;
import com.project.jiamixiu.utils.FileUtils;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishVideoActivity extends BaseActivity implements IpublishView {

    @BindView(R.id.iv_title)
    ImageView iv_title;
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.et_title)
    EditText et_title;
    private String videoPath;
    private String fid="",tagName = "";
    private PublishPrenster prenster;
    private LoadingDialog loadingDialog;
    private String imagPath,ossid;

    @Override
    protected void initData() {
        super.initData();
        prenster = new PublishPrenster(this);
        loadingDialog = new LoadingDialog(this, "正在提交...");
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_upload_publish_layout);
        ButterKnife.bind(this);
        toolbar.setTitle("发布");
        toolbar.setToolbarLisenter(() -> finish());
        Intent intent = getIntent();
        videoPath = intent.getStringExtra("videoPath");
        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        iv_title.setImageBitmap(bitmap);
        imagPath = FileUtils.saveImage2Local(bitmap);
    }

    @OnClick({R.id.tv_game_type,R.id.tv_game_tags,R.id.ll_look,R.id.btn_commit})
    public void viewOnClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.tv_game_type:
                intent = new Intent(this,SelectTagTypeActivity.class);
                intent.putExtra("title","游戏类型");
                startActivityForResult(intent,11);
                break;
            case R.id.tv_game_tags:
                intent = new Intent(this,SelectTagTypeActivity.class);
                intent.putExtra("title","游戏标签");
                startActivityForResult(intent,12);
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void commit() {

        if (TextUtils.isEmpty(et_title.getText().toString())){
            ToastUtil.showTosat(this,"请输入标题");
            return;
        }
        if (TextUtils.isEmpty(fid)){
            ToastUtil.showTosat(this,"请选择游戏类型");
            return;
        }
        loadingDialog.show();
        //上传视频
        prenster.uploadVideo2Oss(videoPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 1){
           fid = data.getStringExtra("id");

        } else if(requestCode == 12 && resultCode == 2){
            tagName = data.getStringExtra("name");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void commitsuccess() {
        ToastUtil.showTosat(this,"上传成功！");
        int index = activityList.size();
        activityList.get(index-1).finish();
        activityList.get(index-2).finish();
        activityList.get(index-3).finish();
    }

    @Override
    public void onError(String errmsg) {
        loadingDialog.dismiss();
    }

    @Override
    public void onFinish() {
        loadingDialog.dismiss();
    }

    @Override
    public void uploadVideoSuccess(String ossid) {
        this.ossid = ossid;
        //上传标题图片
        prenster.uploadPicture(imagPath);
    }

    @Override
    public void uploadPictureSuccess(String imgUrl) {
        prenster.commit(et_title.getText().toString().trim(),imgUrl,tagName,"",
                ossid,"",fid);
    }
}
