package com.zy.rxjavaretrofitokhttp.http;

import android.content.Context;
import android.text.TextUtils;

import com.zy.rxjavaretrofitokhttp.api.NetworkApi;
import com.zy.rxjavaretrofitokhttp.http.interceptor.DynamicParameterInterceptor;
import com.zy.rxjavaretrofitokhttp.http.interceptor.HeaderInterceptor;
import com.zy.rxjavaretrofitokhttp.http.interceptor.LogInterceptor;
import com.zy.rxjavaretrofitokhttp.http.interceptor.ParameterInterceptor;
import com.zy.rxjavaretrofitokhttp.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 网络请求总入口
 * <p>
 * RtHttp的代码很简洁，NetworkApiBuilder使用builder模式创建NetWorkApi，可以动态地配置Retrofit和OkHttpClient的参数。
 * Retrofit可配置参数：
 * <p>
 * 1.baseUrl:可通过设置baseUrl产生不同的retrofit
 * 2.addConverterFactory：通过设置addConverterFactory可适配后台接口返回不同的数据类型，例如json和String
 * 3.OkHttp可添加任意Interceptor实现网络请求的处理：
 * <p>
 * 4.HeaderInterceptory用于添加header(sessionid)
 * 5.ParameterInterceptor用于添加url固定的参数
 * 6.DynamicParameterInterceptor用于url添加动态参数
 * 7.LogInterceptor 用户显示log
 * <p>
 * Created by G on 2016/12/1.
 */

public class RtHttp {

    public static final String TAG = RtHttp.class.getSimpleName();
    public static RtHttp instance = new RtHttp();//单例模式
    private Observable observable;
    private static Context mContext;
    private boolean isShowWaitingDialog;

    /**
     * 设置Context使用弱引用
     *
     * @param ct
     * @return
     */
    public static RtHttp with(Context ct) {
        WeakReference<Context> wr = new WeakReference<Context>(ct);
        mContext = wr.get();
        return instance;
    }

    /**
     * 设置是否显示加载动画
     *
     * @param showWaitingDialog
     * @return
     */
    public RtHttp setShowWaitingDialog(boolean showWaitingDialog) {
        isShowWaitingDialog = showWaitingDialog;
        return instance;
    }

    /**
     * 设置observable
     *
     * @param observable
     * @return
     */
    public RtHttp setObservable(Observable observable) {
        this.observable = observable;
        return instance;
    }

    public RtHttp subscriber(ApiSubscriber subscriber) {
        subscriber.setmCtx(mContext);//给subscriber设置Context，用于显示网络加载动画
        subscriber.setShowWaitDialog(isShowWaitingDialog);//控制是否显示动画
        observable.subscribe(subscriber);//
        return instance;
    }

    /**
     * 使用Retrofit.Builder和Okhttp.Builder构建networkapi
     */
    public static class NetWorkApiBuilder {

        private String baseUrl;//根地址
        private boolean isAddSession;//是否添加sessionid
        private HashMap<String, String> addDynamicParameterMap;//url动态参数
        private boolean isAddCommonParameter;//url是否添加固定参数
        private Retrofit.Builder rtBuilder;
        private OkHttpClient.Builder okBuilder;
        private Converter.Factory convertFactory;

        public NetWorkApiBuilder setConvertFactory(Converter.Factory convertFactory) {
            this.convertFactory = convertFactory;
            return this;
        }

        public NetWorkApiBuilder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public NetWorkApiBuilder addParameter() {
            isAddCommonParameter = true;
            return this;
        }

        public NetWorkApiBuilder addSession() {
            isAddSession = true;
            return this;
        }

        public NetWorkApiBuilder setAddDynamicParameterMap(HashMap<String, String> addDynamicParameterMap) {
            this.addDynamicParameterMap = addDynamicParameterMap;
            return this;
        }

        public NetworkApi build() {
            rtBuilder = new Retrofit.Builder();
            okBuilder = new OkHttpClient.Builder();
            if (!TextUtils.isEmpty(baseUrl)) {
                rtBuilder.baseUrl(baseUrl);
            } else {
                rtBuilder.baseUrl(Constants.BASE_URL);
            }
            if (isAddSession) {
                okBuilder.addInterceptor(new HeaderInterceptor(mContext));
            }
            if (isAddCommonParameter) {
                okBuilder.addInterceptor(new ParameterInterceptor());
            }
            if (addDynamicParameterMap != null) {
                okBuilder.addInterceptor(new DynamicParameterInterceptor(addDynamicParameterMap));
            }
            //warning:must in the last intercepter to log the network;
//            if (Log.isDebuggable()) { //改成自己的显示log判断逻辑
//            okBuilder.addInterceptor(new LogInterceptor());
//            }
            if (convertFactory != null) {
                rtBuilder.addConverterFactory(convertFactory);
            } else {
                rtBuilder.addConverterFactory(GsonConverterFactory.create());
            }
            rtBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okBuilder.build());
            return rtBuilder.build().create(NetworkApi.class);
        }

    }

}


























