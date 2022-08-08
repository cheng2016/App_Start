package com.huiyao.gamecenter.module.user.wallet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.WithdrawalLogData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/23 15:51
 * @描述:提现记录
 */
public class PayAdapter extends BaseAdapter {
    private Context context;
    private List<WithdrawalLogData.DataBean.SpendingBean> mList = new ArrayList<>();

    public PayAdapter(Context context) {
        this.context = context;
    }

    public PayAdapter(Context context, List<WithdrawalLogData.DataBean.SpendingBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void updateData(List<WithdrawalLogData.DataBean.SpendingBean> list) {
        this.mList = list;
        this.notifyDataSetChanged();
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
        WithdrawalLogData.DataBean.SpendingBean bean = mList.get(i);
        viewHolder.tvUserOrderInfo.setText("提现");
        viewHolder.tvUserOrderMoney.setText("-"+bean.getAmount() + " 元");
        viewHolder.tvUserWalletBalance.setText("钱包余额： "+bean.getBalance() + " 元");
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