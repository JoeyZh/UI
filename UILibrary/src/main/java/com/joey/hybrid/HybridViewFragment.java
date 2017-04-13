package com.joey.hybrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.joey.protocol.ResponseHandler;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class HybridViewFragment extends Fragment {
    private String good_desc;
    private HybridWebView we;
    private HybridWebClient client;
    private OnWebTitleListener titleListener;

    public static HybridViewFragment newInstance(String url, boolean hasFlag) {
        HybridViewFragment fragment = new HybridViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("handler", hasFlag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        good_desc = getArguments().getString("url");
        boolean flag = getArguments().getBoolean("handler");
        ResponseHandler handler = null;
        if (flag && getActivity() instanceof ResponseHandler) {
            handler = (ResponseHandler) getActivity();
        }
        client = new HybridWebClient(getActivity(), handler);
        we = new HybridWebView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        we.setLayoutParams(params);
        we.setWebViewClient(client);
        we.loadUrl(good_desc);
        we.setOnTitleListener(titleListener);
        we.getSettings().setJavaScriptEnabled(true);
        we.getSettings().setDomStorageEnabled(true);
        return we;
    }

    public void setOnTitleListener(OnWebTitleListener listener) {
        titleListener = listener;
    }

    public void reload() {
        we.loadUrl(good_desc);
    }

}
