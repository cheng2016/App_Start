package com.huiyao.gamecenter.module.first;

import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;

import io.reactivex.disposables.CompositeDisposable;

public class FirstPagePresenter implements FirstPageContract.Presenter{
    public final static String TAG = "DevicePresenter";
    private FirstPageContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public FirstPagePresenter(FirstPageContract.View view) {
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
