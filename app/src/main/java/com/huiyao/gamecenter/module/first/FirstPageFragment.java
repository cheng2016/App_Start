package com.huiyao.gamecenter.module.first;

import android.os.Bundle;
import android.view.View;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;

/**
 * 首页页面
 */
public class FirstPageFragment extends BaseFragment implements FirstPageContract.View, View.OnClickListener {

    public static FirstPageFragment newInstance() {
        FirstPageFragment mainFragment = new FirstPageFragment();
        new FirstPagePresenter(mainFragment);
        return mainFragment;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_page;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {


    }

    @Override
    public void setPresenter(FirstPageContract.Presenter presenter) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }
}
