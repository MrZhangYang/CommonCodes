package com.zy.rxjavaretrofitokhttp.http;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.zy.rxjavaretrofitokhttp.R;
import com.zy.rxjavaretrofitokhttp.apiexception.ApiException;
import com.zy.rxjavaretrofitokhttp.utils.Constants;
import com.zy.rxjavaretrofitokhttp.utils.ToastUtils;
import com.zy.rxjavaretrofitokhttp.widget.WaitingDialog;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 封装了是否显示动画，和对onError()默认处理
 * Created by G on 2016/12/1.
 */
public abstract class ApiSubscriber<T> extends Subscriber<T> {

    private Context mCtx;
    private WaitingDialog waitingDialog;//加载dialog
    private boolean ishowWaitingDialog;

    public void setShowWaitDialog(boolean showWaitDialog) {
        ishowWaitingDialog = showWaitDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ishowWaitingDialog)
            showWaitDialog();
    }

    public void setmCtx(Context mCtx) {
        this.mCtx = mCtx;
    }

    @Override
    public void onCompleted() {
        if (ishowWaitingDialog)
            dismissDialog();
    }


    /**
     * 对onError()处理
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (ishowWaitingDialog)
            dismissDialog();

        Throwable throwable = e;
//        if(Log.isDebuggable()){
//            Log.i(RtHttp.TAG,throwable.getMessage().toString());
//        }
        /**
         * 获取根源异常
         *
         */
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof HttpException) {//对网络异常 弹出相应的toast
            HttpException httpException = (HttpException) e;
            String errorMsg = httpException.getMessage();
            if (TextUtils.isEmpty(errorMsg)) {
                ToastUtils.showShortToast(mCtx, R.string.imi_toast_common_net_error);
            } else {
                ToastUtils.showShortToast(mCtx, errorMsg);
            }
        } else if (e instanceof ApiException) {//服务器返回的错误
            onResultError((ApiException) e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {//解析异常
            ToastUtils.showShortToast(mCtx, R.string.imi_toast_common_parse_error);
        } else if (e instanceof UnknownError) {
            ToastUtils.showShortToast(mCtx, R.string.imi_toast_common_server_error);
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShortToast(mCtx, R.string.imi_toast_common_net_timeout);
        } else {
            e.printStackTrace();
            ToastUtils.showShortToast(mCtx, R.string.imi_toast_common_net_error);
        }

    }

    /**
     * 服务器返回的错误
     *
     * @param e
     */
    private void onResultError(ApiException e) {//服务器返回code默认处理
        switch (e.getCode()) {
            case Constants.COMMONERRORCODE_5:
                break;
            case Constants.COMMONERRORCODE_6:
                break;
            default:
                String msg = e.getMessage();
                if (TextUtils.isEmpty(msg)) {
                    ToastUtils.showShortToast(mCtx, R.string.imi_toast_common_net_error);
                } else {
                    ToastUtils.showShortToast(mCtx, msg);
                }
        }
    }

    private void dismissDialog() {
        if (waitingDialog != null) {
            if (waitingDialog.isShowing()) {
                waitingDialog.dismiss();
            }
        }
    }

    private void showWaitDialog() {
        if (waitingDialog == null) {
            waitingDialog = new WaitingDialog(mCtx);
//            waitingDialog.
            waitingDialog.setCanceledOnTouchOutside(false);
        }
        waitingDialog.show();
    }
}
