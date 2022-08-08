package com.huiyao.gamecenter.module.user.withdrawal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.WithDrawInfoData;
import com.huiyao.gamecenter.module.user.wallet.UserWalletActivity;
import com.huiyao.gamecenter.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @作者: chengzj
 * @创建时间: 2022/6/10 14:53
 * @类名: WithdrawalActivity
 * @描述: 提现界面
 */
public class WithdrawalActivity extends BaseActivity implements WithDrawInfoContract.View {
    WithDrawInfoPresenter mPresenter;
    @Bind(R.id.wallet_amount)
    TextView walletAmount;
    @Bind(R.id.walfare_record)
    TextView walfareRecord;
    @Bind(R.id.withdrawal_way)
    TextView withdrawalWay;
    @Bind(R.id.hy_flash_withdrawal_tv)
    TextView hyFlashWithdrawalTv;
    @Bind(R.id.hygame_lightning_gold_gridview)
    GridView hygameLightningGoldGridview;
    @Bind(R.id.withdrawal_description)
    TextView withdrawalDescription;
    @Bind(R.id.withdraw_btn)
    TextView withdrawBtn;

    private WithDrawInGvAdapter withDrawInGvAdapter;
    private List<String> withDrawAmountList = new ArrayList<String>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    private String currentAmount = "0";

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("提现");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        withDrawInGvAdapter = new WithDrawInGvAdapter(this);
        hygameLightningGoldGridview.setAdapter(withDrawInGvAdapter);
        hygameLightningGoldGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setCheckedItmeUI(i);
            }
        });

    }


    @Override
    protected void initData() {
        new WithDrawInfoPresenter(this);
        //请求提现挡位信息
        mPresenter.getWithDrawInfo(this);
    }

    @Override
    public void setPresenter(WithDrawInfoContract.Presenter presenter) {
        mPresenter = (WithDrawInfoPresenter) presenter;
    }


    @Override
    public void notifiRefreshUI(WithDrawInfoData data) {
        if(data!=null) {
            mLoadingView.showContent();
            withDrawAmountList.clear();
            withDrawAmountList.addAll(data.getAmount());
            withDrawInGvAdapter.updateData(withDrawAmountList);
            Utils.calGridViewWidthAndHeight(hygameLightningGoldGridview);
            setCheckedItmeUI(0);
            walletAmount.setText(data.getBalance()+"元");
            withdrawalDescription.setText(TextUtils.isEmpty(data.getInstructions())?"":data.getInstructions());
        }else {
            mLoadingView.showEmpty();
        }
    }

    @Override
    public void getWithDrawInfoFail(String msg) {
        mLoadingView.setErrorText(msg);
        mLoadingView.showError();
    }


    /**
     * 设置选中提现金额挡位iteme UI
     * @param checkedItmePostion
     */
    private void setCheckedItmeUI(int checkedItmePostion){
        currentAmount = withDrawAmountList.get(checkedItmePostion);
        withDrawInGvAdapter.setCheckItme(checkedItmePostion);
    }



    @OnClick({R.id.walfare_record, R.id.withdraw_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.walfare_record:
                startActivity(new Intent(this, UserWalletActivity.class));
                break;
            case R.id.withdraw_btn:
                mPresenter.withdrawal(currentAmount);
                break;
        }
    }
}
