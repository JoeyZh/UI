package com.joey.crash;

import java.lang.Thread.UncaughtExceptionHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.widget.Toast;
import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;


public class CrashHandler implements UncaughtExceptionHandler {

    static CrashHandler crash;

    UncaughtExceptionHandler defaultHandler;
    String crashLogPath = null;
    private Context context;
    public static final String ACTION_CRASH_OCCUR = "com.joey.crash.EVENT";

    public static CrashHandler getInstance() {
        if (crash == null)
            crash = new CrashHandler();
        return crash;
    }

    public void init(String crashDir, Context context) {
        this.context = context;
        prepareCrashLogPath(crashDir);
        if (crashLogPath != null) {
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);

            findAndSendCrashLog();
        }
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(thread, ex) && defaultHandler != null) {
            defaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    void prepareCrashLogPath(String crashDir) {
        File file = null;
        try {
            file = new File(crashDir);
        } catch (Exception e) {
        }

        if (file == null) {
            return;
        }

        crashLogPath = file.getAbsolutePath() + "/crash_log";
        try {
            File log = new File(crashLogPath);
            if (!log.exists())
                log.mkdirs();
        } catch (Exception e) {
            crashLogPath = null;
        }

    }

    boolean handleException(Thread thread, Throwable ex) {
        if (ex == null) return false;
        ex.printStackTrace();//打印到终端,方便开发时调试.

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                context.sendBroadcast(new Intent(ACTION_CRASH_OCCUR));
                Toast toast = Toast.makeText(context, context.getPackageName() + " crashed", Toast.LENGTH_LONG);
                toast.show();
                Looper.loop();
            }
        }.start();

        //先保存,下次重启时再上传.
        String fname = saveCrashAndDeviceInfo2File(ex);
        return true;
    }

    String saveCrashAndDeviceInfo2File(Throwable ex) {
        StringBuilder sb = DeviceBuildInfo.getInfo(context);

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String time = formatter.format(new Date());
            String fileName = "crash_log_" + time + "-" + ".txt";
            FileOutputStream fos = new FileOutputStream(crashLogPath + "/" + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }

    void findAndSendCrashLog() {
        new Thread(new Runnable() {
            public void run() {
                File appFile = new File(crashLogPath);
                File[] files = appFile.listFiles();
                for (File file : files) {
                    if (file.getName().startsWith("boxasst-crash-")) {
                        //Report.file2Server(file.getAbsolutePath());
                    }
                }
            }
        }).start();
    }
}

