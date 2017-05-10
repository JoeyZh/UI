package com.joey.protocol;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.joey.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.multipart.FormBodyPart;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2016/12/1.
 * http请求的管理工具
 */
public class HttpRequestManager {

    private NetUtils mNetUtils;

    private HttpUtils httpUtils;
    private Context context;
    // 标记全局是否离线操作，用此拦截所有网络请求
    private static boolean offline;
    private static String domainUrl;

    public HttpRequestManager(Context context) {
        this.context = context;
        mNetUtils = NetUtils.getInstance(context);
        httpUtils = new HttpUtils();
    }

    public static void setDomainUrl(String url) {
        domainUrl = url;
    }

    public static void setOffline(boolean isOffline) {
        offline = isOffline;
    }

    public <T> void httpRequest(String domainUrl, String url, final HashMap<String, String> params, final ResponseListener<T> responseListener) {
        if (offline) {
            ResponseError error = new ResponseError(ResponseError.ERROR_OFF_LINE, "离线登录");
            responseListener.onError(error, 1);
            return;
        }
        url = domainUrl + url;
        int method = Request.Method.POST;
        if (params == null || params.isEmpty()) {
            method = Request.Method.GET;
        }
        LogUtils.a("map: " + params.toString());
        StringRequest request = new StringRequest(method,
                url,
                responseListener,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ResponseError error = new ResponseError(ResponseError.ERRPR_BY_NET, "亲，服务器在偷懒，请重试");
                        responseListener.onError(error, ResponseError.ERRPR_BY_NET);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        mNetUtils.addToRequestQueue(request);
    }

    public <T> void httpRequest(String url, final HashMap<String, String> params, final ResponseListener<T> responseListener) {
        httpRequest("", url, params, responseListener);

    }

    public <T> void upLoad(String domainUrl, String url, HashMap<String, String> mapParams, final ResponseListener<T> responseListener, File[] files) {
        if (offline) {
            ResponseError error = new ResponseError(ResponseError.ERROR_OFF_LINE, "离线登录");
            responseListener.onError(error, 1);
            return;
        }
        RequestParams params = new RequestParams();
        MultipartEntity entity = new MultipartEntity();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                FileBody body = new FileBody(files[i]);
                entity.addPart(new FormBodyPart("file", body));
                LogUtils.e("-->" + files[i]);
            }
        }
        try {
            for (String key : mapParams.keySet()) {
                entity.addPart(key, new StringBody(mapParams.get(key)));
            }
        } catch (Exception e) {

        }
        params.setBodyEntity(entity);
        LogUtils.a("map: " + params.toString());
        httpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            String s = new String(responseInfo.result);
                            responseListener.onResponse(s);

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        responseListener.onLoading(total, current, isUploading);
                    }


                    @Override
                    public void onFailure(HttpException e, String s) {
                        ResponseError error = new ResponseError(ResponseError.ERRPR_BY_NET, e.getMessage());
                        responseListener.onError(error, ResponseError.ERRPR_BY_NET);
                    }
                }
        );
    }

    public <T> void upLoad(String url, HashMap<String, String> mapParams, final ResponseListener<T> responseListener, File[] files) {
        upLoad("", url, mapParams, responseListener, files);
    }


}
