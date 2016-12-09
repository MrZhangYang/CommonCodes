package com.zy.rxjavaretrofitokhttp.bean;

/**
 * Created by G on 2016/12/9.
 */

public class Weather extends BaseBean {

    private String currentCity;
    private String pm25;

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }
}
