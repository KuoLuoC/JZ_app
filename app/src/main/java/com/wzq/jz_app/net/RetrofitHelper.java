package com.wzq.jz_app.net;


import com.wzq.jz_app.utils.ConstantUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private Retrofit retrofit = null;

    public ApiService getApiService() {

        return getRetrofit().create(ApiService.class);
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder().baseUrl(ConstantUtil.BASE_URL).client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 生成okHttpClient
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(addHttpInterceptor());
        builder.connectTimeout(ConstantUtil.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(ConstantUtil.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(ConstantUtil.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return builder.build();
    }

    /**
     * 拦截请求日志
     *
     * @return
     */
    private Interceptor addHttpInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                Request         request = builder.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8").build();
                try {
                    Response  response  = chain.proceed(request);
                    MediaType mediaType = response.body().contentType();
                    String url = request.url().toString();
                    String    content   = response.body().string();
                    return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }
}
