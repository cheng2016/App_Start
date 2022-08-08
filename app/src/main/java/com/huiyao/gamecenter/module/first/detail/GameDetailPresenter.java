package com.huiyao.gamecenter.module.first.detail;

import android.annotation.SuppressLint;

import com.huiyao.gamecenter.data.entity.GameDetailBean;
import com.huiyao.gamecenter.data.source.remote.HY_UrlConstants;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/21 15:04
 * @描述:
 */
public class GameDetailPresenter implements GameDetailContract.Presenter {
    public final static String TAG = "DevicePresenter";
    private GameDetailContract.View view;
    private HttpApi mHttpApi;

    public GameDetailPresenter(GameDetailContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mHttpApi = HttpFactory.createRetrofit2(HttpApi.class);
    }

    public void getDetailData(String recommend_id, int product_id, int type) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getDetailData(int product_id) {
        mHttpApi.getGameDetailData(product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        if (status == 0) {
                            GameDetailBean data = GsonUtil.stringToBean(completeInfo, GameDetailBean.class);
                            view.loadGameDetailDataSuccess(data);
                        } else {
                            view.loadGameDetailDataFail(message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        view.loadGameDetailDataFail(message);
                    }
                });
    }

    @Override
    public void getDetail(int product_id) {
        Map<String,String> map = new HashMap<>();
        map.put("product_id", String.valueOf(product_id));
        HttpClientUtils.get(HY_UrlConstants.URL_GET_GAME_DETAIL, map, new HttpClientUtils.SimpleResponseCallback() {
            @Override
            public void onSuccess(String response) {
                GameDetailBean  data = GsonUtil.stringToBean(response,GameDetailBean.class);
                view.loadGameDetailDataSuccess(data);
            }

            @Override
            public void onFailure(Exception error) {
                view.loadGameDetailDataFail(error.getMessage());
            }
        });
    }


}
