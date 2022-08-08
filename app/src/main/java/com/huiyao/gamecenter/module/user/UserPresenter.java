package com.huiyao.gamecenter.module.user;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.data.entity.UserAccountStateInfoData;
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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter implements UserContract.Presenter {
    public final static String TAG = "UserPresenter";
    private UserContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public UserPresenter(UserContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        mHttpApi =  HttpFactory.createRetrofit2(HttpApi.class);
    }



    @SuppressLint("CheckResult")
    @Override
    public void getUserInfo(Context context) {
        LoginUtils loginUtils = LoginUtils.getInstance();
        if (!loginUtils.isLoginSucess()) {
            Logger.i("获取用户信息需登录");
            view.getUserInfoFail();
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
        httpApi.getUserInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("获取用户信息返回结果>>>" + completeInfo);
                        if(status==0){
                            BaseResponseMode<UserAccountStateInfoData> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<UserAccountStateInfoData>>(){});
                            view.notifityAccountStateUI(baseResponseMode.getData());
                        }else{
                            view.getUserInfoFail();
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.getUserInfoFail();
                    }
                });
    }
}
