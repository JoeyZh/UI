package com.joey.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.joey.R;
import com.joey.utils.ScreenUtils;

/**
 * Created by Administrator on 2016/9/23.
 * 进度提示框,遮挡全屏
 */
public class LoadingPopUp extends PopupWindow {

    private Context mContent;
    private ImageView imgLoadingAnim;
    private AnimationDrawable drawable;

    public LoadingPopUp(Context context) {
        this.mContent=context;
        View mPopWindow = View.inflate(context, R.layout.pop_loading, null);
        imgLoadingAnim = (ImageView) mPopWindow.findViewById(R.id.img_progress);
//        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading_bar);
        drawable = (AnimationDrawable) imgLoadingAnim.getBackground();
        this.setContentView(mPopWindow);
        setWidth(ScreenUtils.screenWidth);
        setHeight(ScreenUtils.screenHeight);
//        setHeight(DensityUtil.dip2px(context, 166));
        setFocusable(true);
        // 设置可点击
        setTouchable(true);
        // 设置外部可点击
        setOutsideTouchable(true);
        // 设置外部点击消失// 设置是否允许在外点击使其消失
        setBackgroundDrawable(new BitmapDrawable());

        //垂直出现的动画
//        setAnimationStyle(R.style.mypopwindow_anim_style_2);

        setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }
    public void showPro(View view){
//       this.showAsDropDown(view, -20, 0);
        drawable.start();
        this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    public void dismissPro(){
        drawable.stop();
        this.dismiss();
    }
}
