package com.huiyao.gamecenter.module.first;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.FirstData;
import com.huiyao.gamecenter.data.entity.FirstGameListData;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FirstPagePresenter implements FirstPageContract.Presenter {
    public final static String TAG = "DevicePresenter";
    private FirstPageContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public FirstPagePresenter(FirstPageContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
    }


    FirstData firstData;

    @SuppressLint("CheckResult")
    @Override
    public void getFirstData(final int page) {
        mHttpApi.getFirstData()
                .subscribeOn(Schedulers.io())
                .doOnNext(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            firstData = GsonUtil.stringToBean(completeInfo, FirstData.class);
                        }
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return mHttpApi.getGameListData(page, 10)
                                .subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            FirstGameListData data = GsonUtil.stringToBean(completeInfo, FirstGameListData.class);
                            view.refreshData(firstData, data);
                        }else {
                            view.refreshDataFail();
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.refreshDataFail();
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getGameListMoreData(final int page) {
        mHttpApi.getGameListData(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            FirstGameListData data = GsonUtil.stringToBean(completeInfo, FirstGameListData.class);
                            view.loadGameListMoreData(data);
                        } else {
                            view.loadGameListMoreDataFail();
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.loadGameListMoreDataFail();
                    }
                });
    }
}
