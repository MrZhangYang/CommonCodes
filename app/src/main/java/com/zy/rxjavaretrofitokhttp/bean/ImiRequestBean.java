package com.zy.rxjavaretrofitokhttp.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by G on 2016/12/2.
 */
public class ImiRequestBean implements Serializable {
    private HashMap data;

    public void setRequeststamp(long timestamp) {

    }

    public void setData(HashMap data) {
        this.data = data;
    }
}
