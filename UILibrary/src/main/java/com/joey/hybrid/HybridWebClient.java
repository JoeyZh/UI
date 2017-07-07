package com.joey.hybrid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.joey.protocol.ResponseHandler;
import com.joey.utils.LogUtils;

/**
 * Created by Administrator on 2016/11/16.
 */
public class HybridWebClient extends WebViewClient {

    private ResponseHandler responseHandler;
    private Activity activity;
    private HybridActionListener actionListener = new HybridActionListener<String>() {
        @Override
        public void action(int action, String s) {
            String packageName = activity.getPackageName();
            Class<?> goodsDetailCls = null;
            try {
                goodsDetailCls = Class.forName(packageName + ".user.goods.GoodsDetailActivity");
            } catch (Exception e) {
                return;
            }
            Intent intent = new Intent(activity, goodsDetailCls);
            intent.putExtra("goods_id", s);
            activity.startActivity(intent);
        }
    };

    public static String parseGoodsId(String url) {
        return HybridParser.parseGoodsId(url);
    }

    public HybridWebClient(Activity activity, ResponseHandler handler) {
        this.activity = activity;
        responseHandler = handler;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.a(" 跳转的url = " + url);
        if (url.indexOf(HybridParser.GOTO_DETAIL_URL) > 0) {
            if (responseHandler != null)
                responseHandler.onSuccess();
            String goodsId = HybridParser.parseGoodsId(url);
            if (actionListener != null) {
                actionListener.action(HybridAction.ACTION_GO_TO_GOODS_DETAILS, goodsId);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (responseHandler != null)
            responseHandler.onLoading();
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (responseHandler != null)
            responseHandler.onSuccess();
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (responseHandler != null)
            responseHandler.onError();
        super.onReceivedError(view, errorCode, description, failingUrl);
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//        super.onReceivedSslError(view, handler, error);
        if (responseHandler != null)
            responseHandler.onError();
        handler.proceed();
    }

    public interface HybridActionListener<T> {
        void action(int action, T t);
    }

    public class HybridAction {
        public static final int ACTION_GO_TO_GOODS_DETAILS = 0;
    }
}
