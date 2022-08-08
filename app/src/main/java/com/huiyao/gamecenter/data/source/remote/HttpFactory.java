package com.huiyao.gamecenter.data.source.remote;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.huiyao.gamecenter.App;
import com.huiyao.gamecenter.data.entity.AppEvent;
import com.huiyao.gamecenter.util.NetUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chengzj on 2017/6/18.
 */

public class HttpFactory {
    public static final String TAG = "HY HttpFactory ";

    private static Retrofit retrofit;

    private static Retrofit retrofit2;

    private static HttpApi httpApi;

    private HttpFactory() {
    }

    public static <T> T createRetrofit2(Class<T> service) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(HY_UrlConstants.URL_HOST)
                    .client(getOkClient())
                    .build();
        }
        return retrofit.create(service);
    }

    /*public static <T> T createGsonRetrofit2(Class<T> service) {
        if (retrofit2 == null) {
            synchronized (HttpFactory.class) {
                if (retrofit2 == null) {
                    retrofit2 = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(HY_UrlConstants.URL_HOST)
                            .client(getOkClient())
                            .build();
                }
            }
        }
        return retrofit2.create(service);
    }*/

    public static HttpApi getHttpApiService() {
        if (httpApi == null) {
            httpApi = createRetrofit2(HttpApi.class);
        }
        return httpApi;
    }


    public static <T> T createSSLService(final Class<T> service, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getSSLClient(context))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Https.wxBaseurl)
                .build();
        return retrofit.create(service);
    }


    private static OkHttpClient getOkClient() {
        //设置缓存路径
        final File httpCacheDirectory = new File(App.getInstance().getCacheDir(), "okhttpCache");
        Log.i(TAG, httpCacheDirectory.getAbsolutePath());
        //设置缓存 10M
//        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);   //缓存可用大小为10M
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                //设置拦截器，显示日志信息
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CommonRequestInterceptor())
//                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .cache(cache)
                .addInterceptor(dynamicBaseUrlInterceptor)
                .build();
        return okHttpClient;
    }

    static class CommonRequestInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();

            if ("GET".equals(request.method())) { // GET方法
                //加入公共参数
                Map<String, String> params = RequestParameterUtils.getCommonRequestBaseParameter();
                for (String key : params.keySet()) {
                    httpUrlBuilder.addQueryParameter(key, params.get(key));
                }

                String productId = "";
                HttpUrl httpUrl = httpUrlBuilder.build();
                // 打印所有get参数
                Set<String> paramKeys = httpUrl.queryParameterNames();
                for (String key : paramKeys) {
                    String value = httpUrl.queryParameter(key);
                    if (key.equals("product_id")) {
                        productId = value;
                    }
                    Log.d(TAG + "GET 参数", key + " " + value);
                }

                if (!TextUtils.isEmpty(productId)) {
                    AppEvent event = RequestParameterUtils.getAppEvent(productId);
                    if (event != null) {
                        httpUrlBuilder.addQueryParameter("recommend_id", event.recommend_id);
                        httpUrlBuilder.addQueryParameter("type", String.valueOf(event.type));
                    }
                }

                requestBuilder.url(httpUrlBuilder.build());
            } else if ("POST".equals(request.method())) {
                // FormBody 和url不太一样，若需添加公共参数，需要新建一个构造器
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                // 把已有的post参数添加到新的构造器
                if (request.body() instanceof FormBody) {
                    FormBody formBody = (FormBody) request.body();
                    for (int i = 0; i < formBody.size(); i++) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }

                //加入公共参数
                Map<String, String> params = RequestParameterUtils.getCommonRequestBaseParameter();
                // 这里可以添加一些公共post参数
                for (String key : params.keySet()) {
                    bodyBuilder.addEncoded(key, params.get(key));
                }
                FormBody newBody = bodyBuilder.build();
                // 打印所有post参数
                for (int i = 0; i < newBody.size(); i++) {
                    Log.d(TAG + "POST 参数", newBody.name(i) + " " + newBody.value(i));
                }
                // 将最终的表单body填充到request中
                requestBuilder.post(newBody);
            }
            request = requestBuilder.build();
            return chain.proceed(request);
        }
    }


    private static final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d(TAG, "HttpLoggingInterceptor 获取到的日志>>>" + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);


    /******
     *
     * 定义okhttp 拦截器 通过拦截 retrofit请求方法中定义的 请求头中 header 值来判断用那个baseUrl
     *
     *
     */
    private static final Interceptor dynamicBaseUrlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取原始的originalRequest
            Request originalRequest = chain.request();
            //获取老的url
            HttpUrl oldUrl = originalRequest.url();

            Log.i(TAG, "Interceptor 替换域名获取oldUrl>>>" + oldUrl);
            //获取originalRequest的建立者builder
            Request.Builder builder = originalRequest.newBuilder();
            //获取头信息的集合如：manage,mdffx
            List<String> urlnameList = originalRequest.headers("UrlName");
            if (urlnameList != null && urlnameList.size() > 0) {
                Log.i(TAG, "获取header UrlName value");

                //删除原有配置中的值,就是namesAndValues集合里的值
                builder.removeHeader("UrlName");
                //获取头信息中配置的value,如：manage或者mdffx
                String urlnameValue = urlnameList.get(0);
                HttpUrl baseURL = null;
                //根据头信息中配置的value,来匹配新的base_url地址
                if (HttpApi.URL_HOST.equals(urlnameValue)) {
                    //替换api 域名
                    baseURL = HttpUrl.parse(HY_UrlConstants.URL_HOST);
                } else if (HttpApi.URL_HOST_BU.equals(urlnameValue)) {
                    //替换 bu 域名
                    baseURL = HttpUrl.parse(HY_UrlConstants.URL_HOST_BU);
                } else if (HttpApi.URL_HOST_U9API.equals(urlnameValue)) {
                    //替换 u9 域名
                    baseURL = HttpUrl.parse(HY_UrlConstants.URL_HOST_U9API);
                }
                //重建新的HttpUrl，须要从新设置的url部分
                HttpUrl newHttpUrl = oldUrl.newBuilder()
                        .scheme(baseURL.scheme())//http协议如：http或者https
                        .host(baseURL.host())//主机地址
                        .port(baseURL.port())//端口
                        .build();
                //获取处理后的新newRequest
                Request newRequest = builder.url(newHttpUrl).build();
                HttpUrl newUrl = newRequest.url();

                Log.i(TAG, "Interceptor 替换域名后新域名>>>" + newUrl.toString());
                return chain.proceed(newRequest);
            } else {
                return chain.proceed(originalRequest);
            }
        }
    };


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //获取网络状态
            int netWorkState = NetUtils.getNetworkState(App.getInstance());
            //无网络，请求强制使用缓存
            if (netWorkState == NetUtils.NETWORK_NONE) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            switch (netWorkState) {
                case NetUtils.NETWORK_MOBILE://moblie network 情况下缓存15s
                    int maxAge = 0;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                case NetUtils.NETWORK_WIFI://wifi network 情况下不使用缓存
                    maxAge = 0;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();

                case NetUtils.NETWORK_NONE://none network 情况下离线缓存4周
                    int maxStale = 60 * 60 * 24 * 4 * 7;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                default:
                    throw new IllegalStateException("network state  is Erro!");
            }
        }
    };

    public static OkHttpClient getSSLClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor);
        HttpsUtils.SSLParams sslParams = null;
//      sslParams = setInputStream( new Buffer().writeUtf8(WEIXIN).inputStream());

        try {
            sslParams = setInputStream(context.getAssets().open("mp.weixin.qq.com.crt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        return builder.build();
    }

    private static HttpsUtils.SSLParams setInputStream(InputStream... inputStream) {
        return HttpsUtils.getSslSocketFactory(null, null, inputStream);
    }


}
