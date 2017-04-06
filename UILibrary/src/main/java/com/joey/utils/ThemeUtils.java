package com.joey.utils;

import android.content.Context;

import com.joey.R;

/**
 * Created by Joey on 2017/3/30.
 */

public class ThemeUtils {

    private static int mainColorRes = R.color.main_color;
    public static void init(Context context){
        ResourcesUtils.register(context);
        mainColorRes = ResourcesUtils.getColorID("main_color");
    }

    public static int getMainColorRes(){
        return mainColorRes;
    }
}
