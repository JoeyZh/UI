package com.joey.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;


/**
 * ToastUtils 工具类
 */
public class ToastUtil {

    public final static int TOAST_OF_WARING = 1;
    public final static int TOAST_OF_ERROR = 2;
    public final static int TOAST_OF_SUCCESS = 0;
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object synObj = new Object();

    private ToastUtil() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        showMessage(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        showMessage(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        showMessage(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        showMessage(context, text, duration);
    }

    public static void show(Context context, int resId, Object... args) {
        showMessage(context, String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        showMessage(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        showMessage(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        showMessage(context, String.format(format, args), duration);
    }

    private static void showMessage(final Context context, final CharSequence text,
                                    final int duration) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) {
                    if (toast != null) {
                        toast.setText(text);
                        toast.setDuration(duration);
                    } else {
                        toast = Toast.makeText(context, text, duration);
                    }
                    toast.setGravity(Gravity.BOTTOM, 0, 10);
                    toast.show();
                }
            }
        });
    }

    public static void cancel() {
        handler.removeCallbacksAndMessages(null);
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    // 弹出显示数据框
    public static void ShowToastCommon(Activity mcontext, String strRes, int flag) {
//        LinearLayout layout = (LinearLayout) mcontext.getLayoutInflater().inflate(R.layout.imageview_item,null);
//        TextView text = (TextView)layout.findViewById(R.id.tv_item);
//        text.setText(strRes);
//        ImageView image = (ImageView)layout.findViewById(R.id.iv_item);
        // 对号
//        if(flag == TOAST_OF_SUCCESS){
//            image.setImageResource(R.drawable.succeed);
//            // 叹号
//        }else if(flag == TOAST_OF_WARING){
//            image.setImageResource(R.drawable.warn);
//            // 错误
//        }else if(flag == TOAST_OF_ERROR){
//            image.setImageResource(R.drawable.error);
//        }
//        Toast toast = new Toast(mcontext);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0,0);
//        toast.setView(layout);//setting the view of custom toast layout
//        toast.show();

    }
}
