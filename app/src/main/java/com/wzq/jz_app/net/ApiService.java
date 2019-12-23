package com.wzq.jz_app.net;



import com.wzq.jz_app.ui.news.NewsBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * retrofit2 Api接口
 */
public interface ApiService {

    /**
     * post请求接口
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("journalismApi")
    Observable<ResponseBody> noticeList(@FieldMap Map<String, String> map);

    /**
     * get请求财经新闻接口
     *
     * @return
     */

    @GET("journalismApi")
    Observable<NewsBean> test();


}



