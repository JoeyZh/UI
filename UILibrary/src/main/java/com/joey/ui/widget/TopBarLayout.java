package com.joey.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joey.R;
import com.joey.utils.DensityUtil;

/**
 * 统一的titleBar
 * Created by Joey on 2016/7/26.
 */
public class TopBarLayout extends RelativeLayout {

    private View leftView;
    private View rightView;
    private TextView title;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView imgBtnLeft;
    private ImageView imgBtnRight;
    private SearchBarLayout searchLayout;
    private View rightCustomView;
    private View leftCustomView;
    private int colorRes = -1;
    private int hintColor = -1;
    private View customTitle;
    private ViewGroup titleCenter;
    private TextView tvNotice;
    private View topLayout;
    private OnClickListener mOnClickListener;

    public TopBarLayout(Context context) {
        super(context);
        init();
    }

    public TopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public View getRightView() {
        return rightView;
    }

    public void setTopBar(int title, OnClickListener listener) {
        setTitle(title);
        setOnItemClickListener(listener);
    }

    public void setTopBar(CharSequence title, OnClickListener listener) {
        setTitle(title);
        setOnItemClickListener(listener);

    }

    public void setSearchEditClick(OnClickListener liseer) {
        this.searchLayout.setSearchEditClick(liseer);
    }

    public void showNotice(CharSequence text) {
        tvNotice.setText(text);
        tvNotice.setVisibility(VISIBLE);
    }

    public void showNotice(int text) {
        tvNotice.setText(text);
        tvNotice.setVisibility(VISIBLE);
    }

    public void hideNotice() {
        tvNotice.setVisibility(GONE);
    }

    public void setOnItemClickListener(OnClickListener listener) {
        leftView.setOnClickListener(listener);
        rightView.setOnClickListener(listener);
        tvNotice.setOnClickListener(listener);
    }

    public void setOnSearchListener(OnSearchListener listener) {
        searchLayout.setOnSearchListener(listener);
    }

    private void init() {
        View root = View.inflate(getContext(), R.layout.common_title_layout1, this);
        topLayout = root.findViewById(R.id.top_layout);
        title = (TextView) root.findViewById(R.id.text_title);
        titleCenter = (ViewGroup) root.findViewById(R.id.top_center_layout);
        tvRight = (TextView) root.findViewById(R.id.top_right_text);
        tvLeft = (TextView) root.findViewById(R.id.top_left_text);
        tvNotice = (TextView) root.findViewById(R.id.top_notice_text);
        imgBtnRight = (ImageView) root.findViewById(R.id.top_right_img_btn);
        imgBtnLeft = (ImageView) root.findViewById(R.id.top_left_img_btn);
        leftView = root.findViewById(R.id.top_left_layout);
        rightView = root.findViewById(R.id.top_right_layout);
        searchLayout = (SearchBarLayout) root.findViewById(R.id.top_search_layout);
    }

    public void setTitle(CharSequence text) {
        if (text == null) {
            return;
        }
        setVisibility(VISIBLE);
        title.setVisibility(VISIBLE);
        title.setText(text);
        searchLayout.setVisibility(GONE);
    }

    /**
     * 设置自定义头部标签
     */
    public void setCustomTitleView(View view) {
        if (customTitle != null) {
            titleCenter.removeAllViews();
        }
        customTitle = view;
        titleCenter.addView(customTitle);
    }

    public void setSearchEditBackgroundResource(int resource) {
        this.searchLayout.setBackgroundResource(resource);
    }

    public void setTitle(int text) {
        if (text <= 0) {
            return;
        }
        setVisibility(VISIBLE);
        title.setVisibility(VISIBLE);
        title.setText(text);
        searchLayout.setVisibility(GONE);
    }

    public void setLeftText(CharSequence text) {
        if (null == text) {
            leftView.setVisibility(GONE);
            return;
        }
        leftView.setVisibility(VISIBLE);
        tvLeft.setText(text);
        tvLeft.setVisibility(VISIBLE);
        imgBtnLeft.setVisibility(INVISIBLE);
        if (leftCustomView != null){
            leftCustomView.setVisibility(GONE);
        }
    }

    public void setLeftText(int text) {
        if (text <= 0) {
            leftView.setVisibility(GONE);
            return;
        }
        leftView.setVisibility(VISIBLE);
        tvLeft.setText(text);
        tvLeft.setVisibility(VISIBLE);
        imgBtnLeft.setVisibility(INVISIBLE);
        if (leftCustomView != null) {
            leftCustomView.setVisibility(GONE);
        }
    }

