package com.joey.ui.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.joey.protocol.ResponseHandler;
import com.joey.R;
import com.joey.ui.widget.LoadingDialog;
import com.joey.ui.widget.LoadingPopUp;
import com.joey.ui.widget.TopBarLayout;
import com.joey.utils.ScreenUtils;
import com.joey.utils.ThemeUtils;

/**
 * BaseActivity
 * Created by Joey on 2016/3/3 0003.
 */
public abstract class BaseActivity extends FragmentActivity implements ResponseHandler {
    private FrameLayout mFl_container;
    private Button btn_reload;
    private RelativeLayout rl_loading, rl_error_cart;
    private LoadingDialog loadingDlg;
    LoadingPopUp loadingPro;
    protected TopBarLayout topBarLayout;
    // 网络请求
    private ImageView imgLoadingAnim;
    private AnimationDrawable drawable;
    private LinearLayout mBaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getActivityManager().pushActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initLoadingDlg();
        initSuperView();
    }

    private void initSuperView() {
        mBaseRoot = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_base, null);
        //标题栏
        topBarLayout = (TopBarLayout) mBaseRoot.findViewById(R.id.top_layout);
        topBarLayout.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.top_left_layout) {
                    onBackPressed();
                    return;
                }
            }
        });
        topBarLayout.setVisibility(View.GONE);
        topBarLayout.setBackgroundResource(ThemeUtils.getMainColorRes());
        View.OnClickListener reloadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        };
        mBaseRoot.findViewById(R.id.page_iv).setOnClickListener(reloadListener);
        btn_reload = (Button) mBaseRoot.findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener(reloadListener);
        rl_loading = (RelativeLayout) mBaseRoot.findViewById(R.id.rl_loading);
        rl_error_cart = (RelativeLayout) mBaseRoot.findViewById(R.id.rl_error_layout);
        mFl_container = (FrameLayout) mBaseRoot.findViewById(R.id.fl_container);
        imgLoadingAnim = (ImageView) mBaseRoot.findViewById(R.id.img_progress);
//        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading_bar);
        drawable = (AnimationDrawable) imgLoadingAnim.getBackground();
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(this, layoutResID, null);
        if (view != null) {
            view.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mFl_container.removeAllViews();
            mFl_container.addView(view);
        }
        super.setContentView(mBaseRoot);
        initViews();
        initData();
    }

    @Override
    public void setContentView(View view) {
        if (view != null) {
            view.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mFl_container.removeAllViews();
            mFl_container.addView(view);
        }
        super.setContentView(mBaseRoot);
        initViews();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (topBarLayout.getVisibility() == View.VISIBLE)
            topBarLayout.setBackgroundResource();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getActivityManager().popActivity(this);
    }

    public void resetScreen() {
        //屏幕高宽
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        ScreenUtils.screenWidth = windowManager.getDefaultDisplay().getWidth();
        ScreenUtils.screenHeight = windowManager.getDefaultDisplay().getHeight();
        // 方法2
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ScreenUtils.dentity = dm.density;
    }

    private void initLoadingDlg() {
        loadingDlg = new LoadingDialog(this);
        loadingPro = new LoadingPopUp(this);
    }

    public void showDialog(boolean cancel) {
        loadingDlg.setCancelable(cancel);
        loadingDlg.showDialog();
    }

    public void showDialog(boolean cancel, String message) {
        loadingDlg.setCancelable(cancel);
        loadingDlg.showDialog(message);
    }

    public void showDialog(boolean cancel, int message) {
        loadingDlg.setCancelable(cancel);
        loadingDlg.showDialog(message);
    }

    public void showDialog() {
        loadingDlg.showDialog();
    }

    public void showDialog(String message) {
        loadingDlg.showDialog(message);
    }

    public void showDialogMessage(int message) {
        loadingDlg.showDialog(message);
    }

    public void dismissDialog() {
        loadingDlg.dismissDialog();
        loadingPro.dismissPro();
    }

    protected void initData() {

    }

    protected void initViews() {

    }

    /**
     * 网络请求失败提示
     */
    protected void reload() {

    }

    public void showLoading() {
        loadingPro.showPro(topBarLayout);
    }

    ;

    /**
     * 子类的联网操作
     */
    protected void getDataFromNet() {

    }

    public void onError() {
        rl_loading.setVisibility(View.GONE);
        rl_error_cart.setVisibility(View.VISIBLE);
        mFl_container.setVisibility(View.GONE);
        drawable.stop();
        dismissDialog();
    }

    public void onSuccess() {
        rl_loading.setVisibility(View.GONE);
        rl_error_cart.setVisibility(View.GONE);
        mFl_container.setVisibility(View.VISIBLE);
        drawable.stop();
        dismissDialog();
    }

    public void onLoading() {
        rl_loading.setVisibility(View.VISIBLE);
        rl_error_cart.setVisibility(View.GONE);
        mFl_container.setVisibility(View.GONE);
        drawable.stop();
        drawable.start();
    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//
//    }
}
