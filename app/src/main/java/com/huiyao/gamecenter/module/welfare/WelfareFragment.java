package com.huiyao.gamecenter.module.welfare;

import android.os.Bundle;
import android.view.View;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;

public class WelfareFragment extends BaseFragment implements WelfareContract.View, View.OnClickListener {

    public static WelfareFragment newInstance() {
        WelfareFragment mainFragment = new WelfareFragment();
        new WelfarePresenter(mainFragment);
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void setPresenter(WelfareContract.Presenter presenter) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
