package com.huiyao.gamecenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huiyao.gamecenter.util.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Created by chengzj on 2017/6/17.
 */

public abstract class BaseFragment extends Fragment implements CustomAdapt {
    public String TAG = "";

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        View view = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        Logger.d(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(TAG,"onViewCreated");
        initView(view,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG,"onActivityCreated");
        initData();
    }

    @Subscribe
    public void onEventMainThread(Object event) {
        Logger.d(TAG,"onEventMainThread");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG,"onDestroyView");
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData();
}
