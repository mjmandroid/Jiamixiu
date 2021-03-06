package com.reafor.jiamixiu.function.person.activity;

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

import com.reafor.jiamixiu.R;
import com.reafor.jiamixiu.utils.UIUtils;
import com.reafor.jiamixiu.widget.CustomerToolbar;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetRegionActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomerToolbar toolbar;
    @BindView(R.id.edt_region)
    EditText edtRegion;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_region);
        ButterKnife.bind(this);
        toolbar.setTitle("常出没地");
        toolbar.setToolbarLisenter(new CustomerToolbar.ToolbarListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
        edtRegion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())){
                    tvSave.setOnClickListener(SetRegionActivity.this);
                    tvSave.setTextColor(ContextCompat.getColor(SetRegionActivity.this,R.color.theme_color));
                }else {
                    tvSave.setOnClickListener(null);
                    tvSave.setTextColor(ContextCompat.getColor(SetRegionActivity.this,R.color.text_color_99));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

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
}
