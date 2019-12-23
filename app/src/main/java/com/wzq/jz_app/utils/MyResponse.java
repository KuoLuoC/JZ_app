package com.wzq.jz_app.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>文件描述：<p>
 * <p>作者：wangzhiqiang<p>
 * <p>创建时间：2018/12/3<p>
 * <p>更改时间：2018/12/3<p>
 * <p>版本号：1<p>
 * <p>邮箱：blackcat6039@163.com<p>
 */
public class MyResponse<T> {
    /**
     * 状态,0=成功，1=失败,2=成功但是出现数据重复操作等的问题。
     */
    private int status;
    /**
     * 说明
     */
    private String msg;
    /**
     * 业务数据,AES加密
     */
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return
     */
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 先对data进行解密，然后返回datajson对应的对象。
     *
     * @param clazz
     * @return
     */
    public T getDataBean(Class clazz) {
        try {
            if (!TextUtils.isEmpty(this.getData())) {
                String data = "";
//                data = AesUtils.decryptAES(this.getData());
                Log.e("123", "返回数据解析data= " + data);
                return (T) GsonUtil.getInstance().fromJson(data, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MyResponse fromJson(String json, Class clazz) {
        try {
            Gson gson = new Gson();
            Type objectType = type(MyResponse.class, clazz);
            MyResponse response = gson.fromJson(json, objectType);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
