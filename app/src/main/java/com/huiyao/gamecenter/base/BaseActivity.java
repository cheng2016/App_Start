package com.huiyao.gamecenter.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huiyao.gamecenter.module.service.BindService;
import com.huiyao.gamecenter.util.AppManager;
import com.huiyao.gamecenter.util.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import ezy.ui.layout.LoadingLayout;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Created by chengzj on 2017/6/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements CustomAdapt  {
    public String TAG = "BaseActivity";

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 0;
    }

    protected LoadingLayout mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
//        CustomDensityUtil.setCustomDensity(this,getApplication());
        Logger.d(TAG,"onDestroy");
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //绑定服务
        Intent intent = new Intent(this, BindService.class);
        intent.putExtra("from", TAG);
//        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        AppManager.getInstance().addActivity(this);
        mLoadingView = LoadingLayout.wrap(this);
        changeStatusBarTextColor(true);
        initView(savedInstanceState);
        initData();
    }

    /**
     * 改变状态栏字体颜色值
     * @param isBlack
     */
    private void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            }else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Subscribe
    public void onEventMainThread(Object event) {
        Logger.d(TAG,"onEventMainThread");
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG,"onDestroy");
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        AppManager.getInstance().finishActivity(this);
    }
}
