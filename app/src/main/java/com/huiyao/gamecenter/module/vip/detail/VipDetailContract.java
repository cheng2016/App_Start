package com.huiyao.gamecenter.module.vip.detail;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.VipDetail;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.BaseContract;
import com.huiyao.gamecenter.util.GsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by: chengzj
 * @创建时间: 2022/8/3 10:29
 * @描述:
 */
public interface VipDetailContract {

    interface View extends BaseContract.BaseView<Presenter> {
        void  vipDetailSuccess(VipDetail D);
        void   vipDetailFail(String s);
    }

    interface Presenter extends BaseContract.BasePresenter {

    }


    public static class MyPresenter implements Presenter {
        View view;

        public MyPresenter(View view) {
            this.view = view;
            view.setPresenter(this);
        }

        @SuppressLint("CheckResult")
        public void vipDetail(int t) {
            HttpFactory.getHttpApiService().vipDetail(t)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HyStringConsumer() {
                        @Override
                        protected void onAcceptComplete(int status, String message, String completeInfo) {
                            if (status == 0) {
                                VipDetail data = GsonUtil.stringToBean(completeInfo, VipDetail.class);
                                view.vipDetailSuccess(data);
                            } else {
                                view.vipDetailFail(message);
                            }
                        }
                    }, new ThrowableConsumer() {
                        @Override
                        protected void onExceptionInfo(int code, String message) {
                            view.vipDetailFail(message);
                        }
                    });
        }

        @Override
        public void clear() {

        }
    }


}
