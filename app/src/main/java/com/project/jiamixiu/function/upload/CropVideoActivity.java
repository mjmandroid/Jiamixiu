package com.project.jiamixiu.function.upload;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.project.jiamixiu.R;
import com.project.jiamixiu.base.BaseActivity;
import com.project.jiamixiu.interfaces.VideoCompressListener;
import com.project.jiamixiu.interfaces.VideoTrimListener;
import com.project.jiamixiu.utils.ToastUtil;
import com.project.jiamixiu.utils.VideoCompressor;
import com.project.jiamixiu.widget.LoadingDialog;
import com.project.jiamixiu.widget.VideoTrimmerView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropVideoActivity extends BaseActivity implements VideoTrimListener {

    private String videoPath;
    private LoadingDialog loadingDialog;
    @BindView(R.id.trimmer_view)
    VideoTrimmerView trimmerView;
    private String storageVideoPath;
    private int speed;

    @Override
    protected void initData() {
        videoPath = getIntent().getStringExtra("path");
        loadingDialog = new LoadingDialog(this,"正在处理...");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);
        File videoFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/MyVideo");
        storageVideoPath = videoFile.getPath();
        if (!videoFile.exists()){
            videoFile.mkdirs();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_upload_video_layout);
        ButterKnife.bind(this);
        trimmerView.setOnTrimVideoListener(this);
        trimmerView.initVideoByURI(Uri.parse(videoPath));

    }

    @OnClick({R.id.iv_back,R.id.tv_next})
    public void btnOnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                trimmerView.onCancelClicked();
                break;
            case R.id.tv_next:
                trimmerView.onSaveClicked();
                break;
        }
    }

    @Override public void onPause() {
        super.onPause();
        trimmerView.onVideoPause();
        trimmerView.setRestoreState(true);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        trimmerView.onDestroy();
    }
    @Override
    public void onStartTrim() {
        loadingDialog.show();
    }

    @Override
    public void onFinishTrim(String outputFile) {

        /*String outputFile = storageVideoPath + File.separator + System.currentTimeMillis() + ".mp4";
        VideoCompressor.compress(this, in, outputFile, new VideoCompressListener() {
            @Override public void onSuccess(String message) {
                Intent intent = new Intent(CropVideoActivity.this, SelectTitlePageActivity.class);
                intent.putExtra("videoPath",outputFile);
                startActivity(intent);
                String delFile = in.substring(in.lastIndexOf("/")+1);
                deleteFile(delFile);
            }

            @Override public void onFailure(String message) {

            }

            @Override public void onFinish() {
                if (loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        });*/
        Intent intent = new Intent(CropVideoActivity.this, SelectTitlePageActivity.class);
        intent.putExtra("videoPath",outputFile);
        startActivity(intent);
    }

    @Override
    public void onCancel() {
        trimmerView.onDestroy();
        finish();
    }

    @Override
    public void onSpeedSelect(int speedTag) {
        //0->急慢  1->慢  2->正常  3->快  4->很快
        this.speed = speedTag;
    }
}
