package com.reafor.jiamixiu.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.reafor.jiamixiu.R;

public class DownloadDialog extends AlertDialog {
    private TextView tips_loading_msg;
    public DownloadDialog(Context context) {
        this(context, R.style.loadingDialog);
    }

    public DownloadDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_downloading_layout);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
    }

    public void setText(String message) {
        if (tips_loading_msg != null)
        tips_loading_msg.setText(message);
    }
}
