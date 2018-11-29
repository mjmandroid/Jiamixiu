package com.project.jiamixiu.function.person.activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.project.jiamixiu.R;
import com.project.jiamixiu.bean.PersonInfoDetailBean;
import com.project.jiamixiu.function.login.RegisterActivity;
import com.project.jiamixiu.function.person.inter.IPersonInfoView;
import com.project.jiamixiu.function.person.presenter.PersonInfoPresenter;
import com.project.jiamixiu.utils.DialogUtils;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.widget.CustomerToolbar;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener,IPersonInfoView {
    private static final int REQUST_CAMER = 4434;
    private static final int SELECT_PHOTO = 4222;
    private static final int TAKE_PICTURE = 4311;
    private static final int PUT_NAME = 5433;
    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.ll_headimage)
    LinearLayout llHeadimage;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_address_2)
    TextView tvAddress2;
    @BindView(R.id.ll_address_2)
    LinearLayout llAddress2;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.tv_confirm_name)
    TextView tvConfirmName;
    @BindView(R.id.ll_confirm_name)
    LinearLayout llConfirmName;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_tier)
    TextView tvTier;
    @BindView(R.id.ll_tier)
    LinearLayout llTier;
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.ll_bill)
    LinearLayout llBill;
    @BindView(R.id.btn_exit)
    Button btnExit;
    PersonInfoPresenter personInfoPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        toolbar.setTitle("个人资料");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
         personInfoPresenter = new PersonInfoPresenter(this);
         personInfoPresenter.getPersonInfo();

    }

    @OnClick({R.id.ll_headimage,R.id.ll_nickname,R.id.ll_gender,R.id.ll_birthday,R.id.ll_address,R.id.ll_address_2,R.id.ll_sign,
            R.id.ll_confirm_name,R.id.ll_pwd,R.id.ll_email,R.id.ll_phone,R.id.ll_tier,R.id.ll_bill})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_headimage:
                showPhotoWindow();
                break;
            case R.id.ll_nickname:
                break;
            case R.id.ll_gender:
                showGender();
                break;
            case R.id.ll_birthday:
                break;
            case R.id.ll_address:
                break;
            case R.id.ll_address_2:
                break;
            case R.id.ll_sign:
                break;
            case R.id.ll_confirm_name:
                break;
            case R.id.ll_pwd:
                Intent pi = new Intent(this, RegisterActivity.class);
                pi.putExtra("type","1");
                startActivity(pi);
                break;
            case R.id.ll_email:
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_tier:
                break;
            case R.id.ll_bill:
                break;
            case R.id.btn_exit:
                SharedPreferencesUtil.saveToken("");
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.takePhoto:
                if ((ContextCompat.checkSelfPermission(PersonInfoActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(PersonInfoActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)
                        && (ContextCompat.checkSelfPermission(PersonInfoActivity.this, CAMERA)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonInfoActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE, CAMERA},
                            REQUST_CAMER);
                } else {
                    photo();
                }
                photoDialog.dismiss();
                break;
            case R.id.selectPic:
                if ((ContextCompat.checkSelfPermission(PersonInfoActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(PersonInfoActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonInfoActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE},
                            REQUST_CAMER);
                } else {
                    selectPic(SELECT_PHOTO);
                }
                photoDialog.dismiss();
                break;
            case R.id.cancel:
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
        personInfoPresenter.upLoadingFile(path);
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
    public void loadPicSuccess(String fileUrl) {
        personInfoPresenter.updateInfo(fileUrl,1);
    }

    @Override
    public void LoadPicFail() {

    }

    @Override
    public void getPersonInfo(PersonInfoDetailBean bean) {
        if (bean.data != null){
            if (!TextUtils.isEmpty(bean.data.avator)){
                Picasso.with(this).load(bean.data.avator).into(ivUserImg);
            }
            if (!TextUtils.isEmpty(bean.data.nick)){
                tvNickname.setText(bean.data.nick);
            }
            if (!TextUtils.isEmpty(bean.data.gender)){
                switch (bean.data.gender){
                    case "0":
                        tvGender.setText("未设置");
                        break;
                    case "1":
                        tvGender.setText("男");
                        break;
                    case "2":
                        tvGender.setText("女");
                        break;
                }

            }else {
                tvGender.setText("未设置");
            }



        }
    }

    @Override
    public void updateInfoSuccess() {
        personInfoPresenter.getPersonInfo();
    }

    @Override
    public void updateFail() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case PUT_NAME:
                    String modify_name = data.getStringExtra("modify_name");
                    tvNickname.setText(modify_name);
                    break;
                case SELECT_PHOTO:
                    if (data == null) {
                        return;
                    }
                    inUri = getUri(data);//这个方法是预防小米手机  选择照片崩溃
                    if (outUri == null){
                        outUri = Uri.fromFile(new File(getPath()));
                    }
                    Crop.of(inUri,outUri).asSquare().start(this);
                    break;
                case TAKE_PICTURE:
                    inUri  = Uri.fromFile(new File(path));
                    if (outUri == null){
                        outUri = Uri.fromFile(new File(getPath()));
                    }
                    if (inUri != null){
                        String s = inUri.getPath();
                        Crop.of(inUri,outUri).asSquare().start(this);
                    }

                    break;
                case Crop.REQUEST_CROP:
                    path = outUri.getPath();
                    upLoadingPicture(path);
                    break;
            }
        }
    }


    Dialog genderDialog;
    //性别选择框
    private void showGender() {
        if (genderDialog == null){
            View dialog = UIUtils.inflate(this,R.layout.dialog_choose_gender);
            genderDialog = DialogUtils.BottonDialog(PersonInfoActivity.this, dialog);
            Button btn_cancel = (Button) dialog.findViewById(R.id.bt_cancel);
            final Button btn_man = (Button) dialog.findViewById(R.id.btn_man);
            final Button btn_women = (Button) dialog.findViewById(R.id.btn_women);
            btn_man.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    genderDialog.dismiss();
                    personInfoPresenter.updateInfo("1",2);
                }
            });
            btn_women.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    genderDialog.dismiss();
                    personInfoPresenter.updateInfo("2",2);
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    genderDialog.dismiss();
                }
            });
        }
        genderDialog.show();
    }
}
