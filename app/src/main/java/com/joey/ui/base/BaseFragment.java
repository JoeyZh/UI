package com.joey.ui.base;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.joey.protocol.ResponseHandler;
import com.joey.R;
import com.joey.ui.widget.LoadingDialog;
import com.joey.ui.widget.TopBarLayout;

/**
 * Created by Administrator on 2016/8/31.
 */
public abstract class BaseFragment extends Fragment implements ResponseHandler {

    protected Activity mActivity;
    private RelativeLayout fmRlLoading, fmRlError;
    private Button fmRlErrorTxt;
    protected LoadingDialog loadingDlg;
    protected TopBarLayout topBarLayout;
    private ImageView imgLoadingAnim;
    private AnimationDrawable drawable;
    /**
     * 自定义布局
     */
    private ViewGroup customContent;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDlg = new LoadingDialog(getActivity());
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_base, container, false);
        mActivity = getActivity();
        initViewUI(view);
        return view;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgLoadingAnim.setVisibility(View.VISIBLE);
        // 判断是否有自定义view
        View content = getChild();
        if (content != null) {
            customContent.removeAllViews();
            customContent.addView(content);
        }
        loadLocalData();
    }

    private void initViewUI(View view) {
        fmRlLoading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        fmRlError = (RelativeLayout) view.findViewById(R.id.rl_error_layout);
        //重新加载按钮
        fmRlErrorTxt = (Button) view.findViewById(R.id.btn_reload);
        fmRlErrorTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        //标题栏
        topBarLayout = (TopBarLayout) view.findViewById(R.id.top_layout);
        topBarLayout.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.top_left_layout:
                        getActivity().onBackPressed();
                        break;
                }
            }
        });
        //加载小人动画
        imgLoadingAnim = (ImageView) view.findViewById(R.id.img_progress);
//        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading_bar);
        drawable = (AnimationDrawable) imgLoadingAnim.getBackground();
        // 自定义内容布局
        customContent = (ViewGroup) view.findViewById(R.id.fl_container);
    }

    /**
     * 自定义布局
     *
     * @return
     */
    protected View getChild() {
        return null;
    }

    /**
     * 是否显示加载动画
     *
     * @return 表示是否加载小人动画，
     * true:表示默认加载小人动画
     * false: 表示默认加载progressbar
     */
    protected boolean showLoadingAnim() {
        return true;
    }

    public void onError() {
        fmRlLoading.setVisibility(View.GONE);
        fmRlError.setVisibility(View.VISIBLE);
        drawable.stop();
    }

    public void onSuccess() {
        fmRlLoading.setVisibility(View.GONE);
        fmRlError.setVisibility(View.GONE);
        drawable.stop();
    }

    public void onLoading() {
        fmRlLoading.setVisibility(View.VISIBLE);
        fmRlError.setVisibility(View.GONE);
        drawable.stop();
        drawable.start();
    }

    /**
     * 恢复标题栏颜色
     */
    public void revertTopBarColor() {
        if (topBarLayout != null)
            topBarLayout.setBackgroundResource();
    }

    /**
     * 加载失败时的点击操作
     */
    abstract protected void reload();

    protected void loadLocalData() {

    }

}
