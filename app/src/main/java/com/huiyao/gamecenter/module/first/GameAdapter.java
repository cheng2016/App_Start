package com.huiyao.gamecenter.module.first;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.FirstGameListData;

import java.util.List;




/**
 * @作者: chengzj
 * @创建时间: 2022/6/14 17:25
 * @类名: GameAdapter
 * @描述:
 */
public class GameAdapter extends BaseAdapter {
    private Context mContext;
    private List<FirstGameListData.DataBean> mList;

    public GameAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public GameAdapter(Context mContext, List<FirstGameListData.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void updataData(List<FirstGameListData.DataBean> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_first_game, null);
            viewHolder.iconImg = (ImageView) convertView.findViewById(R.id.icon_img);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.messageTv = (TextView) convertView.findViewById(R.id.message_tv);
            viewHolder.goBtn = (TextView) convertView.findViewById(R.id.go_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList == null || mList.size() == 0) return null;

        FirstGameListData.DataBean bean = mList.get(position);

        viewHolder.nameTv.setText(bean.getName());
        viewHolder.messageTv.setText(bean.getType());
        if (!TextUtils.isEmpty(bean.getIcon())) {
            Glide.with(mContext).load(bean.getIcon()).into(viewHolder.iconImg);
        }
        viewHolder.goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)listener.onClick(position);
            }
        });
        return convertView;
    }


    public void setListener(OnSeeButtonClickListener listener) {
        this.listener = listener;
    }

    OnSeeButtonClickListener listener;

    public interface OnSeeButtonClickListener{
       void  onClick(int position);
    }


    class ViewHolder {
        ImageView iconImg;
        TextView nameTv;
        TextView messageTv;
        TextView goBtn;
    }
}
