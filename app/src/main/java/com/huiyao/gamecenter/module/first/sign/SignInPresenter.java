package com.huiyao.gamecenter.module.first.sign;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.SignInData;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/14 10:36
 * @描述:
 */
public class SignInPresenter implements SignInContract.Presenter{
    SignInContract.View view;
    public SignInPresenter(SignInContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void clear() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getSignInData() {
        HttpFactory.getHttpApiService().getSignInData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            SignInData data = GsonUtil.stringToBean(completeInfo, SignInData.class);
                            view.getSignInDataSuccess(data);
                        } else {
                            view.getSignInDataFail(message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.getSignInDataFail(message);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void sign(int status){
        HttpFactory.getHttpApiService().sign(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0)
                            view.signSuccess(message);
                        else
                            ToastUtils.showShort(message);
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        ToastUtils.showShort(message);
                    }
                });
    }
}
