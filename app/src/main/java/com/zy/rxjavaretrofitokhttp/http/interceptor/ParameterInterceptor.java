package com.zy.rxjavaretrofitokhttp.http.interceptor;

import com.zy.rxjavaretrofitokhttp.bean.CommonData;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by G on 2016/12/2.
 */
public class ParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //get请求后面追加共同参数
        Request request = chain.request();
//        int uid = CommonData.getUid();
        HttpUrl httpUrl = request.url().newBuilder().    //使用addQueryParameter()在url后面添加参数
                addQueryParameter("userId", "uid").build();
        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
