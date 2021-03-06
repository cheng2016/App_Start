package com.huiyao.gamecenter.module.user;

import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;

import io.reactivex.disposables.CompositeDisposable;

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

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
