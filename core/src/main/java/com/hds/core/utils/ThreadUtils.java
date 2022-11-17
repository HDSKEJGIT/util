package com.hds.core.utils;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {
    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnUiThread(Runnable runnable){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    public static void runOnUiThread(Runnable runnable, long upTimeMillis) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postAtTime(runnable, upTimeMillis);
    }

    public static void runOnUIThread(Runnable runnable, long delayMills) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, delayMills);
    }
}
