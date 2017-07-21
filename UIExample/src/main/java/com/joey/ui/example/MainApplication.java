package com.joey.ui.example;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.joey.crash.CrashHandler;
import com.joey.utils.FileUtil;
import com.joey.utils.SharedPreferenceUtils;
import com.joey.utils.ThemeUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MainApplication extends Application {
    private static MainApplication mAppInstance;
    //SD卡根路径
    private static final String mSdRootPath = Environment.getExternalStorageDirectory().getPath();

    private Context mAppContext;

    public static MainApplication getInstance() {
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        mAppInstance = this;
        initSDKS();
        SharedPreferenceUtils.getInstance().init(this, "test");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * TODO 加载三方SDK
     */
    private void initSDKS() {
        ThemeUtils.init(this);
        CrashHandler.getInstance().init(mSdRootPath + "/crash", this);
    }
}
