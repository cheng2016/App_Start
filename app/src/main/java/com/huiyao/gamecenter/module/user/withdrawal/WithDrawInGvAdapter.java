package com.huiyao.gamecenter.module.user.withdrawal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyao.gamecenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 区服选择适配器
 *
 */
public class WithDrawInGvAdapter extends BaseAdapter {
    private Context context;
    private List<String> list = new ArrayList<String>();

    private int checkedItmePostion = 0;
    public WithDrawInGvAdapter(Context context){
        this.context = context;
    }

    public void updateData(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setCheckItme(int checkedItmePostion){
        this.checkedItmePostion = checkedItmePostion;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder  holder = null;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.itme_withdraw_amount,null);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.tv_withdraw_amount);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String amountStr = list.get(i);
        holder.tvAmount.setText(amountStr+"元");

        if(checkedItmePostion==i){
            holder.tvAmount.setSelected(true);
        }else{
            holder.tvAmount.setSelected(false);
        }

        return convertView;
    }





    private final class ViewHolder{
        TextView  tvAmount;

    }



}
