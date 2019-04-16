package com.joey.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.joey.R;
import com.joey.utils.LogUtils;

public class SearchBarLayout extends FrameLayout {
    private EditText searchEdit;
    private ImageView searchBtn;
    private boolean onSearching;
    private TopBarLayout.OnSearchListener searchListener;

    public boolean isOnSearching() {
        return this.onSearching;
    }

    public void setOnSearching(boolean onSearching) {
        this.onSearching = onSearching;
    }

    public SearchBarLayout(Context context) {
        super(context);
        this.init(context);
    }

    public SearchBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public SearchBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        View root = View.inflate(context, R.layout.layout_search_bar, this);
        this.searchBtn = (ImageView)root.findViewById(R.id.top_search_btn);
        this.searchEdit = (EditText)root.findViewById(R.id.top_search_edit);
        this.searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SearchBarLayout.this.startSearch();
            }
        });
        this.searchEdit.setImeOptions(3);
        this.searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId != 3 && (event == null || event.getKeyCode() != 66)) {
                    return false;
                } else {
                    SearchBarLayout.this.onSearch();
                    return true;
                }
            }
        });
    }

    public void setOnSearchListener(TopBarLayout.OnSearchListener listener) {
        this.searchListener = listener;
    }

    public void setSearchEditClick(OnClickListener liseer) {
        this.showSearchBar();
        this.searchEdit.setFocusable(false);
        this.searchEdit.setOnClickListener(liseer);
        this.searchBtn.setOnClickListener(liseer);
    }

    public void setSearchEditBackgroundResource(int resource) {
        this.searchEdit.setBackgroundResource(resource);
    }

    private void startSearch() {
        LogUtils.i("startSearch onSearching = " + this.onSearching);
        if(this.searchListener != null) {
            this.searchListener.startSearch();
        }

        if(!this.onSearching) {
            this.onSearching = true;
            this.showSearchBar();
        }
    }

    public void onSearch() {
        String text = this.searchEdit.getText().toString().trim();
        if(this.searchListener != null) {
            this.searchListener.onSearch(text);
        }

    }

    public Editable getInputText() {
        return this.searchEdit.getText();
    }

    private void showSearchBar() {
        this.setVisibility(VISIBLE);
    }

    public void showSearchBar(CharSequence hint) {
        this.showSearchBar();
        this.searchEdit.setHint(hint);
    }

    public void setSearchBarKeyWords(String keyWords) {
        this.showSearchBar();
        this.searchEdit.setText(keyWords);
    }

    public void stopSearch() {
        LogUtils.i("stopSearch onSearching = " + this.onSearching);
        if(this.onSearching) {
            this.onSearching = false;
            this.searchEdit.setText("");
            if(this.searchListener != null) {
                this.searchListener.stopSearch();
            }

            InputMethodManager imm = (InputMethodManager)this.getContext().getApplicationContext().getSystemService("input_method");
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void searchToggle() {
        LogUtils.i("searchToggle onSearching = " + this.onSearching);
        if(this.onSearching) {
            this.stopSearch();
        } else {
            this.startSearch();
        }
    }
}