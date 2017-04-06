package com.joey.ui.example;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.JAlertDialog;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JAlertDialog dialog = new JAlertDialog.Builder(MainActivity.this)
                .setMessage("sssssss")
                .setTitle("hahahaah")
                .setNegativeButton("aaaa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("aaaa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
        topBarLayout.setLeftResource(-1);
        topBarLayout.setTitle("测试呀");
    }
}
