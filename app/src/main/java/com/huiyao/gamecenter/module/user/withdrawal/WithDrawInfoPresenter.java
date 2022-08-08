package com.huiyao.gamecenter.module.user.withdrawal;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.App;
import com.huiyao.gamecenter.common.CommonEventMessage;
import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.data.entity.WithDrawInfoData;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WithDrawInfoPresenter implements WithDrawInfoContract.Presenter {
    private WithDrawInfoContract.View view;
    HttpApi httpApi;
    public WithDrawInfoPresenter(WithDrawInfoContract.View view){
        this.view = view;
        view.setPresenter(this);
        httpApi = HttpFactory.getHttpApiService();
    }



    /**
     * 获取提现挡位信息
     */
    @SuppressLint("CheckResult")
    public void getWithDrawInfo(Context context){
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter();
//        if (userInfoEntity != null) {
//            params.put("guid", userInfoEntity.getGuid());
//            params.put("token", userInfoEntity.getToken());
//            Logger.d("用户不为空");
//        }
        params.put("app_id", HY_Constants.APPID);
        httpApi.getWithDrawInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("提现挡位信息接口返回数据>>>" + completeInfo);
                        if (status == 0) {
                            BaseResponseMode<WithDrawInfoData> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<WithDrawInfoData>>(){});
                            view.notifiRefreshUI(baseResponseMode.getData());
                        } else {
                            view.getWithDrawInfoFail(message);
                            Logger.i("提现挡位信息接口返回数据异常>>>" + completeInfo);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.getWithDrawInfoFail(message);
                    }
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void withdrawal(String amount) {
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter();
        params.put("amount",amount);
        httpApi.withdrawal(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("提现接口返回数据>>>" + completeInfo);
                        if (status == 0) {
                            ToastUtils.showShort("提现成功");
                             getWithDrawInfo(App.getInstance());
                             CommonEventMessage commonEventMessage = new CommonEventMessage();
                             commonEventMessage.setTag(3);
                            EventBus.getDefault().post(commonEventMessage);
                        } else {
                            Logger.i("提现接口返回数据返回数据异常>>>" + completeInfo);
                            ToastUtils.showShort("提现失败： " + message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });
    }
}
