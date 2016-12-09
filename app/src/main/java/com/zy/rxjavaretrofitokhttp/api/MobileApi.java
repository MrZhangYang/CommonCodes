package com.zy.rxjavaretrofitokhttp.api;

import com.zy.rxjavaretrofitokhttp.http.RtHttp;
import com.zy.rxjavaretrofitokhttp.utils.Constants;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by G on 2016/12/9.
 */


/**
 * getNetworkApi()方法可以创建特定的NetworkApi，
 * getObserable添加数据返回后特定的处理
 */
public class MobileApi extends BaseApi {
    public static NetworkApi networkApi;
    public static Observable observable1;

    public static NetworkApi getNetworkApi() {//使用NetworkApiBuilder创建networkApi
        if (networkApi == null) {
            networkApi = new RtHttp.NetWorkApiBuilder()
                    .addSession()             //添加sessionid
                    .addParameter()           //添加固定参数
                    .build();
        }
        return networkApi;
    }

    public static Observable getObservable(Observable observable) {
        observable1 = new ObserableBuilder(observable)
                .addApiException()             //添加apiexception
                .build();
        return observable1;
    }

    public static Observable response(HashMap<String, Integer> map, int protocolId) {
//        RequestBody body = toBody(map);
//        return getObservable(getNetworkApi().response(protocolId, body));
        return getObservable(getNetworkApi().getTop250());
    }


}
