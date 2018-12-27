package com.reafor.jiamixiu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.function.home.HomeFragment;
import com.reafor.jiamixiu.function.message.MessageFragment;
import com.reafor.jiamixiu.function.person.PersonFragment;
import com.reafor.jiamixiu.function.subscrite.SubscriteFragment;
import com.reafor.jiamixiu.function.upload.CropVideoActivity;
import com.reafor.jiamixiu.manger.FragmentChangeManager;
import com.reafor.jiamixiu.manger.HttpManager;
import com.reafor.jiamixiu.manger.listener.HttpRequestListener;
import com.reafor.jiamixiu.utils.FileUtils;
import com.reafor.jiamixiu.utils.OssUtils;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.ToastUtil;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.utils.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    FragmentChangeManager manager;
    private static final int REQUST_CAMER = 1002;
    private static final int SELECT_PHOTO = 1003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.rg);
        ArrayList fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SubscriteFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new PersonFragment());

        manager = new FragmentChangeManager(getSupportFragmentManager(),R.id.fl_content,fragmentList);
        initEvent();
        getOsToken();
    }

    private void initEvent() {
        findViewById(R.id.iv_add).setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        manager.setFragments(0);
                        break;
                    case R.id.rb_sub:
                        manager.setFragments(1);
                        SubscriteFragment fragment = (SubscriteFragment) manager.getCurrentFragment();
                        if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                            fragment.setUpdate(false);
                        }
                        fragment.updateList(null);
                        break;
                    case R.id.rb_msg:
                        manager.setFragments(2);
                        break;
                    case R.id.rb_mine:
                        manager.setFragments(3);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FileUtils.delVideoFile();
    }

    private void getOsToken(){
        HttpManager.sendRequest(UrlConst.os_token, new HashMap<>(), new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.optJSONObject("data");
                    String securitytoken = data.optString("securitytoken");
                    SharedPreferencesUtil.saveOsToken(securitytoken);
                    String accesskeysecret = data.optString("accesskeysecret");
                    SharedPreferencesUtil.saveOsSecret(accesskeysecret);
                    String accesskeyid = data.optString("accesskeyid");
                    SharedPreferencesUtil.saveOsKey(accesskeyid);
                    data.optString("expiration");
                    OssUtils.getInstance().initOss(accesskeyid,accesskeysecret,securitytoken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFail(String result, String code) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if ((ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE},
                    REQUST_CAMER);
        } else {
            selectPic(SELECT_PHOTO);
        }
    }
    public void selectPic(int mark) {
//        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//
//        if (openAlbumIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(openAlbumIntent, mark);
//        } else {
//            UIUtils.showToast(this,"您的手机暂不支持选择视频，请查看权限是否允许！");
//        }
        Intent intent = null;
        if (FileUtils.isMIUI()){
            intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"video/*");
        } else {
            intent = new Intent();
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
            }
        }
        startActivityForResult(Intent.createChooser(intent, "视频选择"), mark);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO){
            if (data == null)
                return;
            Uri uri = data.getData();
            if (uri == null)
                return;
            String videoPath = FileUtils.getPath2uri(this, uri);
            if (!videoPath.contains(".mp4")){
                ToastUtil.showTosat(this,"不支持的格式！");
                return;
            }
            Intent intent = new Intent(this, CropVideoActivity.class);
            intent.putExtra("path",videoPath);
            startActivity(intent);
        }
    }
    private long mExitTime;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showTosat(this,"再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
