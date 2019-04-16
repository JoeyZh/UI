package com.joey.protocol;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

    protected static String ERROR_CODE_KEY = "resultCode";
    protected static String ERROR_MSG_KEY = "resultMessage";
    protected static String ERROR_RESULT_KEY = "result";


    private NetUtils mNetUtils;

    private HttpUtils httpUtils;
    private Context context;
    // 标记全局是否离线操作，用此拦截所有网络请求
    private static boolean offline;
    private static String domainUrl = "";

    private static Map<String, String> header;
    private final String SESSION_KEY = "ASP.NET_SessionId";

    public HttpRequestManager(Context context) {
        this.context = context;
        mNetUtils = NetUtils.getInstance(context);
        httpUtils = new HttpUtils();
    }

    public static void setDomainUrl(String url) {
        domainUrl = url;
    }

    /**
     * 初始化解析json的关键key值
     * json格式为 {"codeKey":"","msgKey":"","resultKey":{}}
     *
     * @param codeKey
     * @param msgKey
     * @param resultKey
     */
    public static void initReponseKey(String codeKey, String msgKey, String resultKey) {
        ERROR_CODE_KEY = codeKey;
        ERROR_MSG_KEY = msgKey;
        ERROR_RESULT_KEY = resultKey;
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
        LogUtils.a("url：" + url + "[[[+ map: " + params.toString());
        StringRequest request = new StringRequest(method,
                url,
                responseListener,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ResponseError error;
                        if (volleyError == null || volleyError.networkResponse == null) {
                            error = new ResponseError(ResponseError.ERRPR_NO_RESPONCE, "主人，服务器正在偷懒！");
                        } else {
                            error = new ResponseError(volleyError.networkResponse.statusCode, "" + volleyError.networkResponse.statusCode);
                        }
                        responseListener.onError(error, ResponseError.ERRPR_BY_NET);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                responseListener.parseResponseHeader(response.headers);
                return super.parseNetworkResponse(response);

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return responseListener.getHeaders();
            }
        };
        mNetUtils.addToRequestQueue(request);
    }

    public <T> void httpRequest(String url, final HashMap<String, String> params, final ResponseListener<T> responseListener) {
        httpRequest(domainUrl, url, params, responseListener);

    }

    public <T> void upLoad(String domainUrl, String url, HashMap<String, String> mapParams, final ResponseListener<T> responseListener, File[] files) {
        if (offline) {
            ResponseError error = new ResponseError(ResponseError.ERROR_OFF_LINE, "离线登录");
            responseListener.onError(error, 1);
            return;
        }
        if (files == null || files.length == 0) {
            LogUtils.a("url：" + url + "[[[+ map: " + mapParams.toString());
            httpRequest(domainUrl, url, mapParams, responseListener);
            return;
        }
        url = domainUrl + url;
        RequestParams params = new RequestParams();
        MultipartEntity entity = new MultipartEntity();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                FileBody body = new FileBody(files[i]);
                entity.addPart(new FormBodyPart("file", body));
                LogUtils.a("filePart-->" + files[i]);
            }
        }
        try {
            for (String key : mapParams.keySet()) {
                entity.addPart(key, new StringBody(mapParams.get(key)));
            }
        } catch (Exception e) {

        }
        params.setBodyEntity(entity);
        LogUtils.a("url：" + url + "[[[+ map: " + mapParams.toString());
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
        upLoad(domainUrl, url, mapParams, responseListener, files);
    }


}
