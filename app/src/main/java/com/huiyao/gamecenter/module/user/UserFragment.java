package com.huiyao.gamecenter.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;

/**
 * 我的页面
 */
public class UserFragment extends BaseFragment implements UserContract.View, View.OnClickListener {
    public final static String TAG = "UserFragment";

    public static UserFragment newInstance() {
        UserFragment mainFragment = new UserFragment();
        new UserPresenter(mainFragment);
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        ((TextView)view.findViewById(R.id.title)).setText("我的");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

        }
    }
}
