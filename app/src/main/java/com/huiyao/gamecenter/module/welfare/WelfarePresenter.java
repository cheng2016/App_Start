package com.huiyao.gamecenter.module.welfare;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.BonusListData;
import com.huiyao.gamecenter.data.entity.GameGiftBagData;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WelfarePresenter implements WelfareContract.Presenter {
    public final static String TAG = "MessagePresenter";
    private WelfareContract.View view;
    private HttpApi mHttpApi;
    private CompositeDisposable mCompositeDisposable;

    public WelfarePresenter(WelfareContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
    }


    @SuppressLint("CheckResult")
    @Override
    public void requestBonusListData(int page) {
        mHttpApi.getBonusListData(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            BonusListData data = GsonUtil.stringToBean(completeInfo, BonusListData.class);
                            view.loadBonusListData(data);
                        } else {
                            view.loadBonusListDataFail();
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.loadBonusListDataFail();
                    }
                });
    }



    @SuppressLint("CheckResult")
    @Override
    public void requestGameGiftListData(int page) {
        mHttpApi.getGiftListData(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            GameGiftBagData data = GsonUtil.stringToBean(completeInfo, GameGiftBagData.class);
                            view.loadGameGiftBagData(data);
                        } else {
                            view.loadGameGiftBagDatafail();
//                            ToastUtils.showShort(message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.loadGameGiftBagDatafail();
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void receiveGift(int gift_id,int parentIndex,int childIndex) {
        mHttpApi.receiveGift(gift_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0)
                            view.receiveGiftSuccess(parentIndex,childIndex);
                        else
                            ToastUtils.showShort("领取失败 " + message);
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        ToastUtils.showShort("领取失败 " + message);
                    }
                });
    }
}
