package com.project.jiamixiu.function.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.jiamixiu.R;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetInfoValueActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_value)
    EditText edtValue;
    @BindView(R.id.btn_ok)
    Button btnOk;
    String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_info_value);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if ("1".equals(type)){
            toolbar.setTitle("设置昵称");
        }else {
            toolbar.setTitle("设置个性签名");
        }
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtValue.getText().toString())){
                    UIUtils.showToast(SetInfoValueActivity.this,"值不能为空");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("type",type);
                intent.putExtra("value",edtValue.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
