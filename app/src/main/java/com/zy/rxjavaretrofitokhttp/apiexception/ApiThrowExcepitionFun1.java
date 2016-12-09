package com.zy.rxjavaretrofitokhttp.apiexception;

import com.zy.rxjavaretrofitokhttp.http.ResponseInfo;

import rx.Observable;

/**
 * Created by G on 2016/12/9.
 */

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 * Subscriber真正需要的数据类型，也就是Data部分的数据类型
 *
 * @param <T>
 */
public class ApiThrowExcepitionFun1<T> implements rx.functions.Func1<ResponseInfo<T>, Observable<T>> {
    @Override
    public Observable<T> call(ResponseInfo<T> tResponseInfo) {


        if (!"success".equals(tResponseInfo.getStatus())) {
            return Observable.error(new ApiException(tResponseInfo.getError(),
                    tResponseInfo.getStatus()));
        }
//        if (tResponseInfo.getCode() != 200) { //如果code返回的不是200,则抛出ApiException异常，否则返回data数据
//            return Observable.error(new ApiException(tResponseInfo.getCode(),
//                    tResponseInfo.getMessage()));
//        }
        return Observable.just(tResponseInfo.getResults());
    }
}
