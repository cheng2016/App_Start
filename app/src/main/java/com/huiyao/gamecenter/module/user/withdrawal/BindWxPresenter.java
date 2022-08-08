package com.huiyao.gamecenter.module.user.withdrawal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindWxPresenter implements BindWxContract.Presenter{
    BindWxContract.View view;

    private boolean isUnsubscribe = false;
    public BindWxPresenter(BindWxContract.View view){
        this.view = view;
        view.setPresenter(this);
    }


    public void subscribe() {

    }

    public void unsubscribe() {
        isUnsubscribe = true;
    }




    /***发送微信认证 code  给服务端**/
    @SuppressLint("CheckResult")
    public void sendWxCode(final Activity mActivity,String user_name,String user_number, final String wxCode) {
        LoginUtils loginUtils = LoginUtils.getInstance();
        if (!loginUtils.isLoginSucess()) {
            ToastUtils.showShort(mActivity, "请先登录账号,再进行微信绑定!");
            return;
        }

        String reg = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)";
        String reg2 = "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
        if(TextUtils.isEmpty(user_name)||TextUtils.isEmpty(user_number)){
            ToastUtils.showShort(mActivity,"请填写用户信息！");
            return;
        }
        if(!user_number.matches(reg)){
            ToastUtils.showShort(mActivity,"请填写正确的身份证号码!");
            return;
        }

        if (TextUtils.isEmpty(wxCode)){
            ToastUtils.showShort(mActivity,"请授权微信绑定!");
            return;
        }

        UserInfoEntity userInfoEntity = loginUtils.getUser();
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(mActivity);
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("用户不为空");
        }
        params.put("real_name",user_name);
        params.put("id_card",user_number);
        params.put("code", wxCode);
        httpApi.wxAuth(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("微信认证返回数据>>>" + completeInfo);
                        if (status == 0) {
                            if(!isUnsubscribe) {
                                view.notifyBindWxResult(0, message);
                            }
                        } else {
                            if(!isUnsubscribe) {
                                view.notifyBindWxResult(status, message);
                            }
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                       /* wxCode = "";
                        tvWxBind.setText("需重新授权");*/
                       if(!isUnsubscribe) {
                           view.notifyBindWxResult(code, message);
                       }
                    }
                });
    }



}
