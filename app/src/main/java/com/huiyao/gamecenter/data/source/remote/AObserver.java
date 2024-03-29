package com.huiyao.gamecenter.data.source.remote;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.huiyao.gamecenter.App;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;

public abstract class AObserver<T> implements Observer<T> {

    private Context context;

    public AObserver() {
    }

    public AObserver(Context context) {
        this.context = context;
    }

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            String message = "";
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    message = "请求未授权，请稍后重试！";
                    break;
                case FORBIDDEN:
                    message = "请求被禁止，请稍后重试！";
                    break;
                case NOT_FOUND:
                    message = "请求链接无效，请稍后重试！";
                    break;
                case REQUEST_TIMEOUT:
                    message = "请求超时，请稍后重试！";
                    break;
                case INTERNAL_SERVER_ERROR:
                    message = "服务器异常，请稍后重试！";
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                    message = "网络异常，请稍后重试！";
                    break;
            }
            ToastUtils.showShort(App.getInstance(), message);
        } else if (e instanceof JSONException || e instanceof JsonParseException || e instanceof ParseException) {
            ToastUtils.showShort(App.getInstance(), "数据解析异常，请稍后再试！");
        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            ToastUtils.showShort(App.getInstance(), "网络连接超时，请稍后再试！");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShort(App.getInstance(), "网络未连接，请稍后重试！");
        } else if (e instanceof SSLHandshakeException) {
            ToastUtils.showShort(App.getInstance(), "证书验证失败，请稍后再试！");
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        //ToastUtils.showShort(App.getInstance(), "建立连接");
        Logger.d("建立连接");
        //可以弹出Dialog 提示正在加载
        showDialog();
    }

    protected abstract void hideDialog();

    protected abstract void showDialog();




    @Override
    public void onComplete() {
        //ToastUtils.showShort(App.getInstance(), "请求完毕");
        Logger.d("请求完毕");
        //可以取消Dialog 加载完毕
        hideDialog();
    }
}
