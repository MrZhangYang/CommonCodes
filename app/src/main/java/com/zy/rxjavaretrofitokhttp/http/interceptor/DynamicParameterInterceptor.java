package com.zy.rxjavaretrofitokhttp.http.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by G on 2016/12/2.
 */
public class DynamicParameterInterceptor implements Interceptor {
    private HashMap<String,String> map;
    public DynamicParameterInterceptor(HashMap<String, String> map) {
        this.map=map;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        HttpUrl.Builder builder = request.url().newBuilder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            builder.addQueryParameter((String) entry.getKey(),(String) entry.getValue());
        }
        request=request.newBuilder().url(builder.build()).build();
        return chain.proceed(request);
    }
}
