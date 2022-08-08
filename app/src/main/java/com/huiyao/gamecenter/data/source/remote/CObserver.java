package com.huiyao.gamecenter.data.source.remote;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.huiyao.gamecenter.util.Logger;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/21 15:50
 * @描述:
 */
public abstract class CObserver<T> implements Observer<T> {
    public static final String TAG = CObserver.class.getSimpleName();
    private Context context;

    private Disposable k;

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public String erroMsg = "";

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    erroMsg = "请求未授权，请稍后重试！";
                    break;
                case FORBIDDEN:
                    erroMsg = "请求被禁止，请稍后重试！";
                    break;
                case NOT_FOUND:
                    erroMsg = "请求链接无效，请稍后重试！";
                    break;
                case REQUEST_TIMEOUT:
                    erroMsg = "请求超时，请稍后重试！";
                    break;
                case INTERNAL_SERVER_ERROR:
                    erroMsg = "服务器异常，请稍后重试！";
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                    erroMsg = "网络异常，请稍后重试！";
                    break;
            }
            Log.e(TAG, erroMsg);
        } else if (e instanceof JSONException || e instanceof JsonParseException || e instanceof ParseException) {
            Log.e(TAG, "数据解析异常，请稍后再试！");
        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            Log.e(TAG, "网络连接超时，请稍后再试！");
        } else if (e instanceof ConnectException) {
            Log.e(TAG, "网络未连接，请稍后重试！");
        } else if (e instanceof SSLHandshakeException) {
            Log.e(TAG, "证书验证失败，请稍后再试！");
        }
        Logger.e(TAG, e.getMessage(), e);
    }


    @Override
    public void onSubscribe(Disposable d) {
        //ToastUtils.showShort(App.getInstance(), "建立连接");
        Logger.d("建立连接");
        //可以弹出Dialog 提示正在加载
        k = d;
    }


    @Override
    public void onComplete() {
        //ToastUtils.showShort(App.getInstance(), "请求完毕");
        Logger.d("请求完毕");
        //可以取消Dialog 加载完毕
        if (!k.isDisposed())
            k.dispose();
    }
}
