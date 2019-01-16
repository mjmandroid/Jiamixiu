package com.reafor.jiamixiu.function.emoji;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.function.emoji.fragment.EmojiFragment;

/**
 * Created by zejian
 * Time  16/1/11 下午4:17
 * Email shinezejian@163.com
 * Description:主体内容为EditText
 */
public class EditTextActivity extends AppCompatActivity {

    private EditText ll_main; //编辑器
    private EmojiFragment emotionMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_edittext);
        initView();
        initListentener();
        initDatas();
    }

    /**
     * 初始化控件
     */
    private void initView()
    {
        ll_main= (EditText) findViewById(R.id.ll_main);
        ll_main.setFocusable(true);
        ll_main.setFocusableInTouchMode(true);
        ll_main.requestFocus();
        InputMethodManager im = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        im.showSoftInput(ll_main, 0);
    }

    /**
     * 初始化监听器
     */
    public void initListentener(){

    }

    /**
     * 初始化布局数据
     */
    private void initDatas(){
        initEmotionMainFragment();
    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmojiFragment.BIND_TO_EDITTEXT,false);
        //隐藏控件
        bundle.putBoolean(EmojiFragment.HIDE_BAR_EDITTEXT_AND_BTN,true);
        //替换fragment
        //创建修改实例
        emotionMainFragment =EmojiFragment.newInstance(EmojiFragment.class,bundle);
        emotionMainFragment.bindToContentView(ll_main);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress()) {
            super.onBackPressed();
        }
    }
    public void respond(String s){
        Intent i = new Intent();
        i.putExtra("value",s);
        setResult(RESULT_OK,i);
        finish();
    }
}
