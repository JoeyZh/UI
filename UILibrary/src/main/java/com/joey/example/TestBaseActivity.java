package com.joey.example;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.joey.R;
import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.pull_to_refresh.PullToRefreshView;
import com.joey.ui.widget.pull_to_refresh.RefreshListView;

public class TestBaseActivity extends BaseActivity {

    RefreshListView listView;
    Handler handler = new Handler();
    RefreshListView.OnRefreshListener onRefreshListener = new RefreshListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listView.setOnRefreshComplete();
                }
            },3000);
        }
    };
    private String[] arrays = {"1","2","3","4","5"};

    PullToRefreshView.OnHeaderRefreshListener onHeaderRefreshListener = new PullToRefreshView.OnHeaderRefreshListener() {
        @Override
        public void onHeaderRefresh(PullToRefreshView view) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        listView = (RefreshListView)findViewById(R.id.list_view);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrays);
//        listView.setAdapter(adapter);
//        listView.setOnRefreshListener(onRefreshListener);
    }


}
