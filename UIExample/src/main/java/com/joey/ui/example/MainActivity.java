package com.joey.ui.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.joey.protocol.HttpRequestManager;
import com.joey.protocol.ResponseError;
import com.joey.protocol.ResponseListener;
import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.JAlertDialog;
import com.joey.ui.widget.TopBarLayout;
import com.joey.utils.FileUtil;
import com.joey.utils.LogUtils;
import com.joey.utils.NetWorkUtil;
import com.joey.utils.TimeUtils;
import com.joey.utils.ToastUtil;
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
    View tv5;
    View tv6;
    String cookieKey = "";
    String cookie = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topBarLayout.setTitle("测试");
        // 展示搜索栏，如果不展示，不用调用
        topBarLayout.showSearchBar("输入测试内容");
        topBarLayout.setOnSearchListener(new TopBarLayout.OnSearchListener() {
            @Override
            public void onSearch(String key) {
                ToastUtil.show(getApplicationContext(), "开始搜索" + key);
            }

            @Override
            public void stopSearch() {

            }

            @Override
            public void startSearch() {

            }
        });
//        标题栏左右按钮点击监听
        topBarLayout.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // 右侧按钮监听
                    case R.id.top_right_layout:
                        topBarLayout.onSearch();

                        break;
                    case R.id.top_left_layout:
//                        左侧按钮点击
                        break;
                }
            }
        });
        tv1 = findViewById(R.id.tv_dlg);
        tv2 = findViewById(R.id.tv_list);
        tv3 = findViewById(R.id.tv_upload);
        tv4 = findViewById(R.id.tv_recycler);
        tv5 = findViewById(R.id.tv_search);
        tv6 = findViewById(R.id.tv_calender);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        //标题栏左侧按钮隐藏
        topBarLayout.setLeftResource(-1);
        // 设置标题栏左侧按钮
//        topBarLayout.setLeftResource(R.mipmap.ic_back);
        // 这是提醒
        topBarLayout.showNotice("这是提醒");
    }


    private void showJDialog() {
        View view = View.inflate(this, R.layout.dlg_add_spare_part, null);
        JAlertDialog dialog = new JAlertDialog.Builder(MainActivity.this)
                .setContentView(view)
                .setTitle("dialog")
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
            case R.id.tv_list:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
            case R.id.tv_dlg:
                showJDialog();
                break;
            case R.id.tv_upload:
                testHttpPost();
//                testUpload();
//                testCookies();
                break;
            case R.id.tv_recycler:
                startActivity(new Intent(MainActivity.this, TestRecycleActivity.class));
                break;

            case R.id.tv_search:
                topBarLayout.onSearch();
                break;

            case R.id.tv_calender:
                startActivity(new Intent(MainActivity.this,CalenderActivity.class));
                break;
        }
    }

    private void testFileSize() {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/EAM" + "/knowledge";
        File file = new File(dir).listFiles()[0];
        ToastUtil.show(getApplicationContext(), FileUtil.calcSizeString(file.length()));
    }


    private void testUpload() {
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
        HttpRequestManager.initReponseKey("errcode", "errmsg", "result");
        final HttpRequestManager manager = new HttpRequestManager(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("AccountID", "jwj");
        map.put("Password", "123456");
        String dateStr = TimeUtils.convertDateToStr(new Date(System.currentTimeMillis()), TimeUtils.FORMATTER_DATE);
        manager.httpRequest("", "http://alp.joeyzh.xyz:8081/home/GetAreaByCheckUser?temp=" + dateStr,
                map
                , new ResponseListener<Object>(null) {
                    @Override
                    public void onSuccess(Object o, int status) {

                    }

                    @Override
                    public void onError(ResponseError error, int status) {

                    }

                    @Override
                    public void getCookies(Map<String, String> map) {
                        super.getCookies(map);
                        cookieKey = "ASP.NET_SessionId";
                        cookie = map.get(cookieKey);
                        LogUtils.a("cookies = " + map.toString());

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
                        LogUtils.i("array " + o.toString());

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
