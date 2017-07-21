package com.joey.crash;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


// 用于搜集设备信息和本apk构建信息，添加到上传log和崩溃信息的头部

public class DeviceBuildInfo {

    public static StringBuilder getInfo(Context appContext) {
        Map<String, String> infos = new HashMap<String, String>();
        Build build = new Build();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(build).toString());
            } catch (Exception e) {
            }
        }

        PackageManager pm = appContext.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(appContext.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (Exception e) {
        }

        StringBuilder sb = new StringBuilder();
        if (pi != null) {
            sb.append("version code: " + pi.versionCode + "\n");
            sb.append("version name: " + pi.versionName + "\n");
        }

        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        return sb;
    }
}

