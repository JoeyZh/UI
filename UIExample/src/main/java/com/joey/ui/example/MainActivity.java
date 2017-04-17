package com.joey.ui.example;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.joey.protocol.HttpRequestManager;
import com.joey.protocol.ResponseError;
import com.joey.protocol.ResponseListener;
import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.JAlertDialog;
import com.joey.utils.LogUtils;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = View.inflate(this, R.layout.dlg_add_spare_part, null);
        JAlertDialog dialog = new JAlertDialog.Builder(MainActivity.this)
                .setContentView(view)
//                .setTitle("sssssss")
//                .setMessage("44x43的那怎么办")
                .setNegativeButton("aa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("上传图片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        test();
                    }
                }).create();
        dialog.show();
        topBarLayout.setLeftResource(-1);
        topBarLayout.setTitle("测试呀");
    }

    private void test() {
        showDialog("正在上传。。。");
        String url = "http://192.168.1.132:8080/EAM/exceptionSubmit/saveExceptionSubmit.action";
        String json = "{\"exceptionSubmitId\":\"\",\"equipmentPartId\":\"41\",\"equipmentId\":\"1010103\",\"resourceType\":\"yx\",\"submitContent\":\"测试\",\"submitUserId\":\"101\",\"deptId\":\"01\"}";
        HttpRequestManager manager = new HttpRequestManager(this);
        String sdCardPath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/img0";
        File[] files = new File[3];
        for (int i = 0; i < 3; i++) {
            String path = sdCardPath + (i + 1) + ".png";
            File file = new File(path);
            files[i] = file;
            LogUtils.a(path + " is exists :" + file.exists());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("json", json);
        manager.upLoad(url, map, new ResponseListener<JSONArray>(this) {
            @Override
            public void onSuccess(JSONArray objects, int status) {
                dismissDialog();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);

                showDialog("正在上传" + current + "/" + total);
            }

            @Override
            public void onError(ResponseError error, int status) {
                dismissDialog();
            }
        }, files);
    }
}
