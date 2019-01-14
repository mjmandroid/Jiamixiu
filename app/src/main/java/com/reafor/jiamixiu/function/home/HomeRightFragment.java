package com.reafor.jiamixiu.function.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.base.BaseFragment;
import com.reafor.jiamixiu.function.message.MessageFragment;
import com.reafor.jiamixiu.function.person.PersonFragment;
import com.reafor.jiamixiu.function.subscrite.SubscriteFragment;
import com.reafor.jiamixiu.function.upload.CropVideoActivity;
import com.reafor.jiamixiu.manger.FragmentChangeManager;
import com.reafor.jiamixiu.utils.FileUtils;
import com.reafor.jiamixiu.utils.SharedPreferencesUtil;
import com.reafor.jiamixiu.utils.ToastUtil;

import java.util.ArrayList;

import cn.jzvd.JzvdStd;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeRightFragment extends BaseFragment {
    private RadioGroup radioGroup;
    private static final int REQUST_CAMER = 1002;
    private static final int SELECT_PHOTO = 1003;
    private FragmentChangeManager manager;
    private boolean isVideoResume = true;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home_right_layout,null);
        radioGroup = view.findViewById(R.id.rg);
        initEvent(view);
        ArrayList fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new HomeVideoFragment());
        fragmentList.add(new SubscriteFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new PersonFragment());

        manager = new FragmentChangeManager(getChildFragmentManager(),R.id.fl_home,fragmentList);
        return view;
    }

    private void initEvent(View view) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        JzvdStd.goOnPlayOnResume();
                        isVideoResume = true;
                        manager.setFragments(0);
                        break;
                    case R.id.rb_sub:
                        if (isVideoResume){
                            JzvdStd.goOnPlayOnPause();
                            isVideoResume = false;
                        }
                        manager.setFragments(1);
                        SubscriteFragment fragment = (SubscriteFragment) manager.getCurrentFragment();
                        if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())){
                            fragment.setUpdate(false);
                        }
                        fragment.updateList(null);
                        break;
                    case R.id.rb_msg:
                        if (isVideoResume){
                            JzvdStd.goOnPlayOnPause();
                            isVideoResume = false;
                        }
                        manager.setFragments(2);
                        break;
                    case R.id.rb_mine:
                        if (isVideoResume){
                            JzvdStd.goOnPlayOnPause();
                            isVideoResume = false;
                        }
                        manager.setFragments(3);
                        break;
                }
            }
        });
        view.findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE},
                            REQUST_CAMER);
                } else {
                    selectPic(SELECT_PHOTO);
                }
            }
        });
    }

    @Override
    public void initData() {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO){
            if (data == null)
                return;
            Uri uri = data.getData();
            if (uri == null)
                return;
            String videoPath = FileUtils.getPath2uri(getActivity(), uri);
            if (!videoPath.contains(".mp4")){
                ToastUtil.showTosat(mContext,"不支持的格式！");
                return;
            }
            Intent intent = new Intent(mContext, CropVideoActivity.class);
            intent.putExtra("path",videoPath);
            startActivity(intent);
        }
    }
}
