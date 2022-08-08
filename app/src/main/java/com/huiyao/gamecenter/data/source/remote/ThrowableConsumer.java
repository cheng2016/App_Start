package com.huiyao.gamecenter.data.source.remote;

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

import io.reactivex.functions.Consumer;
import retrofit2.adapter.rxjava2.HttpException;

/***
 * 处理 异常comsumer 观察者类
 */
public abstract class ThrowableConsumer implements Consumer<Throwable> {
    public static final String TAG = ThrowableConsumer.class.getSimpleName();
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;



    public ThrowableConsumer(){

    }





    @Override
    public void accept(Throwable e) throws Exception {
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
            onExceptionInfo(httpException.code(),message);

            ToastUtils.showShort(App.getInstance(), message);
        } else if (e instanceof JSONException || e instanceof JsonParseException || e instanceof ParseException) {
            ToastUtils.showShort(App.getInstance(), "数据解析异常，请稍后再试！");
            onExceptionInfo(5001,"数据解析异常，请稍后再试！");
        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            ToastUtils.showShort(App.getInstance(), "网络连接超时，请稍后再试！");
            onExceptionInfo(5002,"网络连接超时，请稍后再试！");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShort(App.getInstance(), "网络未连接，请稍后重试！");
            onExceptionInfo(5003,"网络未连接，请稍后重试");
        } else if (e instanceof SSLHandshakeException) {
            ToastUtils.showShort(App.getInstance(), "证书验证失败，请稍后再试！");
            onExceptionInfo(5004,"证书验证失败，请稍后再试");
        }else {
            Logger.i("ThrowableConsumer网络请求返回未判断类型异常>>>"+e.getMessage());
            onExceptionInfo(5005,"其它未知类型错误");
        }
        Logger.e(TAG, e.getMessage(), e);

    }


       protected abstract void onExceptionInfo(int code,String message);


}
