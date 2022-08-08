package com.huiyao.gamecenter.data.source.remote;

import android.content.Context;
import android.text.TextUtils;

import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.common.HY_SimResolve;
import com.huiyao.gamecenter.data.entity.AppEvent;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class RequestParameterUtils {
    public static final String TAG = "RequestParameterUtils";
    private static Map<String, AppEvent> requestMap;

    public static void putAppEvent(String id, AppEvent event) {
        if (requestMap == null) {
            requestMap = new HashMap();
        }
        requestMap.put(id, event);
    }

    public static AppEvent getAppEvent(String id) {
        if (requestMap == null) return null;
        AppEvent event = requestMap.get(id);
        requestMap.clear();
        return event;
    }


    public static Map<String, String> getCommonRequestBaseParameter() {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(HY_SimResolve.deviceId)) {
            map.put("device", HY_SimResolve.deviceId);
        }
        map.put("aid", HY_Constants.PLAN_ID);
        map.put("appversion", "1");

        String imei = HY_SimResolve.imei;
        String oaid = HY_SimResolve.oaid;
        String androidId = HY_SimResolve.androidId;

        map.put("imei", imei);
        Logger.d("imei:" + imei);

        map.put("uuid", HY_SimResolve.uuid);
        map.put("oaid", oaid);
        map.put("androidId", androidId);
        map.put("channel", HY_Constants.CHANNEL_CODE);
        map.put("channel_id", HY_Constants.CHANNEL_CODE);
//		 map.put("plan_id", HY_Constants.PLAN_ID);
        map.put("sub_channel", HY_Constants.CHANNEL_ID);
        map.put("sub_channel_id", HY_Constants.CHANNEL_ID);
        map.put("app", HY_Constants.APPID);
        map.put("app_id", HY_Constants.APPID);
        //废弃参数原sdk初始化中使用,去掉后c初始化接口参数返回异常
        map.put("sdk_version", HY_Constants.SDK_VERSION);
        if (LoginUtils.getInstance().getUser() != null && !TextUtils.isEmpty(LoginUtils.getInstance().getUser().getGuid())) {
            map.put("guid", LoginUtils.getInstance().getUser().getGuid());
        }
        if (LoginUtils.getInstance().getUser() != null && !TextUtils.isEmpty(LoginUtils.getInstance().getUser().getToken())) {
            map.put("token", LoginUtils.getInstance().getUser().getToken());
        }
        /*map.put("sdk_version", HY_Constants.HY_SDK_VERSION_CODE);
        if (HY_IsSimulator.isEmulator(mActivity)
                || HY_IsSimulator.isHasBlueTooth()
                || HY_IsSimulator.isHasLightSensorManager(mActivity)
                || HY_IsSimulator.isFeatures(mActivity)
                || HY_IsSimulator.checkIsNotRealPhone()) {
            HY_Log.d("HUtils--Device--->模拟器");
            map.put("brand_desc", "moniqi");
        } else {
            HY_Log.d("HUtils--Device--->真机");
            map.put("brand_desc", HY_SimResolve.model);
        }*/
        return map;
    }

    public static Map<String, String> getRequestBaseParameter() {
        return new HashMap<>();
    }

    public static Map<String, String> getRequestBaseParameter(Context mActivity) {
        return new HashMap<>();
    }


    /***
     * 手机号码登录 完成后获取平台 userid 请求参数
     * @param context
     * @param userInfoEntity
     * @return
     */
    public static Map<String, String> getLoginRequestParameter(Context context, UserInfoEntity userInfoEntity) {

        Map<String, String> param = getRequestBaseParameter(context);
        param.put("Ext", userInfoEntity.getGuid());//guid 为辉耀渠道用户标识
        param.put("ChannelId", HY_Constants.CHANNEL_CODE);
        param.put("Token", userInfoEntity.getToken());
        param.put("ProductId", HY_Constants.APPID);
        param.put("ChannelUserId", userInfoEntity.getGuid());
        param.put("MobileInfo", "");
        param.put("ChannelUserName", userInfoEntity.getUsername());
        param.put("DeviceId", HY_SimResolve.deviceId);
        param.put("Aid", HY_Constants.PLAN_ID);


        return param;

    }


}
