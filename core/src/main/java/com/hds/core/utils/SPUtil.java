package com.hds.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SPUtil {
    public static final String FILE_NAME = "AndroidCommon";
    private static SPUtil instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static SPUtil getInstance(Context context) {
        if (instance == null && context != null) {
            instance = new SPUtil(context.getApplicationContext());
        }
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private SPUtil(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public long getLongValue(String key) {
        if (!TextUtils.isEmpty(key)) {
            return sp.getLong(key, 0);
        }
        return 0;
    }

    public String getStringValue(String key) {
        if (!TextUtils.isEmpty(key)) {
            return sp.getString(key, null);
        }
        return null;
    }

    public int getIntValue(String key) {
        if (!TextUtils.isEmpty(key)) {
            return sp.getInt(key, 0);
        }
        return 0;
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        if (StringUtil.isEmpty(key)){
            return false;
        }else {
            return sp.getBoolean(key, defaultValue);
        }
    }

    public float getFloatValue(String key) {
        if (!TextUtils.isEmpty(key)) {
            return sp.getFloat(key, 0);
        }
        return 0;
    }

    public void putStringValue(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            editor = sp.edit();
            editor.putString(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putIntValue(String key, int value) {
        if (!TextUtils.isEmpty(key)) {
            editor = sp.edit();
            editor.putInt(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putObject(String key, Object object) {
        try {
            if (!TextUtils.isEmpty(key)) {
                editor = sp.edit();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                out.writeObject(object);
                String string = outputStream.toString("ISO-8859-1");
                string = URLEncoder.encode(string, "UTF-8");
                out.close();
                outputStream.close();
                editor.putString(key, string);
                editor.commit();
                SharedPreferencesCompat.apply(editor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject(String key) {
        if (!TextUtils.isEmpty(key)) {
            if (sp.contains(key)) {
                String string = sp.getString(key, null);
                if (string != null) {
                    String str = null;
                    ByteArrayInputStream byteArrayInputStream = null;
                    ObjectInputStream objectInputStream = null;
                    try {
                        str = URLDecoder.decode(string, "UTF-8");
                        byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
                        objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        return objectInputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (byteArrayInputStream != null) {
                                byteArrayInputStream.close();
                            }
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void putBooleanValue(String key, boolean value) {
        if (!TextUtils.isEmpty(key)) {
            editor = sp.edit();
            editor.putBoolean(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putLongValue(String key, long value) {
        if (!TextUtils.isEmpty(key)) {
            editor = sp.edit();
            editor.putLong(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putFloatValue(String key, Float value) {
        if (!TextUtils.isEmpty(key)) {
            editor = sp.edit();
            editor.putFloat(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {

        return sp.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}
