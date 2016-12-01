package com.joey.example;

import android.os.Bundle;

import com.joey.R;
import com.joey.ui.base.BaseActivity;

public class TestBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
