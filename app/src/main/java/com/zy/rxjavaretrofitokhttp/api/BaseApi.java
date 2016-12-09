package com.zy.rxjavaretrofitokhttp.api;

import com.google.gson.Gson;
import com.zy.rxjavaretrofitokhttp.apiexception.ApiThrowExcepitionFun1;
import com.zy.rxjavaretrofitokhttp.bean.ImiRequestBean;
import com.zy.rxjavaretrofitokhttp.http.ObjectToJSONObjectFun1;
import com.zy.rxjavaretrofitokhttp.http.StringToJSONObjectFun1;
import com.zy.rxjavaretrofitokhttp.utils.ProtocolUtils;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by G on 2016/12/2.
 */

public class BaseApi {

    public static RequestBody toBody(HashMap map) {
        Gson gson = new Gson();
        ImiRequestBean requestBean = new ImiRequestBean();
        requestBean.setRequeststamp(ProtocolUtils.getTimestamp());
        requestBean.setData(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(requestBean));
    }


    public static RequestBody toBody(JSONObject jsonObject) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject).toString());
    }

    public static class ObserableBuilder {

        private Observable observable;
        private boolean apiException;
        private boolean toJSONJbject;
        private boolean isWeb;
        private Scheduler subscribeScheduler;
        private Scheduler obscerveScheduler;

        public void setObscerveScheduler(Scheduler obscerveScheduler) {
            this.obscerveScheduler = obscerveScheduler;
        }

        public void setSubscribeScheduler(Scheduler subscribeScheduler) {
            this.subscribeScheduler = subscribeScheduler;
        }

        public ObserableBuilder(Observable o) {
            this.observable = o;
        }

        public ObserableBuilder addApiException() {
            apiException = true;
            return this;
        }

        public ObserableBuilder addToJSONObject() {
            toJSONJbject = true;
            return this;
        }

        public ObserableBuilder isWeb() {
            isWeb = true;
            return this;
        }

        public Observable build() {
            if (isWeb) {
                observable = observable.map(new StringToJSONObjectFun1());
            }
            if (apiException) {
                observable = observable.flatMap(new ApiThrowExcepitionFun1());
            }
            if (toJSONJbject) {
                observable = observable.map(new ObjectToJSONObjectFun1());
            }
            if (subscribeScheduler != null) {
                observable = observable.subscribeOn(subscribeScheduler);
            } else {
                observable = observable.subscribeOn(Schedulers.io());
            }
            if (obscerveScheduler != null) {
                observable = observable.observeOn(obscerveScheduler);
            } else {
                observable = observable.observeOn(AndroidSchedulers.mainThread());
            }
            return observable;
        }
    }
}
