package com.zy.rxjavaretrofitokhttp.http;

/**
 * Created by G on 2016/12/2.
 */
public class ResponseInfo<T> {

    private int error;
    private String status;
    private T results;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    //    private int code;
//    private String message;
//    private T data;
//    private String responsestamp;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public String getResponsestamp() {
//        return responsestamp;
//    }
//
//    public void setResponsestamp(String responsestamp) {
//        this.responsestamp = responsestamp;
//    }
}
