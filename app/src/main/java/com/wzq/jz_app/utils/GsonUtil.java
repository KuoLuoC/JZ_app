package com.wzq.jz_app.utils;

/**
 * 作者：wzq on 2019/4/19.
 * 邮箱：wang_love152@163.com
 */

import com.google.gson.Gson;

/**
 * Created by hy on 2018/5/30.
 */

public class GsonUtil {
    private static class GsonHolder{
        private static final Gson INSTANCE = new Gson();
    }

    public static Gson getInstance()
    {
        return GsonHolder.INSTANCE;
    }


}
