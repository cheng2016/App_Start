package com.huiyao.gamecenter;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.huiyao.gamecenter.base.BaseApplication;
import com.huiyao.gamecenter.common.InitConfigDevice;
import com.huiyao.gamecenter.data.source.remote.HY_UrlConstants;
import com.huiyao.gamecenter.module.login.HY_JverificationHelp;
import com.huiyao.gamecenter.util.AppUtils;
import com.huiyao.gamecenter.util.DeviceUtils;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by chengzj on 2017/6/17.
 */

public class App extends BaseApplication {
    public final static String TAG = "App";

    private static App mInstance;

    private String imageCacheDir = "";

    private String appCacheDir = "";

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context);
            }
        });
    }

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco初始化
        Fresco.initialize(this);
        mInstance = this;
        initAppTool();
        //初始化请求地址
        HY_UrlConstants.initURL(this);
        //极光一键登陆初始化
        HY_JverificationHelp.JverificationSdkInit(this);
        InitConfigDevice.init(this);
        Logger.i("onCreate");

        if (Build.VERSION.SDK_INT >= 24){
            // android 7.0系统解决拍照的问题
            Logger.d("系统版本=="+Build.VERSION.SDK_INT);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    void initAppTool() {
        //初始化工具类
        Utils.init(this);
        Logger.initialize(this, true, Logger.Level.VERBOSE);
        ToastUtils.isShow = true;
        initPicasoConfig();
    }


    private void initPicasoConfig() {
        if (DeviceUtils.isSDCardEnable()) {
            appCacheDir = Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName(this) + File.separator;
        } else {
            appCacheDir = getCacheDir().getAbsolutePath() + "/" + AppUtils.getAppName(this) + File.separator;
        }
        File directory = new File(appCacheDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        imageCacheDir = appCacheDir + "image/";
        directory = new File(imageCacheDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Logger.i(TAG, "imageCacheDir：" + imageCacheDir);
        Picasso picasso = new Picasso.Builder(this).downloader(
                new OkHttpDownloader(new File(imageCacheDir))).build();
        Picasso.setSingletonInstance(picasso);
    }

    public String getAppCacheDir() {
        File directory = new File(appCacheDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Logger.i(TAG, "getAppCacheDir：" + appCacheDir);
        return appCacheDir;
    }
}
