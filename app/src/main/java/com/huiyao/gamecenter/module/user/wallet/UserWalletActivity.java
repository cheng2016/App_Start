package com.huiyao.gamecenter.module.user.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.WithdrawalLogData;
import com.huiyao.gamecenter.module.user.withdrawal.WithdrawalActivity;
import com.huiyao.gamecenter.view.SimpleRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/***
 *用户钱包界面
 */
public class UserWalletActivity extends BaseActivity implements UserWalletContract.View {
    UserWalletContract.Presenter mPresenter;
    private TextView tvUserMoneyTitle;
    private TextView tvUserMoney;
    private TextView tvWithdraw;
    private TextView tvWithdrawTip;
    private View lineTop;
    private RadioButton income;
    private RadioButton pay;
    private SimpleRefreshLayout hyRefreshLayout;
    private ListView lvOrderList;
    private TextView emptyText;


    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("我的钱包");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        emptyText = (TextView) findViewById(R.id.empty_text);
        tvUserMoneyTitle = (TextView) findViewById(R.id.tv_user_money_title);
        tvUserMoney = (TextView) findViewById(R.id.tv_user_money);
        tvWithdraw = (TextView) findViewById(R.id.tv_withdraw);
        tvWithdrawTip = (TextView) findViewById(R.id.tv_withdraw_tip);
        lineTop = (View) findViewById(R.id.line_top);
        income = (RadioButton) findViewById(R.id.income);
        pay = (RadioButton) findViewById(R.id.pay);
        hyRefreshLayout = (SimpleRefreshLayout) findViewById(R.id.hy_refreshLayout);
        lvOrderList = (ListView) findViewById(R.id.lv_order_list);
        hyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWithdrawalLog();
            }
        });
        tvWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserWalletActivity.this, WithdrawalActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        new UserWalletPresenter(this);
        mPresenter.getWithdrawalLog();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_wallet;
    }

    @Override
    public void getWithdrawalLogSuccess(WithdrawalLogData data) {
        mLoadingView.showContent();
        hyRefreshLayout.setRefreshing(false);
        mIncomeBeanList = data.getData().getIncome();
        mSpendingBeanList = data.getData().getSpending();
        if (isIncome) {
            setInComeView();
        } else {
            setSpendingView();
        }
        tvUserMoney.setText(data.getData().getBalance() + " 元");
    }

    @Override
    public void getWithdrawalLogFail(String message) {
        mLoadingView.setErrorText(message);
        mLoadingView.showError();
        mLoadingView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getWithdrawalLog();
            }
        });
    }

    private List<WithdrawalLogData.DataBean.IncomeBean> mIncomeBeanList = new ArrayList<>();
    private List<WithdrawalLogData.DataBean.SpendingBean> mSpendingBeanList = new ArrayList<>();

    @Override
    public void setPresenter(UserWalletContract.Presenter presenter) {
        mPresenter = presenter;
    }

    boolean isIncome = true;

    @OnClick({R.id.income, R.id.pay})
    public void onViewClicked(View view) {
        emptyText.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.income:
                setInComeView();
                break;
            case R.id.pay:
                setSpendingView();
                break;
        }
    }

    void setInComeView() {
        isIncome = true;
        lvOrderList.setAdapter(new InComeAdapter(this, mIncomeBeanList));
        if (mIncomeBeanList == null || mIncomeBeanList.size() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText("您当前没有收入");
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    void setSpendingView() {
        isIncome = false;
        lvOrderList.setAdapter(new PayAdapter(this, mSpendingBeanList));
        if (mSpendingBeanList == null || mSpendingBeanList.size() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText("您当前没有支出记录哦~");
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }
}
