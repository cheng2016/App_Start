package com.huiyao.gamecenter.data.source.remote;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.common.HY_SimResolve;
import com.huiyao.gamecenter.data.entity.AppInitData;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.SharedPreferenceConstants;
import com.huiyao.gamecenter.util.SharedPreferenceUtils;
import com.huiyao.gamecenter.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/***
 * app 发送客户端数据给服务端进行初始化
 */
public class AppInitHelp {

    @SuppressLint("CheckResult")
    public static void initAppData(Context context){
        HttpApi httpApi = HttpFactory.getHttpApiService();

        Map<String, String> param =  RequestParameterUtils.getRequestBaseParameter(context);
        httpApi.AppInitData(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("初始化接口返回数据>>>"+completeInfo);
                        if(status==0){
                            BaseResponseMode<AppInitData> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<AppInitData>>(){});
                            AppInitData appInitData = baseResponseMode.getData();
                            saveInitData(context,appInitData);

                            //获取用户共享经济协议
                            getUserContract(context);
                        }else{
                            ToastUtils.showShort(context,message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });
    }







    //获取用户经济共享协议
    @SuppressLint("CheckResult")
    public static void getUserContract(Context context){
        HttpApi httpApi = HttpFactory.getHttpApiService();

        Map<String, String> param =  RequestParameterUtils.getRequestBaseParameter(context);
        httpApi.getUserContract(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("获取经济共享协议返回数据>>>"+completeInfo);
                        if(status==0){
                            try {
                                JSONObject jsonObject = new JSONObject(completeInfo);
                                if(jsonObject.has("data")){
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    if(data.has("title")) {
                                        String title = data.getString("title");
                                        HY_Constants.contractTitle = title;
                                    }
                                    if(data.has("url")) {
                                        String url = data.getString("url");
                                        HY_Constants.contractUrl = url;
                                    }
                                    Logger.i("contractTitle》》》"+HY_Constants.contractTitle+"contractUrl>>>"+HY_Constants.contractUrl);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Logger.i("解析用户共享协议数据异常");
                            }
                        }else{
                            ToastUtils.showShort(context,message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });
    }










    private static void saveInitData(Context context,AppInitData appInitData){
        if(appInitData!=null){
            HY_SimResolve.deviceId = appInitData.getDevice();
            SharedPreferenceUtils.setPrefString(context, SharedPreferenceConstants.DEVICE_ID ,HY_SimResolve.deviceId);
        }


    }


}
