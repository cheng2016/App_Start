package com.huiyao.gamecenter.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;

/***
 *用户钱包界面
 */
public class UserWalletActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {

    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("我的钱包");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_wallet;
    }
}
