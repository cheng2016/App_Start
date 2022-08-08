package com.huiyao.gamecenter.module.first.sign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.SignInData;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/15 10:21
 * @描述:
 */
public class SignGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<SignInData.DataBean.ListBean> mList;

    public SignGridViewAdapter(Context mContext, List<SignInData.DataBean.ListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void updata(List<SignInData.DataBean.ListBean> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public List<SignInData.DataBean.ListBean> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sign_in, null);
            viewHolder.mainview = convertView.findViewById(R.id.mainview);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.money = (ImageView) convertView.findViewById(R.id.money);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList == null || mList.size() == 0) return null;

        SignInData.DataBean.ListBean bean = mList.get(position);


        viewHolder.number.setText(bean.getTop_content());
        Glide.with(mContext).load(bean.getIcon()).into(viewHolder.money);
        if (bean.getStatus() == 0) {//默认
            viewHolder.mainview.setBackground(mContext.getResources().getDrawable(R.drawable.shape_sign_in));
            viewHolder.day.setText(bean.getBottom_content());
        } else if (bean.getStatus() == 1) {//已签到
            viewHolder.mainview.setBackground(mContext.getResources().getDrawable(R.drawable.shape_sign_out));
            viewHolder.day.setText("已签到");
        } else if (bean.getStatus() == 2) {//补签
            viewHolder.mainview.setBackground(mContext.getResources().getDrawable(R.drawable.shape_sign_in));
            viewHolder.day.setText("补签");
        } else if (bean.getStatus() == 3) {//默认，今天未签到
            viewHolder.mainview.setBackground(mContext.getResources().getDrawable(R.drawable.shape_sign_in_today));
            viewHolder.day.setText(bean.getBottom_content());
        }


        viewHolder.mainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGameOnClickListener != null) onGameOnClickListener.onClick(position);
            }
        });

        return convertView;
    }

    OnClickListener onGameOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onGameOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    static class ViewHolder {
        View mainview;
        TextView number;
        ImageView money;
        TextView day;
    }

}
