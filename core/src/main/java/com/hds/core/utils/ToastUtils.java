package com.hds.core.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtils
 */
public class ToastUtils {
    private static Toast toast = null; //Toast的对象！

    public static void showShort(Context mContext, String id) {
        if (toast == null) {
            toast = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }

    public static void showLong(Context mContext, String id){
        if (toast == null) {
            toast = Toast.makeText(mContext, id, Toast.LENGTH_LONG);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
}
