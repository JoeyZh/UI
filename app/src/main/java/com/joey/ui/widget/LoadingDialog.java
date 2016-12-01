package com.joey.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joey.R;

/**
 * Created by Administrator on 2016/8/31.
 * modified by Joey 统一加载动画,修改加载布局
 */
public class LoadingDialog {

    private Dialog loadingDlg;
    private ImageView loadingImg;
    private AnimationDrawable drawable;
    private View rootView;
    private TextView tvMsg;

    public LoadingDialog(Context context) {
        init(context, false);
    }

    public LoadingDialog(Context context, boolean cancel) {
        init(context, cancel);
    }

    public void init(Context context, boolean cancel) {
        rootView = View.inflate(context, R.layout.dlg_loading, null);
        tvMsg = (TextView) rootView.findViewById(R.id.dlg_msg_text);
        loadingImg = (ImageView) rootView.findViewById(R.id.dlg_loading_img);
        drawable = (AnimationDrawable) loadingImg.getBackground();
        loadingDlg = new Dialog(context, R.style.dialog_transparent);
        loadingDlg.setContentView(rootView);
        loadingDlg.setCanceledOnTouchOutside(cancel);
        loadingDlg.setCancelable(cancel);
    }

    public void showDialog() {
        drawable.start();
        loadingDlg.show();
    }

    public void showDialog(String message) {
        if (null == message || message.isEmpty()) {
            showDialog();
            return;
        }
        tvMsg.setText(message);
        showDialog();
    }

    public void showDialog(int message) {
        if (message < 0) {
            showDialog();
            return;
        }
        tvMsg.setText(message);
        showDialog();
    }

    public void dismissDialog() {
        drawable.stop();
        loadingDlg.dismiss();
    }
}
