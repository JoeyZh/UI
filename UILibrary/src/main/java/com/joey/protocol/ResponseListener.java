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
    private String resultKey;

    public ResponseListener(ResponseHandler handler) {
        this.handler = handler;
        resultKey = HttpRequestManager.ERROR_RESULT_KEY;
    }

    public ResponseListener(ResponseHandler handler, String paramKey) {
        this.handler = handler;
        resultKey = paramKey;
    }


    @Override
    public void onResponse(String s) {
        convert(s);
    }

    private void convert(String s) {
        ResponseError error = null;
        try {
            LogUtils.a(getClass().getName(), "convert obj = " + s);
            JSONObject obj = JSON.parseObject(s);
            int status = obj.getInteger(HttpRequestManager.ERROR_CODE_KEY);
            String msg = obj.getString(HttpRequestManager.ERROR_MSG_KEY);
            error = new ResponseError(status, msg);
            if (1 == status || 0 == status) {
                T t = (T) obj.get(resultKey);
                LogUtils.a(getClass().getName(), "result = " + t.toString());
                onSuccess(t, status);
                if (handler != null)
                    handler.onSuccess();
                return;
            }
            onError(error, status);
            if (handler != null)
                handler.onError();
            return;
        } catch (Exception e) {
            if (error == null) {
                error = new ResponseError(ResponseError.ERROR_BY_PARSE, e.getMessage());
            }
            error.setJson(s);
            onError(error, ResponseError.ERROR_BY_PARSE);
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
                HashMap<String, String> map = new HashMap<>();

                for (int i = 0; i < splitCookie.length; i++) {
                    String[] splitSessionId = splitCookie[i].split("=");
                    if (splitSessionId.length <= 1) {
                        continue;
                    }
                    map.put(splitSessionId[0], splitSessionId[1]);
                }
                getCookies(map);
            }

        }
    }

    public void getCookies(Map<String, String> map) {


    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    public abstract void onSuccess(T t, int status);

    public abstract void onError(ResponseError error, int status);

}
