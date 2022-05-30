package com.huiyao.gamecenter.module.welfare;

import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;

import io.reactivex.disposables.CompositeDisposable;

public class WelfarePresenter implements WelfareContract.Presenter{
    public final static String TAG = "MessagePresenter";
    private WelfareContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public WelfarePresenter(WelfareContract.View view) {
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
