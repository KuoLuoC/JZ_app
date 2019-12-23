package com.wzq.jz_app.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wzq.jz_app.BuildConfig;
import com.wzq.jz_app.R;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 * 我的：关于
 */

public class AboutActivity extends AbsAboutActivity {
    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.ic_launcher);
        slogan.setText("家庭记账");
        version.setText("v " + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("家庭记账APP介绍 —开发者(wzq Email:wang_love152@163.com)"));
        items.add(new Card(getString(R.string.about_introduce)));
    }
}
