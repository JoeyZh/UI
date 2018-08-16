package com.joey.ui.example;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.refresh.RefreshRecycleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.GuoBiMarketAdapter;

public class TestRecycleActivity extends BaseActivity {

    private RefreshRecycleView recycleViewFresh;
    private GuoBiMarketAdapter adapterRecycler;
    private String[] array = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private int page = 0;
    private Handler handler = new Handler();
    private Runnable refreshCompleteRunnable = new Runnable() {
        @Override
        public void run() {
            recycleViewFresh.refreshComplete();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycle);
        initView();
        topBarLayout.setTitle("RecyclerTest");
        recycleViewFresh = (RefreshRecycleView) findViewById(R.id.refresh_cycle);
        adapterRecycler = new GuoBiMarketAdapter(this, array);
        adapterRecycler.setViewType(R.layout.item_test);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleViewFresh.setLayoutManager(layoutManager);
        recycleViewFresh.setLoadingListener(new RefreshRecycleView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下拉刷新时的操作
                page = 0;
                //refresh data here
                getDataFromNet();
                handler.removeCallbacks(refreshCompleteRunnable);
                handler.postDelayed(refreshCompleteRunnable, 2000);
            }

            @Override
            public void onLoadMore() {
//                page++;上啦加载更多时的操作
                page += 10;
                getDataFromNet();
                handler.removeCallbacks(refreshCompleteRunnable);
                handler.postDelayed(refreshCompleteRunnable, 2000);

            }
        });
        recycleViewFresh.setAdapter(adapterRecycler);
//        onLoading();
        getDataFromNet();
    }

    @Override
    protected void reload() {
//        onLoading();
        getDataFromNet();
    }

    protected void getDataFromNet() {

    }

    private void initView() {

    }


}
