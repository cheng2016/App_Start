package com.huiyao.gamecenter.common;

import android.annotation.SuppressLint;
import android.content.Context;

import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.util.Logger;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppEventUploadUtils {


    private Context context;
    private static AppEventUploadUtils appEventUploadUtils;
    private AppEventUploadUtils(Context context){
        this.context = context;

    }

    public static AppEventUploadUtils getInstance(Context context){
        if(appEventUploadUtils==null){
            appEventUploadUtils = new AppEventUploadUtils(context);
        }
        return appEventUploadUtils;
    }

    /**
     * app 事件统计 主动上报接口
     * @param recommend_id  活动或者广告位 唯一标识
     * @param type 事件来源
     */
    @SuppressLint("CheckResult")
    public void eventUpLoad(String recommend_id,int type){
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = LoginUtils.getInstance().getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
        }
        params.put("app_id", HY_Constants.APPID);
        params.put("recommend_id",recommend_id+"");
        params.put("type", type+"");
        httpApi.appEventUpload(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("事件上报返回数据>>>type"+type + completeInfo);
                        if (status == 0) {
                            Logger.i("事件上报返回数据>>>" + completeInfo);
                        } else {
                            Logger.i("事件上报返回数据失败>>>"+type + message);
                        }

                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                    }
                });
    }
}
