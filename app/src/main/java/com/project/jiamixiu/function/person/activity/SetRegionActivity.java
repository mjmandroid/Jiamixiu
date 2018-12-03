package com.project.jiamixiu.function.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.project.jiamixiu.R;
import com.project.jiamixiu.utils.UIUtils;
import com.project.jiamixiu.widget.CustomerToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetRegionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_region)
    EditText edtRegion;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_region);
        ButterKnife.bind(this);
        toolbar.setTitle("设置常出没地");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vv = edtRegion.getText().toString();
                if (TextUtils.isEmpty(vv)){
                    UIUtils.showToast(SetRegionActivity.this,"值不能为空");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("value",vv);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
