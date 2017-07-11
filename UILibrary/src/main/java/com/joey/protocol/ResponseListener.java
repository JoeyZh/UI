package com.joey.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.joey.utils.LogUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/22.
 */
public abstract class ResponseListener<T> implements Response.Listener<String> {


    private ResponseHandler handler;

    public ResponseListener(ResponseHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onResponse(String s) {
        convert(s);
    }

    private void convert(String s) {
        LogUtils.a(getClass().getName(), "convert obj = " + s);
        try {
            JSONObject obj = JSON.parseObject(s);
            int status = obj.getInteger(HttpRequestManager.ERROR_CODE_KEY);
            String msg = obj.getString(HttpRequestManager.ERROR_MSG_KEY);
            if (1 == status) {
                T t = (T) obj.get(HttpRequestManager.ERROR_RESULT_KEY);
                LogUtils.a(getClass().getName(), "result = " + t.toString());
                onSuccess(t, status);
                if (handler != null)
                    handler.onSuccess();
                return;
            }
            ResponseError error = new ResponseError(status, msg);
            onError(error, status);
            if (handler != null)
                handler.onError();
            return;
        } catch (Exception e) {
            ResponseError error = new ResponseError(ResponseError.ERROR_BY_PARSE, e.getMessage());
            error.setJson(s);
            onError(error, -1);
            if (handler != null)
                handler.onError();
            LogUtils.e(getClass().getName(), "error =" + e.getMessage());
            return;
        }
    }

    public void onLoading(long total, long current, boolean isUploading) {

    }

    public final void parseResponseHeader(Map<String, String> headers) {
        final String SET_COOKIE_KEY = "Set-Cookie";
        if (headers.containsKey(SET_COOKIE_KEY)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if ((cookie.length()) > 0 && (!cookie.contains("saeut"))) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                String key = splitSessionId[0];
                getCookies(key,cookie);
            }

        }
    }

    public void getCookies(String key,String value){

    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    public abstract void onSuccess(T t, int status);

    public abstract void onError(ResponseError error, int status);

}
