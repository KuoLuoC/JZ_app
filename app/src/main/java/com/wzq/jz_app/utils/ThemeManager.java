package com.wzq.jz_app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wzq.jz_app.R;
import com.wzq.jz_app.common.Constants;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 * 主题
 */
public class ThemeManager {

    private String[] mThemes = {"天真浪漫","青青草原", "酷炫黑暗", "热情似火", "蓝天白云", "紫情满满", "活力鲜橙", "棕色安然"};

    private static ThemeManager instance;

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public String[] getThemes(){
        return mThemes;
    }

    /**
     * 设置主题色
     * @param context   activity
     * @param theme     主题名称
     */
    public void setTheme(Activity context, String theme){
        String curTheme = SharedPUtils.getCurrentTheme(context);
        if(curTheme != null && curTheme.equals(theme)){
            return;
        }

        SharedPUtils.setCurrentTheme(context,theme);

        context.finish();
        Intent intent = context.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.EXTRA_IS_UPDATE_THEME, true);
        intent.putExtra("THEME", "4");
        context.startActivity(intent);
    }

    /**
     * 获取当前主题名
     * @param context 上下文
     * @return 如: 少女粉
     */
    public String getCurThemeName(Context context){
        return SharedPUtils.getCurrentTheme(context);
    }



    public void init(Context context) {
        String theme = SharedPUtils.getCurrentTheme(context);
        if(theme.equals(mThemes[0])){
            context.setTheme(R.style.AppTheme);
        }else if(theme.equals(mThemes[1])){
            context.setTheme(R.style.AppTheme_Green1);
        }else if(theme.equals(mThemes[2])) {
            context.setTheme(R.style.AppTheme_Black);
        } else if(theme.equals(mThemes[3])){
                context.setTheme(R.style.AppTheme_Green);
        }else if(theme.equals(mThemes[4])){
            context.setTheme(R.style.AppTheme_Blue);
        }else if(theme.equals(mThemes[5])){
            context.setTheme(R.style.AppTheme_Purple);
        }else if(theme.equals(mThemes[6])){
            context.setTheme(R.style.AppTheme_Orange);
        }else if(theme.equals(mThemes[7])){
            context.setTheme(R.style.AppTheme_Brown);
        }
    }
}

