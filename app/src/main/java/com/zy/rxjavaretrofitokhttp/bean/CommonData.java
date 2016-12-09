package com.zy.rxjavaretrofitokhttp.bean;

import android.content.Context;

/**
 * Created by G on 2016/12/2.
 */
public class CommonData extends BaseBean{
    private static UserInfo userInfo;
    private static int uid;

    public static UserInfo getUserInfo(Context mCtx) {
        return userInfo;
    }
    public static int getUid(){
        return uid;
    }
}
