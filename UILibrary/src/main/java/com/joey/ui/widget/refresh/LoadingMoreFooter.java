package com.joey.ui.widget.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joey.R;
import com.joey.ui.widget.pull_to_refresh.FirstStepView;

/**
 * Created by Administrator on 2016/9/5.
 */
public class LoadingMoreFooter extends LinearLayout {


    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    public View mFooterView;
    private TextView mFooterTextView;
    /**
     * footer view image
     */
    private FirstStepView mFooterImageView;
    /**
     * footer progress bar
     */
    private ImageView imgAnimalExp;

    private int mFooterViewHeight = 100;
    private AnimationDrawable drawable_foot;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_footer, null);
        mFooterView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        setGravity(Gravity.CENTER);
        mFooterImageView = (FirstStepView) mFooterView.findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_load_text);
        imgAnimalExp = (ImageView) mFooterView.findViewById(R.id.pull_to_load_progress);
        imgAnimalExp.setVisibility(View.VISIBLE);
        drawable_foot = (AnimationDrawable) imgAnimalExp.getBackground();
        // footer layout
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        addView(mFooterView);
        mFooterTextView.setText(R.string.loading);
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                mFooterTextView.setText(R.string.loading);
                drawable_foot.start();
                imgAnimalExp.setVisibility(View.VISIBLE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
                imgAnimalExp.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_NOMORE:
//                没有更多
                mFooterTextView.setText(R.string.loaded_no_more);
                imgAnimalExp.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
