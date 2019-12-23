package com.wzq.jz_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.wzq.jz_app.model.bean.local.NoteBean;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 */
public class SharedPUtils {

    public final static String USER_INFOR = "userInfo";
    public final static String USER_SETTING = "userSetting";


    /**
     * 获取当前用户账单分类信息
     */
    public static NoteBean getUserNoteBean(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFOR, Context.MODE_PRIVATE);
        if (sp != null) {
            String jsonStr = sp.getString("noteBean", null);
            if (jsonStr != null) {
                Gson gson = new Gson();
                return gson.fromJson(jsonStr, NoteBean.class);
            }
        }
        return null;
    }

    /**
     * 设置当前用户账单分类信息
     */
    public static void setUserNoteBean(Context context, String jsonStr) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("noteBean", jsonStr);
        editor.commit();
    }

    /**
     * 设置当前用户账单分类信息
     */
    public static void setUserNoteBean(Context context, NoteBean noteBean) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(noteBean);
        SharedPreferences sp = context.getSharedPreferences(USER_INFOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("noteBean", jsonStr);
        editor.commit();
    }

    /**
     * 获取当前用户主题
     */
    public static String getCurrentTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        if (sp != null)
            return sp.getString("theme", "原谅绿");
        return null;
    }

    /**
     * 获取当前用户主题
     */
    public static void setCurrentTheme(Context context, String theme) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (editor != null) {
            editor.putString("theme", theme);
            editor.commit();
        }
    }

    /**
     * 判断是否第一次进入APP
     *
     * @param context
     * @return
     */
    public static boolean isFirstStart(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("first", true);
        //第一次则修改记录
        if (isFirst)
            sp.edit().putBoolean("first", false).commit();
        return isFirst;
    }


    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     */
    public static <T> void saveBean2Sp(Context context, T t, String fileName, String keyName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        ByteArrayOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            byte[] bytes = bos.toByteArray();
            String ObjStr = Base64.encodeToString(bytes, Base64.DEFAULT);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(keyName, ObjStr);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T extends Object> T getBeanFromSp(Context context, String fileName, String keyNme) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        byte[] bytes = Base64.decode(preferences.getString(keyNme, ""), Base64.DEFAULT);
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        T obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}