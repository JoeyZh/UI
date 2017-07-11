package com.joey.ui.widget.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joey.R;
import com.joey.ui.widget.pull_to_refresh.FirstStepView;

import java.util.Date;

/**
 * RecycleView头部刷新布局
 */
public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {

    private View mContainer;
    private FirstStepView refreshAnimalView;
    //下拉刷新状态，刷新时间
    private TextView txtStatus,txtTime;
    /**
     * header progress bar
     */
    private ImageView  animalDrawableImg;
    private AnimationDrawable      animalDrawable;
    private int mState = STATE_NORMAL;
    public int mMeasuredHeight;


    public ArrowRefreshHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        mContainer = LayoutInflater.from(getContext()).inflate(
                R.layout.refresh_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        // header view
        refreshAnimalView = (FirstStepView) mContainer.findViewById(R.id.pull_to_refresh_image);
        txtStatus = (TextView) mContainer.findViewById(R.id.pull_to_refresh_text);
        txtTime = (TextView) mContainer.findViewById(R.id.pull_to_refresh_updated_at);
        animalDrawableImg = (ImageView) mContainer.findViewById(R.id.pull_to_refresh_progress);
        animalDrawableImg.setVisibility(View.VISIBLE);
        animalDrawable = (AnimationDrawable) animalDrawableImg.getBackground();

        measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();

    }




    public void setState(int state) {
        if (state == mState) {
            return;
        }
        if (state == STATE_REFRESHING) {	// 显示进度
//            refreshAnimalView.clearAnimation();
            animalDrawable.start();
        } else if(state == STATE_DONE) {
            animalDrawable.stop();
        } else {	// 显示箭头图片
            animalDrawable.stop();
        }

        switch(state){
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    animalDrawable.stop();
                }
                txtStatus.setText("下拉刷新");
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
//                    animalDrawable.start();
                    txtStatus.setText("松开刷新");
                }
                break;
            case STATE_REFRESHING:
                txtStatus.setText("正在刷新...");
                break;
            case    STATE_DONE:
                txtStatus.setText("刷新完成");
                break;
            default:
        }
        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void refreshComplete(){
        txtTime.setText(friendlyTime(new Date()));
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                reset();
            }
        }, 200);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public void onMove(float delta) {
        if(getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                }else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if(getVisibleHeight() > mMeasuredHeight &&  mState < STATE_REFRESHING){
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <=  mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public static String friendlyTime(Date time) {
        //获取time距离当前的秒数
        int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);

        if(ct == 0) {
            return "刚刚";
        }

        if(ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if(ct >= 60 && ct < 3600) {
            return Math.max(ct / 60,1) + "分钟前";
        }
        if(ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if(ct >= 86400 && ct < 2592000){ //86400 * 30
            int day = ct / 86400 ;
            return day + "天前";
        }
        if(ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }

}