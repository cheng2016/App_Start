package com.huiyao.gamecenter.module.vip;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.VipInfo;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.BaseContract;
import com.huiyao.gamecenter.util.GsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/7 10:29
 * @描述:
 */
public interface VipContract {
    interface View extends BaseContract.BaseView<Presenter>{
      void   getVipInfoSuccess(VipInfo data);

        void  getVipInfoFail(String s);
    }

    interface Presenter extends BaseContract.BasePresenter{

    }

    class MyPresenter  implements Presenter{
        View view;

        public MyPresenter(View view) {
            this.view = view;
            view.setPresenter(this);
        }

        @Override
        public void clear() {

        }


        @SuppressLint("CheckResult")
        public void getVipInfo() {
            HttpFactory.getHttpApiService().vipList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HyStringConsumer() {
                        @Override
                        protected void onAcceptComplete(int status, String message, String completeInfo) {
                            if (status == 0) {
                                VipInfo data = GsonUtil.stringToBean(completeInfo, VipInfo.class);
                                view.getVipInfoSuccess(data);
                            } else {
                                view.getVipInfoFail(message);
                            }
                        }
                    }, new ThrowableConsumer() {
                        @Override
                        protected void onExceptionInfo(int code, String message) {
                            view.getVipInfoFail(message);
                        }
                    });
        }
    }
}
