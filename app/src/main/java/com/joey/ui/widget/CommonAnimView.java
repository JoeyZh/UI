package com.joey.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.joey.R;

/**
 * Created by Administrator on 2016/11/4.
 */
public class CommonAnimView extends ImageView {

    private AnimationDrawable drawable;

    public CommonAnimView(Context context) {
        super(context);
        init();
    }

    public CommonAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.second_step_animation);
        drawable = (AnimationDrawable) getBackground();
    }

    public void start() {
        if (drawable != null) {
            drawable.start();
        }
    }

    public void stop() {
        if (drawable != null) {
            drawable.stop();
        }
    }

}
