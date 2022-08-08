package com.huiyao.gamecenter.module.first;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.FirstData;
import com.huiyao.gamecenter.util.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @Author: chengzj
 * @CreateDate: 2022/6/8 15:55
 * @ClassName: FirstGridViewAdapter
 * @Description: 首页GridView适配器
 */

public class FirstGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<FirstData.DataBean.HotGameBean> mList;

    public FirstGridViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public FirstGridViewAdapter(Context mContext, List<FirstData.DataBean.HotGameBean> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    public void updata(List<FirstData.DataBean.HotGameBean> list) {
        this.mList = list;
        this.notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_first_gridview, null);
            mViewHolder.mLayMain =   convertView.findViewById(R.id.mainview);
            mViewHolder.mIvIcon = (ImageView) convertView.findViewById(R.id.icon_img);
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList == null || mList.size() == 0) return null;

        FirstData.DataBean.HotGameBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getImg())) {
            Picasso.with(mContext).load(bean.getImg()).transform(new CircleTransformation(mContext)).into(mViewHolder.mIvIcon);
        }

        mViewHolder.mLayMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGameOnClickListener != null) onGameOnClickListener.onClick(position);
            }
        });

        mViewHolder.mTvName.setText(bean.getName());
        return convertView;
    }


    private class ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvName;
        private View mLayMain;
    }

    OnGameOnClickListener onGameOnClickListener;

    public void setOnGameClickListener(OnGameOnClickListener onClickListener) {
        this.onGameOnClickListener = onClickListener;
    }

    public interface OnGameOnClickListener {
        void onClick(int position);
    }
}
