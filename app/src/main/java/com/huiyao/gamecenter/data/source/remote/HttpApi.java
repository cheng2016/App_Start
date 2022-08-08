package com.huiyao.gamecenter.data.source.remote;

import com.huiyao.gamecenter.data.entity.NewsList;
import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by chengzj on 2017/6/18.
 */

public interface HttpApi {
    /**
     * retrofit 中 baseURL 默认域名 用 HY_UrlConstants 类中 URL_HOST 域名填充
     * retrofit 不同请求接口域名发生变化时可以 通过 请求头 headers 注解中 "UrlName"键值对指定用那个域名
     * "URL_HOST" "URL_HOST_U9API" "URL_HOST_BU" 对应 “HY_UrlConstants" 类中域名地址
     * 请求方法中参数 会自动 进行 URLEncoder 编码，无需对含有特殊字符参数编码
     */
    public static final String URL_HOST = "URL_HOST";
    public static final String URL_HOST_U9API = "URL_HOST_U9API";
    public static final String URL_HOST_BU = "URL_HOST_BU";


    @GET("list")
    Observable<NewsList> getNewsList(@Query("req_funType") String funType,
                                     @Query("req_count") String count);

    @Headers({"UrlName:" + URL_HOST_BU})
    @Streaming
    @GET
    Observable<ResponseBody> fileDownload(@Url String fileUrl);

    /**
     * 检查app版本是否更新接口
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/versionUpdate")
    @FormUrlEncoded
    Observable<String> checkAppUpdate(@FieldMap Map<String, String> parm);


    @Headers({"UrlName:" + URL_HOST})
    @POST("/hy/init")
    @FormUrlEncoded
    Observable<String> AppInitData(@FieldMap Map<String, String> parm);


    /**
     * 获取用户共享经济协议地址
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/app/userContract")
    @FormUrlEncoded
    Observable<String> getUserContract(@FieldMap Map<String, String> parm);

    /**
     * 获取手机验证码接口
     */
    @Headers({"UrlName:" + URL_HOST})
    @POST("/public/getVerifyCode")
    @FormUrlEncoded
    Observable<String> getPhoneVerifyCode(@FieldMap Map<String, String> parm);


    /**
     * 手机号登录注册
     **/
    @Headers({"UrlName:" + URL_HOST})
    @POST("/user/mobileRegister")
    @FormUrlEncoded
    Observable<String> registerPhone(@FieldMap Map<String, String> parm);


    /**
     * 极光一键登录后 获取 辉耀平台 token userid
     **/
    @Headers({"UrlName:" + URL_HOST})
    @POST("/user/mobileLogin")
    @FormUrlEncoded
    Observable<String> mobileOneKeyLogin(@FieldMap Map<String, String> parm);

    /**
     * 手机号登录后 获取 辉耀平台 userid
     **/
    @Headers({"UrlName:" + URL_HOST_U9API})
    @POST("/login/loginRequest")
    @FormUrlEncoded
    Observable<String> loginRequest(@FieldMap Map<String, String> parm);

    /**
     * 检查token 快捷登录
     **/
    @Headers({"UrlName:" + URL_HOST})
    @POST("/user/checkToken")
    @FormUrlEncoded
    Observable<String> checkToken(@FieldMap Map<String, String> parm);

    /**
     * 实名认证接口请求
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/user/realNameVerify")
    @FormUrlEncoded
    Observable<String> realNameVerify(@FieldMap Map<String, String> parm);

    /**
     * 微信认证接口请求
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/app/isAuth")
    @FormUrlEncoded
    Observable<String> wxAuth(@FieldMap Map<String, String> parm);

    /**
     * 现金红包详情页游戏信息 账号角色区服信息接口请求
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/getActivityDetail")
    @FormUrlEncoded
    Observable<String> getCashRedpackDetailData(@FieldMap Map<String, String> parm);

    /**
     * 现金红包详情页"任务列表"接口请求
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/getActivityList")
    @FormUrlEncoded
    Observable<String> getCashRedpackTaskListData(@FieldMap Map<String, String> parm);

    /**
     * 现金红包详情页"领取红包"请求
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/userReceive")
    @FormUrlEncoded
    Observable<String> getRedpaackRecive(@FieldMap Map<String, String> parm);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/startPage")
    Observable<String> startPage();

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/recommend")
    Observable<String> getFirstData();

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/getProductDetail")
    Observable<String> getGameDetailData(@Query("product_id") int product_id);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/getProductDetail")
    Observable<String> getGameDetailData(@Query("recommend_id") String recommend_id, @Query("product_id") int product_id, @Query("type") int type);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/getProductDetail")
    Observable<String> getGameDetail(@Query("product_id") int product_id);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/game")
    Observable<String> getGameListData(@Query("page") int page,
                                       @Query("page_size") int page_size);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/bonus")
    Observable<String> getBonusListData(@Query("page") int page,
                                        @Query("page_size") int page_size);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/gift")
    Observable<String> getGiftListData(@Query("page") int page,
                                       @Query("page_size") int page_size);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("platform/getGiftCode")
    Observable<String> receiveGift(@Query("gift_id") int gift_id);


    /**
     * 提现页面展示提现信息接口
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/app/withdrawalInfo")
    @FormUrlEncoded
    Observable<String> getWithDrawInfo(@FieldMap Map<String, String> parm);


    /**
     * 提现页面展示提现信息接口
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/app/myInfo")
    @FormUrlEncoded
    Observable<String> getUserInfo(@FieldMap Map<String, String> parm);


    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/app/withdrawal")
    @FormUrlEncoded
    Observable<String> withdrawal(@FieldMap Map<String, String> parm);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("app/withdrawalLog")
    Observable<String> getWithdrawalLog();


    /** app 行为数据上报接口 **/
    @Headers({"UrlName:"+URL_HOST_BU})
    @POST("/platform/visits")
    @FormUrlEncoded
    Observable<String> appEventUpload(@FieldMap Map<String,String> parm);

    /**
     * 获取邀请好友页面数据
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/invitationPage")
    @FormUrlEncoded
    Observable<String> getInviteFriendsData(@FieldMap Map<String, String> parm);

    /**
     * 校验好友验证码
     **/
    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/invitationBind")
    @FormUrlEncoded
    Observable<String> verifyInviterCode(@FieldMap Map<String, String> parm);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("/platform/signList")
    Observable<String> getSignInData();

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("/platform/scoreList")
    Observable<String> getScoreData(@Query("type") int type);

    @Headers({"UrlName:" + URL_HOST_BU})
    @POST("/platform/sign")
    Observable<String> sign(@Query("status") int status);

    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("/platform/vipList")
    Observable<String> vipList();


    @Headers({"UrlName:" + URL_HOST_BU})
    @GET("/platform/vipDetail")
    Observable<String> vipDetail(@Query("type") int t);

}
