package com.joey.ui.example;

import android.app.Application;
import android.content.Context;

import com.joey.utils.SharedPreferenceUtils;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MainApplication extends Application {
    private static MainApplication mAppInstance;

    private Context mAppContext;
    public static MainApplication getInstance(){
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        mAppInstance = this;
        initSDKS();
        SharedPreferenceUtils.getInstance().init(this,"test");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * TODO 加载三方SDK
     */
    private void initSDKS(){

    }
}
