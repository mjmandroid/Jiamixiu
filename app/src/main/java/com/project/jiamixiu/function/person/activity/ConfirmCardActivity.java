package com.project.jiamixiu.function.person.activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.jiamixiu.R;
import com.project.jiamixiu.function.person.inter.IConfirmCardView;
import com.project.jiamixiu.function.person.presenter.ConfirmCardPresenter;
import com.project.jiamixiu.utils.DialogUtils;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.project.jiamixiu.widget.LoadingDialog;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ConfirmCardActivity extends AppCompatActivity implements View.OnClickListener ,IConfirmCardView {
    private static final int SELECT_PHOTO = 221;
    private static final int TAKE_PICTURE = 111;
    private static final int REQUST_CAMER = 333;
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_number)
    EditText edt_number;
    @BindView(R.id.iv_pic2)
    ImageView iv_pic2;
    @BindView(R.id.iv_pic1)
    ImageView iv_pic1;
    @BindView(R.id.btn_ok)
    Button btnOk;
    String cardImg1,cardImg2;
    int select = 0;
    ConfirmCardPresenter cardPresenter;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_card);
        ButterKnife.bind(this);
        toolbar.setTitle("实名认证");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        loadingDialog = new LoadingDialog(this);
        cardPresenter = new ConfirmCardPresenter(this);
    }
    @OnClick({R.id.btn_ok,R.id.rl_pic2,R.id.rl_pic1})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                loadingDialog.dismiss();
                break;
            case R.id.btn_ok:
                String name = edt_name.getText().toString();
                String card = edt_number.getText().toString();
                if (TextUtils.isEmpty(name)){
                    UIUtils.showToast(this,"请输入身份姓名");
                    return;
                }
                if (TextUtils.isEmpty(card)){
                    UIUtils.showToast(this,"请输入身份证号");
                    return;
                }
                if (TextUtils.isEmpty(cardImg1)){
                    UIUtils.showToast(this,"请上传身份正面照");
                    return;
                }
                if (TextUtils.isEmpty(cardImg2)){
                    UIUtils.showToast(this,"请上传身份反面照");
                    return;
                }
                loadingDialog.show();
                cardPresenter.confirm(name,card,cardImg1,cardImg2,"");
                break;
            case R.id.rl_pic1:
                select = 0;
                showPhotoWindow();
                break;
            case R.id.rl_pic2:
                select =1;
                showPhotoWindow();
                break;
            case R.id.takePhoto:
                if ((ContextCompat.checkSelfPermission(ConfirmCardActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(ConfirmCardActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)
                        && (ContextCompat.checkSelfPermission(ConfirmCardActivity.this, CAMERA)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ConfirmCardActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE, CAMERA},
                            REQUST_CAMER);
                } else {
                    photo();
                }
                photoDialog.dismiss();
                break;
            case R.id.selectPic:
                if ((ContextCompat.checkSelfPermission(ConfirmCardActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(ConfirmCardActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ConfirmCardActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE},
                            REQUST_CAMER);
                } else {
                    selectPic(SELECT_PHOTO);
                }
                photoDialog.dismiss();
                break;
        }
    }


    private Dialog photoDialog;
    //弹拍选择照框
    private void showPhotoWindow() {
        if (photoDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_photo,null);
            view.findViewById(R.id.takePhoto).setOnClickListener(this);
            view.findViewById(R.id.selectPic).setOnClickListener(this);
            view.findViewById(R.id.cancel).setOnClickListener(this);
            photoDialog = DialogUtils.BottonDialog(this, view);
        }
        photoDialog.show();
    }
    /**
     * 相册选取
     */
    public void selectPic(int mark) {
        // 进入选择图片：
        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (openAlbumIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openAlbumIntent, mark);
        } else {
            UIUtils.showToast(this,"您的手机暂不支持选择图片，请查看权限是否允许！");
        }
    }
    private String getPath(){
        StringBuffer sDir = new StringBuffer();
        if (hasSDcard()) {
            sDir.append(Environment.getExternalStorageDirectory() + "/MyPicture/");
        } else {
            String dataPath = Environment.getRootDirectory().getPath();
            sDir.append(dataPath + "/MyPicture/");
        }

        File fileDir = new File(sDir.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File picfile = new File(fileDir, String.valueOf(System.currentTimeMillis()) + ".jpg");
        String path = picfile.getPath();
        return path;
    }

    private File picfile;
    private String path;
    //拍照
    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        StringBuffer sDir = new StringBuffer();
        if (hasSDcard()) {
            sDir.append(Environment.getExternalStorageDirectory() + "/MyPicture/");
        } else {
            String dataPath = Environment.getRootDirectory().getPath();
            sDir.append(dataPath + "/MyPicture/");
        }

        File fileDir = new File(sDir.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        picfile = new File(fileDir, String.valueOf(System.currentTimeMillis()) + ".jpg");
        path = picfile.getPath();
        Uri imageUri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            imageUri = FileProvider.getUriForFile(this, getString(R.string.less_provider_file_authorities), picfile);
//            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//        } else {
        imageUri = Uri.fromFile(picfile);
//        }
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
    //判断是否有SD卡
    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }
    //提交图片到又拍云
    private void upLoadingPicture(String path) {
        loadingDialog.show();
        cardPresenter.upLoadingFile(path);
    }
    private  Uri inUri,outUri;
    /**
     * 把绝对路径转换成content开头的URI
     * For  为解决小米手机选择相册闪退
     */
    private Uri getUri(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID}, buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                }
                if (index == 0) {
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PHOTO:
                    if (data == null) {
                        return;
                    }
                    inUri = getUri(data);//这个方法是预防小米手机  选择照片崩溃
                    if (outUri == null) {
                        outUri = Uri.fromFile(new File(getPath()));
                    }
                    Crop.of(inUri, outUri).asSquare().start(this);
                    break;
                case TAKE_PICTURE:
                    inUri = Uri.fromFile(new File(path));
                    if (outUri == null) {
                        outUri = Uri.fromFile(new File(getPath()));
                    }
                    if (inUri != null) {
                        String s = inUri.getPath();
                        Crop.of(inUri, outUri).asSquare().start(this);
                    }

                    break;
                case Crop.REQUEST_CROP:
                    path = outUri.getPath();
                    upLoadingPicture(path);
                    break;
            }
        }
    }

    @Override
    public void loadPicSuccess(String fileUrl) {
        loadingDialog.dismiss();

        switch (select){
            case 0:
                Picasso.with(this).load(UIUtils.getImageUrl(fileUrl)).into(iv_pic1);
                cardImg1 = fileUrl;
                break;
            case 1:
                Picasso.with(this).load(UIUtils.getImageUrl(fileUrl)).into(iv_pic2);
                cardImg2 = fileUrl;
                break;
        }
    }

    @Override
    public void LoadPicFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onConfirmSuccess() {
        loadingDialog.dismiss();
    }

    @Override
    public void onShowToast(String s) {

    }

    @Override
    public void onLoadFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCompleted() {

    }
}

