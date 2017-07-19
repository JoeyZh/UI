package com.joey.ui.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.joey.protocol.HttpRequestManager;
import com.joey.protocol.ResponseError;
import com.joey.protocol.ResponseListener;
import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.JAlertDialog;
import com.joey.utils.LogUtils;
import com.joey.utils.NetWorkUtil;
import com.joey.utils.TimeUtils;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    View tv1;
    View tv2;
    View tv3;
    View tv4;
    String cookieKey = "";
    String cookie = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv_dlg);
        tv2 = findViewById(R.id.tv_calendar);
        tv3 = findViewById(R.id.tv_upload);
        tv4 = findViewById(R.id.tv_cookies);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        topBarLayout.setLeftResource(-1);
        topBarLayout.setTitle("测试呀");
        topBarLayout.showNotice("ceshishihiho");
        String test1 = "fjklsjfljk";
        String test2 = "189.234.2.443";
        String test3 = "189.234.2.123";

        LogUtils.a(test1 + " is Ip :" + NetWorkUtil.isIP(test1));
        LogUtils.a(test2 + " is Ip :" + NetWorkUtil.isIP(test2));
        LogUtils.a(test3 + " is Ip :" + NetWorkUtil.isIP(test3));
    }


    private void showJDialog() {
        View view = View.inflate(this, R.layout.dlg_add_spare_part, null);
        JAlertDialog dialog = new JAlertDialog.Builder(MainActivity.this)
                .setContentView(view)
//                .setTitle("dialog")
                .setNegativeButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_calendar:
                gotoCalendar();
                break;
            case R.id.tv_dlg:
                showJDialog();
                break;
            case R.id.tv_upload:
                testHttpPost();
                break;
            case R.id.tv_cookies:
                testCookies();
                break;
        }
    }

    private void gotoCalendar() {
        startActivity(new Intent(MainActivity.this, CalenderActivity.class));
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

    private void testHttpPost() {
        HttpRequestManager.initReponseKey("errcode","errmsg","result");
        final HttpRequestManager manager = new HttpRequestManager(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("AccountID", "jwj");
        map.put("Password", "123456");
        String dateStr = TimeUtils.convertDateToStr(new Date(System.currentTimeMillis()),TimeUtils.FORMATTER_DATE);
        manager.httpRequest("", "http://alp.joeyzh.xyz:8081/home/GetAreaByCheckUser?temp="+dateStr,
                map
                , new ResponseListener<Object>(null) {
                    @Override
                    public void onSuccess(Object o, int status) {

                    }

                    @Override
                    public void onError(ResponseError error, int status) {

                    }

                    @Override
                    public void getCookies(Map<String,String> map) {
                        super.getCookies(map);
                        cookieKey ="ASP.NET_SessionId";
                        cookie = map.get(cookieKey);
                        LogUtils.a("cookies = "+map.toString());

                    }
                });
    }

    private void testCookies() {
        HttpRequestManager manager = new HttpRequestManager(this);
        HashMap<String, String> map = new HashMap<>();
//        map.put("AreaID", "29");
        //AreaSelectionForApp
        //home/DeliveryOrdersByUnLoadForApp
        manager.httpRequest("", "http://alp.joeyzh.xyz:8081/home/AreaSelectionForApp",
                map
                , new ResponseListener<JSONArray>(null) {

                    @Override
                    public void onResponse(String s) {
                        super.onResponse(s);
                    }

                    @Override
                    public void onSuccess(JSONArray o, int status) {
                        LogUtils.i("array "+o.toString());

                    }

                    @Override
                    public void onError(ResponseError error, int status) {

                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> header = new HashMap<String, String>();
                        header.put("Cookie", cookieKey + "=" + cookie + "; ");
                        LogUtils.a(header.toString());
                        return header;
                    }
                });
    }
}
