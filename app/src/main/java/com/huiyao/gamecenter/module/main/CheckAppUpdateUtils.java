package com.huiyao.gamecenter.module.main;

import android.annotation.SuppressLint;
import android.content.Context;

import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.CheckAppUpdateEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.AppUtils;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckAppUpdateUtils {

    private static CheckAppUpdateUtils checkAppUpdateUtils;

    private Context context;

    private CheckAppUpdateUtils(Context context){
        this.context = context;
    }

    public static CheckAppUpdateUtils getInstance(Context context){
        if(checkAppUpdateUtils==null){
            checkAppUpdateUtils = new CheckAppUpdateUtils(context);
        }

        return checkAppUpdateUtils;
    }


    /**
     * 检查app是否有更新
     */
    @SuppressLint("CheckResult")
    public void checkAppUpdate() {
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        params.put("app_id", HY_Constants.APPID);
        params.put("app_version", AppUtils.getVersionCode(context)+"");
        httpApi.checkAppUpdate(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("检查版本更新返回结果>>>" + completeInfo);
                        if(status==0){
                            CheckAppUpdateEntity checkAppUpdateEntity = GsonUtil.stringToBean(completeInfo, CheckAppUpdateEntity.class);
                            ApkUpdateDialog.getInstance(context).showDialog(checkAppUpdateEntity.getMessage());
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });

    }


}
