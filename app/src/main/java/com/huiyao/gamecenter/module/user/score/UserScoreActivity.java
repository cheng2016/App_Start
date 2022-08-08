package com.huiyao.gamecenter.module.user.score;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.ScoreData;
import com.huiyao.gamecenter.view.SimpleRefreshLayout;

import java.util.List;

import butterknife.OnClick;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/19 11:16
 * @描述:
 */
public class UserScoreActivity extends BaseActivity implements UserScoreContract.View {
    private TextView tvUserScore;
    private TextView tvUserAllScore;
    private SimpleRefreshLayout hyRefreshLayout;
    private ListView lvOrderList;
    private TextView emptyText;

    UserScorePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_score;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("我的积分");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvUserScore = (TextView) findViewById(R.id.tv_user_score);
        tvUserAllScore = (TextView) findViewById(R.id.tv_user_all_score);

        hyRefreshLayout = (SimpleRefreshLayout) findViewById(R.id.hy_refreshLayout);
        lvOrderList = (ListView) findViewById(R.id.lv_order_list);
        emptyText = (TextView) findViewById(R.id.empty_text);

        hyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getScoreData(isIncome);
            }
        });
    }

    int isIncome;
    @Override
    protected void initData() {
        new UserScorePresenter(this);
        mPresenter.getScoreData(isIncome);
    }

    @OnClick({R.id.income, R.id.pay})
    public void onViewClicked(View view) {
        emptyText.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.income:
                mPresenter.getScoreData(isIncome = 0);
                break;
            case R.id.pay:
                mPresenter.getScoreData(isIncome = 1);
                break;
        }
    }

    @Override
    public void setPresenter(UserScoreContract.Presenter presenter) {
        mPresenter = (UserScorePresenter) presenter;
    }

    List<ScoreData.DataBean.ListBean> mDataList;
    ScoreAdapter scoreAdapter;

    @Override
    public void getScoreDataSuccess(ScoreData data) {
        mLoadingView.showContent();
        hyRefreshLayout.setRefreshing(false);
        mDataList = data.getData().getList();
        tvUserScore.setText(data.getData().getUser_score());
        tvUserAllScore.setText(data.getData().getSum_score());
        setEmptyView(isIncome);
    }

    void setEmptyView(int isIncome) {
        lvOrderList.setAdapter(scoreAdapter = new ScoreAdapter(this, mDataList, isIncome));
        if (scoreAdapter.getDataList() == null || scoreAdapter.getDataList().size() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(isIncome == 0 ? "您当前没有收入" : "您当前没有支出记录哦~");
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    @Override
    public void getScoreDataFail(String message) {
        mLoadingView.setErrorText(message);
        mLoadingView.showError();
        mLoadingView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getScoreData(isIncome);
            }
        });
    }

    private static class ScoreAdapter extends BaseAdapter {
        private Context context;
        private List<ScoreData.DataBean.ListBean> mList;
        private int isIncome;

        public List<ScoreData.DataBean.ListBean> getDataList() {
            return mList;
        }

        ScoreAdapter(Context context, List<ScoreData.DataBean.ListBean> mList, int isIncome) {
            this.context = context;
            this.mList = mList;
            this.isIncome = isIncome;
        }

        @Override
        public int getCount() {
            return null == mList ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            if (mList != null && mList.size() > position) {
                return mList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.user_order_list_item, null);
                viewHolder.tvUserOrderInfo = (TextView) convertView.findViewById(R.id.tv_user_order_info);
                viewHolder.tvUserOrderTime = (TextView) convertView.findViewById(R.id.tv_user_order_time);
                viewHolder.tvUserOrderMoney = (TextView) convertView.findViewById(R.id.tv_user_order_money);
                viewHolder.tvUserWalletBalance = (TextView) convertView.findViewById(R.id.tv_user_wallet_balance);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mList == null || mList.size() == 0) return null;
            ScoreData.DataBean.ListBean bean = mList.get(i);
            viewHolder.tvUserOrderInfo.setText(bean.getType_name());
            viewHolder.tvUserOrderMoney.setText(bean.getScore());
            viewHolder.tvUserWalletBalance.setText("积分余额： " + bean.getUser_score());
            viewHolder.tvUserOrderTime.setText(bean.getCreate_time());
            return convertView;
        }


        private final class ViewHolder {
            TextView tvUserOrderInfo;
            TextView tvUserOrderTime;
            TextView tvUserOrderMoney;
            TextView tvUserWalletBalance;
        }
    }
}
