package com.huiyao.gamecenter.module.welfare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackData;

import java.util.ArrayList;
import java.util.List;

/**
 * 区服选择适配器
 *
 */
public class GameZoneSelectorAdapter extends BaseAdapter {
    private Context context;
    private List<HyCaseRedpackData.RoleDataBean> dataList = new ArrayList<HyCaseRedpackData.RoleDataBean>();

    public GameZoneSelectorAdapter(Context context){
        this.context = context;
    }

    public void updateData(List<HyCaseRedpackData.RoleDataBean> list){
        this.dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public HyCaseRedpackData.RoleDataBean getItem(int i) {
        return dataList.get(i);
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
            convertView = View.inflate(context,R.layout.zone_selector_lv_itme,null);
            holder.ll_bg = (LinearLayout) convertView.findViewById(R.id.ll_bg_view);
            holder.tvZoneName = (TextView) convertView.findViewById(R.id.tv_zone_name);
            holder.tvRoleName = (TextView) convertView.findViewById(R.id.tv_role_level);
            holder.tvChecked = (TextView) convertView.findViewById(R.id.tv_checked);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        HyCaseRedpackData.RoleDataBean roleDataBean = dataList.get(i);
        if(roleDataBean != null) {
            holder.tvChecked.setVisibility(View.INVISIBLE);
            if (roleDataBean.isChecked()) {
                holder.tvChecked.setVisibility(View.VISIBLE);
                holder.ll_bg.setSelected(true);
            } else {
                holder.tvChecked.setVisibility(View.INVISIBLE);
                holder.ll_bg.setSelected(false);
            }
            holder.tvZoneName.setText(roleDataBean.getZone_name()+"");
            holder.tvRoleName.setText(roleDataBean.getRole_name()+" "+roleDataBean.getRole_level()+"级");
       }

        return convertView;
    }





    private final class ViewHolder{
        LinearLayout ll_bg;
        TextView tvZoneName;
        TextView  tvRoleName;
        TextView  tvChecked;

    }



}
