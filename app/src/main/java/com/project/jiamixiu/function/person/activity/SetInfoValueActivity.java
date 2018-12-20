package com.project.jiamixiu.function.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.jiamixiu.R;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetInfoValueActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_value)
    EditText edtValue;
    @BindView(R.id.edt_value_2)
    EditText edtValue2;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.ll_sign)
    LinearLayout ll_sign;
    @BindView(R.id.ll_nick)
    LinearLayout ll_nick;
    String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_info_value);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if ("1".equals(type)){
            toolbar.setTitle("昵称");
            ll_nick.setVisibility(View.VISIBLE);
        }else {
            toolbar.setTitle("个性签名");
            ll_sign.setVisibility(View.VISIBLE);
        }
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        edtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())){
                    tvSave.setOnClickListener(SetInfoValueActivity.this);
                    tvSave.setTextColor(ContextCompat.getColor(SetInfoValueActivity.this,R.color.text_color_33));
                }else {
                    tvSave.setOnClickListener(null);
                    tvSave.setTextColor(ContextCompat.getColor(SetInfoValueActivity.this,R.color.theme_color));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtValue2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())){
                    tvSave.setOnClickListener(SetInfoValueActivity.this);
                    tvSave.setTextColor(ContextCompat.getColor(SetInfoValueActivity.this,R.color.text_color_33));
                }else {
                    tvSave.setOnClickListener(null);
                    tvSave.setTextColor(ContextCompat.getColor(SetInfoValueActivity.this,R.color.text_color_66));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String vv = "";
        if ("1".equals(type)){
            vv = edtValue.getText().toString();
        }else {
            vv = edtValue2.getText().toString();
        }
        if (TextUtils.isEmpty(vv)){
            UIUtils.showToast(SetInfoValueActivity.this,"值不能为空");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("type",type);
        intent.putExtra("value",vv);
        setResult(RESULT_OK,intent);
        finish();
    }
}
