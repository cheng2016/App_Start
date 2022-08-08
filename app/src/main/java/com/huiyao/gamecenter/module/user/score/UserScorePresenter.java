package com.huiyao.gamecenter.module.user.score;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.ScoreData;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/19 11:18
 * @描述:
 */
public class UserScorePresenter implements UserScoreContract.Presenter {

    UserScoreContract.View view;

    public UserScorePresenter(UserScoreContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void clear() {

    }

    @SuppressLint("CheckResult")
    public void getScoreData(int isIncome) {
        HttpFactory.getHttpApiService().getScoreData(isIncome)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            ScoreData data = GsonUtil.stringToBean(completeInfo, ScoreData.class);
                            view.getScoreDataSuccess(data);
                        } else {
                            view.getScoreDataFail(message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.getScoreDataFail(message);
                    }
                });
    }
}
