package com.zy.rxjavaretrofitokhttp.apiexception;

/**
 * Created by G on 2016/12/1.
 */
public class ApiException extends Exception{
    private int code;
    public ApiException(int code,String s){
        super(s);
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
