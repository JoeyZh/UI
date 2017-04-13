package com.joey.hybrid;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/1/4.
 */

public class HybridWebView extends WebView {

    OnWebTitleListener listener;

    public HybridWebView(Context context) {
        super(context);
        initSettings();
    }

    public HybridWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSettings();
    }

    public HybridWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSettings();
    }

    public void setOnTitleListener(OnWebTitleListener listener) {
        this.listener = listener;
    }

    private void initSettings() {
        WebSettings settings = getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (null != listener)
                    listener.onTitle(title);
            }
        };
        setWebChromeClient(chromeClient);
    }


}
