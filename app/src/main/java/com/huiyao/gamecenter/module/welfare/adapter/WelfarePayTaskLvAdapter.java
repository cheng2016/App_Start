package com.huiyao.gamecenter.module.welfare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackTaskData;

import java.util.ArrayList;
import java.util.List;

/**
 * 区服选择适配器
 *
 */
public class WelfarePayTaskLvAdapter extends BaseAdapter {
    private Context context;
    private View.OnClickListener onClickListenerCallBack;
    private List<HyCaseRedpackTaskData.PayTaskBean> list = new ArrayList<HyCaseRedpackTaskData.PayTaskBean>();

    public WelfarePayTaskLvAdapter(Context context){
        this.context = context;
    }

    public void updateData(List<HyCaseRedpackTaskData.PayTaskBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnClickCallback(View.OnClickListener onClickListenerCallBack){
        this.onClickListenerCallBack = onClickListenerCallBack;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HyCaseRedpackTaskData.PayTaskBean getItem(int i) {
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
            convertView = View.inflate(context,R.layout.level_task_item,null);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.tv_task_amount);
            holder.tvInfo = (TextView) convertView.findViewById(R.id.tv_task_info);
            holder.tvDrwa = (TextView) convertView.findViewById(R.id.tv_task_draw);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        HyCaseRedpackTaskData.PayTaskBean payTaskBean = list.get(i);
        if(payTaskBean!=null){
            holder.tvAmount.setText(payTaskBean.getAmount()+"元");
            holder.tvInfo.setText(payTaskBean.getDescribe());
        }

        if(onClickListenerCallBack!=null) {
            holder.tvDrwa.setOnClickListener(onClickListenerCallBack);
            holder.tvDrwa.setTag(i);
        }
        int status = payTaskBean.getStatus();
        if(status==0){
            holder.tvDrwa.setText("未完成");
            holder.tvDrwa.setClickable(false);
        }
        if(status==1){
            holder.tvDrwa.setText("领取");
            holder.tvDrwa.setSelected(true);
            holder.tvDrwa.setClickable(true);
        }
        if(status == 2 ){
            holder.tvDrwa.setText("领取");
            holder.tvDrwa.setSelected(false);
            holder.tvDrwa.setClickable(false);
        }
        if(status == 3){
            holder.tvDrwa.setText("已领取");
            holder.tvDrwa.setSelected(false);
            holder.tvDrwa.setClickable(false);
        }

        return convertView;
    }





    private final class ViewHolder{

        TextView  tvAmount;
        TextView  tvInfo;
        TextView  tvDrwa;

    }



}