    public void setRightText(CharSequence text) {
        if (null == text) {
            rightView.setVisibility(GONE);
            return;
        }
        rightView.setVisibility(VISIBLE);
        tvRight.setText(text);
        tvRight.setVisibility(VISIBLE);
        imgBtnRight.setVisibility(GONE);
        if (rightCustomView != null) {
            rightCustomView.setVisibility(GONE);
        }
    }

    public void setRightText(int text) {
        if (text <= 0) {
            rightView.setVisibility(GONE);
            return;
        }
        rightView.setVisibility(VISIBLE);
        tvRight.setText(text);
        tvRight.setVisibility(VISIBLE);
        imgBtnRight.setVisibility(GONE);
        if (rightCustomView != null) {
            rightCustomView.setVisibility(GONE);
        }
    }

    public void setLeftResource(int res) {
        tvLeft.setVisibility(View.GONE);
        leftView.setVisibility(VISIBLE);
        if (leftCustomView != null) {
            leftCustomView.setVisibility(GONE);
        }
        if (res >= 0) {
            imgBtnLeft.setImageResource(res);
            imgBtnLeft.setVisibility(VISIBLE);
        } else {
            imgBtnLeft.setVisibility(INVISIBLE);
        }
    }

    public void setRightResource(int res) {

        tvRight.setVisibility(GONE);
        rightView.setVisibility(VISIBLE);
        if (rightCustomView != null) {
            rightCustomView.setVisibility(GONE);
        }
        if (res >= 0) {
            imgBtnRight.setVisibility(VISIBLE);
            imgBtnRight.setImageResource(res);
        } else {
            imgBtnRight.setVisibility(INVISIBLE);
        }
    }

    public void setRightView(View view) {
        if (view == null) {
            return;
        }
        if (rightCustomView != null) {
            ((ViewGroup) rightView).removeView(rightCustomView);
        }
        rightCustomView = view;
        tvRight.setVisibility(GONE);
        imgBtnRight.setVisibility(GONE);
        ((ViewGroup) rightView).addView(rightCustomView);
    }

    public void setLeftView(View view) {
        if (view == null) {
            return;
        }
        if (leftCustomView != null) {
            ((ViewGroup) leftView).removeView(leftCustomView);
        }
        leftCustomView = view;
        tvLeft.setVisibility(GONE);
        imgBtnLeft.setVisibility(GONE);
        ((ViewGroup) leftView).addView(leftCustomView);
    }

    public void searchToggle() {
        this.searchLayout.searchToggle();
    }

    public void setTitleDrawableLeft(int res) {
        Drawable drwLeft = getResources().getDrawable(res);
        drwLeft.setBounds(0, 0, drwLeft.getMinimumWidth(), drwLeft.getMinimumHeight());
        title.setCompoundDrawablePadding(DensityUtil.dip2px(getContext(), 10));
        title.setCompoundDrawables(drwLeft, null, null, null);
    }

    public void onSearch() {
        this.searchLayout.onSearch();
    }

    public Editable getInputText() {
        return searchLayout.getInputText();
    }

    private void showSearchBar() {
        searchLayout.setVisibility(VISIBLE);
    }

    public void showSearchBar(CharSequence hint) {
        this.searchLayout.showSearchBar(hint);
    }

    public void setSearchBarKeyWords(String keyWords) {
        this.searchLayout.setSearchBarKeyWords(keyWords);
    }

    public void stopSearch() {
        this.searchLayout.stopSearch();
    }

    public void disMissSearchBar() {
        title.setVisibility(VISIBLE);
        searchLayout.setVisibility(GONE);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        hintColor = -1;
        try {
            if (getContext() instanceof Activity) {
                DensityUtil.setHintColorResource((Activity) getContext(), resid);
            }
        } catch (Exception e) {
            colorRes = -1;
            return;
        }
        colorRes = resid;
    }

    public void setBackgroundResource() {
        if (colorRes <= 0 && hintColor < 0) {
            return;
        }
        if (hintColor > 0) {
            setBackgroundColor(hintColor);
            return;
        }
        setBackgroundResource(colorRes);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        colorRes = -1;
        try {
            if (getContext() instanceof Activity) {
                DensityUtil.setHintColor((Activity) getContext(), color);
            }
        } catch (Exception e) {
            hintColor = -1;
            return;
        }
        hintColor = color;

    }

    public int getColorRes() {
        return colorRes;
    }

    public interface OnSearchListener {
        void onSearch(String key);

        void stopSearch();

        void startSearch();
    }

}
