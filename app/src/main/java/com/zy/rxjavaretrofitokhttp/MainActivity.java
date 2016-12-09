package com.zy.rxjavaretrofitokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zy.rxjavaretrofitokhttp.api.MobileApi;
import com.zy.rxjavaretrofitokhttp.api.WebApi;
import com.zy.rxjavaretrofitokhttp.bean.Weather;
import com.zy.rxjavaretrofitokhttp.http.ApiSubscriber;
import com.zy.rxjavaretrofitokhttp.http.RtHttp;
import com.zy.rxjavaretrofitokhttp.http.stringconverter.StringConverterFactory;
import com.zy.rxjavaretrofitokhttp.utils.ProtocolUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, Integer> mapParam = new HashMap<>();
        mapParam.put("start", 0);
        mapParam.put("count", 20);

        RtHttp.with(this)
                .setShowWaitingDialog(false)
//                .setObservable(WebApi.webPost(mapParam,"",1))
                .setObservable(MobileApi.response(mapParam, 1))//MobileApi.response 返回一个Observalbe
                .subscriber(new ApiSubscriber<ArrayList<Weather>>() {
                    @Override
                    public void onNext(ArrayList<Weather> weatherList) {
                        Gson gson = new Gson();
                        Weather weather = null;
                        try {
//                            String js=weatherList.get(0).toString();
                            String jsonStr="{\"currentCity\":\"上海\",\"pm25\":25}";
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            weather = gson.fromJson(jsonObject.toString(), Weather.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("", weather.getCurrentCity());
                    }
                });
    }
}
