package com.huiyao.gamecenter.module.user;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.data.entity.InviteFriendsData;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InviteFriendsPresenter implements InviteFriendsContract.Presenter {
    public final static String TAG = "InviteFriendsPresenter";
    private InviteFriendsContract.View view;
    private HttpApi mHttpApi;


    public InviteFriendsPresenter(InviteFriendsContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mHttpApi =  HttpFactory.createRetrofit2(HttpApi.class);
    }

    /*@Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
*/



    @SuppressLint("CheckResult")
    @Override
    public void getInviteData(Context context) {
        LoginUtils loginUtils = LoginUtils.getInstance();
        if (!loginUtils.isLoginSucess()) {
            Logger.i("获取用户信息需登录");
            return;
        }
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = loginUtils.getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("用户不为空");
        }
        params.put("app_id", HY_Constants.APPID);
        httpApi.getInviteFriendsData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("获取邀请好友页面数据>>>" + completeInfo);
                        if(status==0){
                            BaseResponseMode<InviteFriendsData> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<InviteFriendsData>>(){});
                            view.notifityRefreshUI(baseResponseMode.getData());
                        }else{
                            Logger.i("获取邀请好友页面数据!");
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void verifyInviterCode(Context context, String inviterCode) {
        LoginUtils loginUtils = LoginUtils.getInstance();
        if (!loginUtils.isLoginSucess()) {
            Logger.i("获取用户信息需登录");
            return;
        }
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = loginUtils.getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("用户不为空");
        }
        params.put("app_id", HY_Constants.APPID);
        params.put("invite_code",inviterCode);
        httpApi.verifyInviterCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("验证好友邀请码返回数据>>>" + completeInfo);
                        view.verifyInviterCodeResult(status,message);
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                    }
                });
    }
}
