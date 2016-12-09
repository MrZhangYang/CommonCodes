package com.zy.rxjavaretrofitokhttp.api;

import com.zy.rxjavaretrofitokhttp.http.ResponseInfo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by G on 2016/12/2.
 */
public interface NetworkApi {
    @POST("open/open.do")
    Observable<Object> post(@Query("ACID") int acid, @Body RequestBody entery);

//    @POST("open/open.do")
    //acid是用于区分接口功能，RequestBody为请求的body参数
//    Observable<ResponseInfo<Object>> response(@Query("ACID") int acid, @Body RequestBody entery);

//    @POST("top250")
//    Observable<ResponseInfo<Object>> webPost(@Query("ACID") int acid);


    //获取豆瓣top250榜单
    @GET("weather.php")
    Observable<ResponseInfo<Object>> getTop250();

}
