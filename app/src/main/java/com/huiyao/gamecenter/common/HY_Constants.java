package com.huiyao.gamecenter.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.huiyao.gamecenter.util.Utils;

public class HY_Constants {

    /***assets 中 hy_game.json 文件存放外部配置 字段**/
    public static String HY_GAME_CONFIG = "hy_game.json";

    public static String APPID = "0";
    // 渠道id,推广渠道id
    public static String CHANNEL_ID = "0";
    // 渠道标识,例如:百度:110
    public static String CHANNEL_CODE = "0";
    // 渠道类型,例如百度:baidu
    public static String CHANNEL_TYPE = "";
    // 广告计划id,与 推广id进行绑定
    public static String PLAN_ID = "0";

    public static String SDK_VERSION = "2";

    //用户共享经济协议标题
    public static String contractTitle = "";
    //用户共享经济协议url
    public static String contractUrl = "";

    public static void init(Context context){
        getHYChannelType(context);
        getHYChannelCode(context);
        getHYGameId(context);
        HY_Constants.PLAN_ID = Utils.getPlanId(context);
        HY_Constants.CHANNEL_ID = Utils.getChannelId(context);
    }


    /**
     *
     * getHYChannelName(获取渠道名称)
     */
    public static String getHYChannelType(Context context) {
        String channelType = getManifestMeta(context, "HY_CHANNEL_TYPE");// 渠道名
        if(!TextUtils.isEmpty(channelType)){
            CHANNEL_TYPE = channelType;
        }
        return CHANNEL_TYPE;
    }

    /**
     *
     * getHYChannelCode(获取游戏渠道号)
     */
    public static String getHYChannelCode(Context context) {
        String channelCode = getManifestMeta(context, "HY_CHANNEL_CODE");// 渠道号
        if(!TextUtils.isEmpty(channelCode)){
            CHANNEL_CODE = channelCode;
        }
        return CHANNEL_CODE;

    }

    /**
     *
     * getHYGameId(获取游戏号)
     */
    public static String getHYGameId(Context context) {
        String appid = getManifestMeta(context, "HY_GAME_ID");// 游戏号，用于区分游戏
        if (!TextUtils.isEmpty(appid)){
            APPID = appid;
        }
        return APPID;

    }

    public static String getManifestMeta(Context context, String key) {
        String result;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), 128);

            result = appInfo.metaData.get(key) + "";// 可能为null
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("read meta " + key + " error", e);
        } catch (Exception e) {
            result = "";
            // throw new IllegalStateException("read meta " + key + " error",
            // e);
        }
        if ((result == null) || (result.length() == 0)) {
            // throw new IllegalStateException("no meta " + key + " found");
            Log.d("HY", "-->IllegalStateException(no meta " + key + " found");
        }
        if (result.equalsIgnoreCase("null")) {
            result = "";
        }
        return result;
    }


}
