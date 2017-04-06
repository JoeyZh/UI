package com.joey.protocol;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2016/12/1.
 * http请求的管理工具
 */
public class HttpRequestManager {

    private NetUtils mNetUtils;

    public HttpRequestManager(Context context) {
        mNetUtils = NetUtils.getInstance(context);
    }

    public <T> void httpRequest(String url, final HashMap<String, String> params,ResponseListener<T> responseListener) {
        int method = Request.Method.POST;
        if (params == null || params.isEmpty()) {
            method = Request.Method.GET;
        }
        StringRequest request = new StringRequest(method,
                url,
                responseListener,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        mNetUtils.addToRequestQueue(request);
    }
}
