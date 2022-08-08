package com.huiyao.gamecenter.data.source.remote;

import android.content.Context;

import com.huiyao.gamecenter.util.AssetsConfigUtils;

/***
 * 网络请求接口数据地址常量
 */
public class HY_UrlConstants {

    public static boolean isDebug = false;

    public static String URL_HOST = ""; // 正式 api 域名
    public static String URL_HOST_U9API = ""; // 正式 u9域名
    public static String URL_HOST_BU = "";//正式 BU域名

    public static String URL_HOST_SZ = "http://api.huiyaohuyu.com"; // 正式1.
    public static String URL_HOST_U9API_SZ = "http://u9php.huiyaohuyu.com"; // 正式1.
    public static String URL_HOST_BU_SZ = "http://bu.huiyaohuyu.com";


    public static String URL_HOST_SZ_TEST = "http://test.api.huiyaohuyu.com"; // 测试1.
    public static String URL_HOST_U9API_SZ_TEST = "http://test.u9php.hyhygame.com"; // 测试1.
    public static String URL_HOST_BU_SZ_TEST = "http://test.bu.hyhygame.com";


    /**
     * 初始化接口
     */
    public static String URL_INIT = "";
    /**
     * 手机注册 - 获取验证码
     */
    public static String URL_PHONE_GET = "";
    /*** 手机注册**/
    public static String URL_PHONE_LOGIN = "";
    /**
     * 极光手机号码一键登录 后 获取平台 userid
     */
    public static String URL_MOBIE_ONEKEY_LOGIN = "";

    /** 登录地址 手机号注册/登录完成后获取 平台 userid
     *
     */
    public static String URL_LOGIN = "";

    /**
     * 检查登录结果 快捷登录
     */
    public static String URL_CHECK_TOKEN = "";
    /***
     * 实名认证接口
     */
    public static  String URL_REAL_NAME_VERIFY = "";
    /**
     * 微信绑定
     */
    public static String URL_WXAUTH = "";

    public static String URL_GET_GAME_DETAIL = "";

    public static void initURL(Context context) {
        int mode = Integer.parseInt(AssetsConfigUtils.getInstance(context).get("mode"));
        isDebug = mode == 2;
        if (isDebug) {
            URL_HOST = URL_HOST_SZ_TEST;
            URL_HOST_U9API = URL_HOST_U9API_SZ_TEST;
            URL_HOST_BU = URL_HOST_BU_SZ_TEST;
        } else {
            URL_HOST = URL_HOST_SZ;
            URL_HOST_U9API = URL_HOST_U9API_SZ;
            URL_HOST_BU = URL_HOST_BU_SZ;
        }
        URL_INIT = URL_HOST + "/hy/init";

        /** 获取验证码 */
        URL_PHONE_GET = URL_HOST + "/public/getVerifyCode";

        /** 手机注册 */
        URL_PHONE_LOGIN = URL_HOST + "/user/mobileRegister";

        /*****极光手机号一键登录***/
        URL_MOBIE_ONEKEY_LOGIN = URL_HOST + "/user/mobileLogin ";

        /** 登录地址  手机号注册/登录完成后获取 平台 userid**/

        URL_LOGIN = URL_HOST_U9API + "/login/loginRequest";

        /** 检查登录结果 */
        URL_CHECK_TOKEN = URL_HOST + "/user/checkToken";

        URL_GET_GAME_DETAIL = URL_HOST_BU + "/platform/getProductDetail";

        /**实名认证接口**/
        URL_REAL_NAME_VERIFY = URL_HOST_BU + "/user/realNameVerify";

        /*****微信授权绑定***/
        URL_WXAUTH = URL_HOST_BU + "/user/wxAuthRedPack";

    }
}
