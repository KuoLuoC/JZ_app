package com.wzq.jz_app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class GlideUtils {

    public static void loadImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        Glide.with(context).load(url).into(iv);
    }

    @SuppressLint("CheckResult")
    public static void loadCirImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(context).load(url).apply(options).into(iv);
    }

//    //圆形头像
//    @SuppressLint("CheckResult")
//    public static void loadRoundImage(Context context, String url, ImageView iv, int dp) {
//        RequestOptions options = new RequestOptions()
//        options.transform(new GlideRoundTransform(context, dp));
//        Glide.with(context)
//                .load(url).apply(options).into(iv);
//    }
//
//    public static void loadImage1(Context context, String url, ImageView iv) {
//        RequestOptions requestOptions = new RequestOptions();
//        Glide.with(context)
//                .load(url)
//                .apply(requestOptions)
//                .into(iv);
//    }
//
//
//    //加载图片
//    @SuppressLint("CheckResult")
//    public static void loadRidus1(Context context, String url, ImageView imageView,int place_image, int erroImg) {
//        if (context != null) {
////通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions requestOptions = new RequestOptions()
//                    .placeholder(place_image)
//                    .error(erroImg)
////                    .crossFade()//crossFade(int duration)设置动画时间，单位ms，默认300ms
////                    //.dontAnimate()//设置关闭动画效果
////                    //重写图片大小
//                    .override(900, 700);
//            requestOptions.transform(new GlideRoundTransform(context, 15));
//            Glide.with(context)
//                    .load(url)
//                    .apply(requestOptions)
//                    .into(imageView);
//        }
//    }
//    @SuppressLint("CheckResult")
//    public static void loadRidus2(Context context, String url,ImageView imageView) {
//        if (context != null) {
////通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions requestOptions = new RequestOptions()
//                    .placeholder(R.mipmap.img_default)
//                    .error(R.mipmap.img_default)
////                    .crossFade()//crossFade(int duration)设置动画时间，单位ms，默认300ms
////                    //.dontAnimate()//设置关闭动画效果
////                    //重写图片大小
//                    .override(900, 700);
////            requestOptions.transform(new GlideRoundTransform(context, 0));
//            Glide.with(context)
//                    .load(url)
//                    .apply(requestOptions)
//                    .into(imageView);
//        }
//    }
}