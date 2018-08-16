package com.joey.ui.example;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.joey.ui.base.BaseActivity;
import com.joey.ui.widget.pull_to_refresh.PullToRefreshView;
import com.joey.ui.widget.pull_to_refresh.RefreshListView;

public class ListActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topBarLayout.setTitle("ListTest");
        setContentView(R.layout.eg_activity_list);
        listView = (RefreshListView)findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrays);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(onRefreshListener);
    }


}
