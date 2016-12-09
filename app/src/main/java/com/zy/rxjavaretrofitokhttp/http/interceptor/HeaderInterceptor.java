package com.zy.rxjavaretrofitokhttp.http.interceptor;

import android.content.Context;

import com.zy.rxjavaretrofitokhttp.bean.CommonData;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by G on 2016/12/2.
 */
public class HeaderInterceptor implements Interceptor {
    private Context mCtx;
    public HeaderInterceptor(Context mContext) {
        this.mCtx=mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original=chain.request();
//        String sessionId = CommonData.getUserInfo(mCtx).sessionId;
        Request.Builder builder=original.newBuilder().header("sessionId", "sessionId");//添加sessionId
        Request request=builder.build();
        return chain.proceed(request);
    }
}
