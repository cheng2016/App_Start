package com.huiyao.gamecenter.module.splash;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.huiyao.gamecenter.data.entity.SplashData;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.main.MainActivity;
import com.huiyao.gamecenter.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: chengzj
 * @CreateDate: 2022/6/7 16:53
 * @ClassName: SplashActivity
 * @Description:
 */
public class SplashActivity extends HY_SplashActivity {

    @SuppressLint("CheckResult")
    @Override
    public void getServerSplashImage(LoadServerSplashListener callback) {
        HttpApi mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
        mHttpApi.startPage().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            SplashData data = GsonUtil.stringToBean(completeInfo, SplashData.class);
                            List<HY_SplashData> list = new ArrayList<>();
                            if (data != null && data.getData() != null && data.getData().getPage() != null && data.getData().getPage().size() > 0) {
                                for (SplashData.DataBean.PageBean bean : data.getData().getPage()) {
                                    list.add(new HY_SplashData() {
                                        @Override
                                        public String getUrl() {
                                            return bean.getImg();
                                        }

                                        @Override
                                        public String getLinkUrl() {
                                            return bean.getType() == 0 ? "" : bean.getUrl();
                                        }
                                    });
                                }
                            }
                            callback.onLoadSuccess(list);
                        } else {
                            callback.onLoadFailed();
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        callback.onLoadFailed();
                    }
                });
    }

    @Override
    public boolean hasServerSplashImage() {
        return true;
    }

    @Override
    public void onSplashStop() {
        startActivity(new Intent().setClass(SplashActivity.this, MainActivity.class));
        this.finish();
    }
}
