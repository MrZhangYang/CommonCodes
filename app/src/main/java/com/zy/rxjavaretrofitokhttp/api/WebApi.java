package com.zy.rxjavaretrofitokhttp.api;

/**
 * Created by G on 2016/12/9.
 */


import com.zy.rxjavaretrofitokhttp.http.RtHttp;
import com.zy.rxjavaretrofitokhttp.http.stringconverter.StringConverterFactory;

import java.util.HashMap;

import rx.Observable;

import static com.zy.rxjavaretrofitokhttp.utils.Constants.BASE_URL;

/**
 * webApi跟MobileApi请求地址以及返回数据的数据都不一样，
 * WebApi返回的数据类型是String
 */

public class WebApi extends BaseApi {

    public static final int ROLLER = 1;
    public static final int FRUIT = 2;
    public static final int WX = 3;
    public static NetworkApi networkApi;
    public static Observable observable;

    public static NetworkApi getNetworkApi(String baseUrl, HashMap map) {
        networkApi = new RtHttp.NetWorkApiBuilder()
                .setBaseUrl(baseUrl)
                .setAddDynamicParameterMap(map)
                .setConvertFactory(StringConverterFactory.create())
                .build();
        return networkApi;
    }

    public static NetworkApi getRollerApi(HashMap map) {
        return getNetworkApi(BASE_URL, map);
    }

    public static NetworkApi getFruitApi(HashMap map) {
        return getNetworkApi(BASE_URL, map);
    }

    public static NetworkApi getWxApi(HashMap map) {
        return getNetworkApi(BASE_URL, map);
    }

    public static Observable getObserable(Observable observable) {
        observable = new ObserableBuilder(observable)
                .isWeb()
                .build();
        return observable;
    }

    public static Observable webPost(HashMap map, String action, int type) {
        NetworkApi networkApi = null;
        if (type == ROLLER) {
            networkApi = getRollerApi(map);
        } else if (type == FRUIT) {
            networkApi = getFruitApi(map);
        } else if (type == WX) {
            networkApi = getWxApi(map);
        }
//        observable = networkApi.webPost(1);
//        String[] str = action.split("/");
//        if (str.length == 1) {
//            observable = networkApi.webPost(str[0]);
//        } else if (str.length == 2) {
//            observable = networkApi.webPost(str[0], str[1]);
//        } else {
//            return null;
//        }
        return getObserable(observable);
    }


}
