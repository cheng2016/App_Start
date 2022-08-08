package com.huiyao.gamecenter.module.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.App;
import com.huiyao.gamecenter.common.CommonEventMessage;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.SharedPreferenceConstants;
import com.huiyao.gamecenter.util.SharedPreferenceUtils;
import com.huiyao.gamecenter.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.VerifyListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginUtils {
    private Activity mActivity;
    private static LoginUtils loginUtils;
    private HY_LoginCallBack hyLoginCallBack;
    private UserInfoEntity mUserInfoEntity;
    private boolean isLoginSucess = false;
    /***
     * 手机号码 一键登录失败2次则打开普通登录
     */
    /***
     * 手机号码 一键登录失败2次则打开普通登录
     */
    int oneKeyLoginFailNum = 0;

    int other_login_code = 0;

    private LoginUtils(){

    }

    public static LoginUtils getInstance(){
        if (loginUtils == null){
            loginUtils = new LoginUtils();
        }
        return loginUtils;
    }


    /***
     * 登录方法
     * @param activity
     * @param onlyAutoLogin  为true 时 只判断是否有用户信息缓存走快捷登录
     */
    public void doLogin(final Activity activity,boolean onlyAutoLogin){
        if(isLoginSucess){
            Logger.i("LoginUtils:dologin已经登录成功无需再登录");
            return;
        }
        this.mActivity = activity;
        if(hyLoginCallBack==null){
            hyLoginCallBack = new HY_LoginCallBack() {
                @Override
                public void onLoginSuccess(UserInfoEntity user) {
                    Logger.i("登录成功拿到用户信息>>>"+user.toString());
                    //ToastUtils.showShort(activity,"登录成功!");
                    isLoginSucess = true;
                    //保存用户信息
                    SaveUserInfo(activity,user);
                    //evenbus 发送信息
                    postLoginSucceedUserInfo(user);

                }

                @Override
                public void onLoginFailed(int code, String message) {
                    SharedPreferenceUtils.setPrefString(App.getInstance(),SharedPreferenceConstants.USER_TOKEN,"");
                    SharedPreferenceUtils.getPrefString(App.getInstance(),SharedPreferenceConstants.USER_GUID,"");
                    isLoginSucess = false;
                }
                @Override
                public void onLogout() {

                }
            };
        }


        if(onlyAutoLogin){
            autoLogin(activity);
            Logger.i("HY","只走无感快捷登录,没有登录信息缓存则不登录");
            return;
        }

       if (autoLogin(activity)){
           Logger.i("HY","有用户信息缓存则走无感快捷登录");
           return;
       }
        if(RomUtil.isOppo()){
            //oppo 手机打开普通登录
            Logger.i("HY","判断为oppo手机");
            //sdk 普通登录
            openCommonLoginDialog(activity);
        }else{
            //极光 本机号码一键登录
            Logger.i("HY","非oppo手机");
            JerificationOnkeyLogin(activity);

        }
    }


    /***
     * 切换账号登录
     */
    public void switchAccountLogin(final Activity activity){
        this.mActivity = activity;
        if(hyLoginCallBack==null){
            hyLoginCallBack = new HY_LoginCallBack() {
                @Override
                public void onLoginSuccess(UserInfoEntity user) {
                    Logger.i("切换账号登录成功拿到用户信息>>>"+user.toString());
                    //ToastUtils.showShort(activity,"登录成功!");
                    isLoginSucess = true;
                    //保存用户信息
                    SaveUserInfo(activity,user);
                    //evenbus 发送信息
                    postLoginSucceedUserInfo(user);

                }

                @Override
                public void onLoginFailed(int code, String message) {

                    isLoginSucess = false;
                }
                @Override
                public void onLogout() {

                }
            };
        }

        if(RomUtil.isOppo()){
            //oppo 手机打开普通登录
            Logger.i("HY","判断为oppo手机");
            //sdk 普通登录
            openCommonLoginDialog(activity);
        }else{
            //极光 本机号码一键登录
            Logger.i("HY","非oppo手机");
            JerificationOnkeyLogin(activity);

        }



    }


    /**
     * 通过 EvenBus 发送 登录成功后的用户信息
     * @param user
     * 在需要的页面注册 evenBus 接收 信息
     *
     */
    private void postLoginSucceedUserInfo(UserInfoEntity user){
        CommonEventMessage eventMessage = new CommonEventMessage();
        eventMessage.setTag(1);
        eventMessage.setData(user);
        EventBus.getDefault().post(eventMessage);
    }


    /**
     * 判断是否登录成功
     */
    public boolean isLoginSucess(){

        return isLoginSucess;
    }

    public void setLoginSucess(boolean loginSucess) {
        isLoginSucess = loginSucess;
    }



    public UserInfoEntity getUser() {
        return mUserInfoEntity;
    }


    /**
     * 存储用户信息
     **/
    private void SaveUserInfo(Context context,UserInfoEntity mUser) {
        if (mUser != null) {
            try {
                Logger.d("登录成功返回接口中存储用户信息");
                mUserInfoEntity = mUser;
                SharedPreferenceUtils.setPrefString(context,SharedPreferenceConstants.USER_UID , mUser.getUid());
                SharedPreferenceUtils.setPrefString(context, SharedPreferenceConstants.USER_ID, mUser.getUserId());//平台用户标识只有手机号验证码登录完返回
                SharedPreferenceUtils.setPrefString(context, SharedPreferenceConstants.USER_GUID, mUser.getGuid());//渠道用户标识
                SharedPreferenceUtils.setPrefString(context, SharedPreferenceConstants.USER_TOKEN, mUser.getToken());
                SharedPreferenceUtils.setPrefString(context, SharedPreferenceConstants.USER_NAME, mUser.getUsername());
                SharedPreferenceUtils.setPrefString(context, SharedPreferenceConstants.USER_MOBILE, mUser.getMobile());
            } catch (Exception e) {
                e.printStackTrace();
                Logger.d("手机号码一键登录完成储存用户信息时发生异常");
            }
        }else{
            ToastUtils.showShort(mActivity,"用户信息存储异常请重新登录");
        }
    }


    private UserInfoEntity createUserInfo(Context context){
        UserInfoEntity mUserInfoEntity = new UserInfoEntity();
        String uid = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_UID,"");
        String userId = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_ID,"");
        String guid = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_GUID,"");
        String token = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_TOKEN,"");
        String userName = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_NAME,"");
        String userMobile = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_MOBILE,"");
        mUserInfoEntity.setUid(uid);
        mUserInfoEntity.setUserId(userId);
        mUserInfoEntity.setGuid(guid);
        mUserInfoEntity.setToken(token);
        mUserInfoEntity.setUsername(userName);
        mUserInfoEntity.setMobile(userMobile);
        return mUserInfoEntity;
    }



    /**
     * 是否自动登录
     */
    private boolean autoLogin(Context context){
        String token = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_TOKEN,"");
        String guid = SharedPreferenceUtils.getPrefString(context,SharedPreferenceConstants.USER_GUID,"");
        if(!TextUtils.isEmpty(token)&&!TextUtils.isEmpty(guid)){
            autoLoginRequest(context,token,guid);
            return true;
        }
        return false;
    }




    /***
     * 极光本机号码一键登录
     */
    private void JerificationOnkeyLogin(final Activity activity){
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(activity);
        if (oneKeyLoginFailNum>1||!verifyEnable){
            //极光手机号一键登录失败2次 则下次打开 sdk 普通登录
            Logger.i("HY","一键登录失败次数大于1次或者网络环境不允许");
            openCommonLoginDialog(activity);
            return;
        }
        HY_JverificationHelp.JerificationLoginAuth(activity, new AuthPageEventListener() {
            @Override
            public void onEvent(int cmd, String msg) {
                if (cmd == 10001){
                    //其他方式登录
                    other_login_code = 10001;
                    openCommonLoginDialog(activity);
                }
            }
        }, new VerifyListener() {
            @Override
            public void onResult(int code, String content, String operator) {
                if (code == 6000){
                    Logger.i("HY", "code=" + code + ", token=" + content+" ,operator="+operator);
                    getOnkeyLoginToken(mActivity,content);
                }else if (code == 6001){
                    Logger.i("HY","code=6001"+"，获取login_token失败打开普通登录方式");
                    openCommonLoginDialog(activity);
                }
                else if(code == 6002){
                    //点击其他登录按钮 也会走到 6002 返回码中
                    if (other_login_code == 10001){
                        //点击其他登录方式 onEvent 事件 cmd  会返回 10001 之后，也会走到6002中 ，这个时候不需要给 cp 返回登录取消回调
                        Logger.i("HY","code=6002"+"，其他方式登录");
                        other_login_code = 0;
                    }else{
                        Logger.i("HY","code=6002"+"，取消手机号一键登录");
                    }
                }else if (code == 6004){
                    Logger.i("HY","code="+code+"message="+content+",operator="+operator+"正在登录中");
                } else{
                    Logger.i("HY","code="+code+"message="+content+",operator="+operator);
                    ToastUtils.showLong(activity,"code=" + code + ", message=" + content+",operator="+operator);
                    oneKeyLoginFailNum = oneKeyLoginFailNum +1;
                }
            }
        });
    }


    /***
     * 极光一键登录成功后 获取 辉耀平台 token ，userid
     */
    @SuppressLint("CheckResult")
    private void getOnkeyLoginToken(final Context context, String jgtoken){
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> param =  RequestParameterUtils.getRequestBaseParameter(context);
        param.put("login_token", jgtoken);

        httpApi.mobileOneKeyLogin(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("极光登录完成获取token返回数据>>>"+completeInfo);
                        if(status==0){
                            BaseResponseMode<UserInfoEntity> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<UserInfoEntity>>(){});
                            UserInfoEntity userInfoEntity = baseResponseMode.getData();
                            if(hyLoginCallBack!=null){
                                hyLoginCallBack.onLoginSuccess(userInfoEntity);
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


    /**
     * 自动登录 请求
     */
    @SuppressLint("CheckResult")
    private void autoLoginRequest(final Context context, String token, String guid){
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> param =  RequestParameterUtils.getRequestBaseParameter(context);
        param.put("token", token);
        param.put("guid", guid);

        httpApi.checkToken(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("自动登录返回数据>>>"+completeInfo);
                        if(status==0){
                          Logger.i("快捷无感登录成功!");
                          //构建用户信息实体类
                            if(hyLoginCallBack!=null){
                                hyLoginCallBack.onLoginSuccess(createUserInfo(context));
                            }
                        }else{
                            hyLoginCallBack.onLoginFailed(status,message);
                            ToastUtils.showShort(context,message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        hyLoginCallBack.onLoginFailed(code,message);
                    }
                });
    }




    /**
     *
     *打开常规手机号 验证码登录
     **/
    private void openCommonLoginDialog(Activity paramActivity){
        CommonPhoneLoginDialog.getInstance(paramActivity).showDialog(hyLoginCallBack);
    }


    /**
     * 清除登录状态
     */
    public void clearLoginStatus(){
        isLoginSucess = false;
        mUserInfoEntity = null;

    }


}
