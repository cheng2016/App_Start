package com.huiyao.gamecenter.module.user.wallet;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.WithdrawalLogData;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/23 15:40
 * @描述:
 */
public class UserWalletPresenter implements UserWalletContract.Presenter {
    UserWalletContract.View view;
    HttpApi mHttpApi;

    public UserWalletPresenter(UserWalletContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getWithdrawalLog() {
        mHttpApi.getWithdrawalLog().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String response) {
                        if (status == 0) {
                            WithdrawalLogData data = GsonUtil.stringToBean(response,WithdrawalLogData.class);
                            view.getWithdrawalLogSuccess(data);
                        } else {
                            ToastUtils.showShort(message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });
    }

}
