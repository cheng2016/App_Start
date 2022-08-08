package com.huiyao.gamecenter.module.welfare.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.common.AppEventType;
import com.huiyao.gamecenter.common.AppEventUploadUtils;
import com.huiyao.gamecenter.data.entity.GameGiftBagData;
import com.huiyao.gamecenter.util.AppUtils;
import com.huiyao.gamecenter.util.DownloadManagerUtils;
import com.huiyao.gamecenter.util.Utils;
import com.huiyao.gamecenter.view.ChildListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Created by: chengzj
 * @创建时间: 2022/6/17 14:32
 * @描述:
 */
public class GiftRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    GiftRecyclerViewAdapter instance;
    private Context mContext;
    private List<GameGiftBagData.DataBean> mList;

    private HashMap<Integer, Boolean> map = new HashMap<>();

    public GiftRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        instance = this;
    }

    public void updateData(List<GameGiftBagData.DataBean> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    private static final int DEFAULT_VIEW = 1;
    private static final int OPEN_VIEW = 2;

    @Override
    public int getItemViewType(int position) {
       if (map.get(position) == null || (!map.get(position))) {
            return DEFAULT_VIEW;
        } else if (map.get(position) != null && map.get(position)) {
            return OPEN_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case DEFAULT_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_gift_default, parent, false);
                return new DefaultViewHolder(view);
            case OPEN_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_gift_open, parent, false);
                return new OpenViewHolder(view);
        }
        return null;
    }


    public static void main(String[] args){
        Map<Integer,Boolean> map = new HashMap<>();

        map.put(1,true);
        map.put(1,false);

        map.put(2,true);
        map.put(2,false);

        map.put(1,true);

        for (Integer key : map.keySet()) {
            System.out.println("key = " + key + " value = " + map.get(key));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int parentIndex) {
        GameGiftBagData.DataBean bean = mList.get(parentIndex);
        if (holder instanceof DefaultViewHolder) {
            DefaultViewHolder viewHolder = (DefaultViewHolder) holder;

            viewHolder.tvGiftGameName.setText(bean.getProduct_name());
            viewHolder.tvCaseGameDescribe.setText(bean.getIntroduction());
            if (!TextUtils.isEmpty(bean.getIcon())) {
                Glide.with(mContext).load(bean.getIcon()).into(viewHolder.hyGiftGameIcon);
            }
            viewHolder.moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.put(parentIndex, true);
                    instance.notifyDataSetChanged();
                }
            });
            if (bean.getGift() != null && bean.getGift().size() > 0) {
                viewHolder.tvGameGiftTitle.setText(bean.getGift().get(0).getName());
                viewHolder.tvGiftGameInfo.setText(bean.getGift().get(0).getContent());
                if (bean.getGift().get(0).getIs_receive() == 0) {
                    viewHolder.viewBtn.setText("领取");
                    viewHolder.viewBtn.setBackground(mContext.getResources().getDrawable(R.drawable.shape_button_gitf_receive));
                } else {
                    viewHolder.viewBtn.setText("查看");
                    viewHolder.viewBtn.setBackground(mContext.getResources().getDrawable(R.drawable.shape_button_gift_see));
                }
                viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onChildClickListener != null)
                            onChildClickListener.onChildClick(parentIndex, 0);
                    }
                });
            }

            viewHolder.tvGiftGameMore.setText(String.format("查看更多礼包(共%d个)", bean.getGift() == null ? 0 : bean.getGift().size()));
            if (bean.getGift() == null || bean.getGift().size() == 0 || bean.getGift().size() == 1) {
                viewHolder.moreView.setVisibility(View.GONE);
            } else {
                viewHolder.moreView.setVisibility(View.VISIBLE);
            }
            isInstalled(bean.getPackage_name(), ((DefaultViewHolder) holder).tvGameDownload);

            setDefaultViewButtonOnClickListener(bean.getPackage_name(), ((DefaultViewHolder) holder).tvGameDownload, bean.getDownload_url(),bean.getRecommend_id());
        } else if (holder instanceof OpenViewHolder) {
            OpenViewHolder viewHolder = (OpenViewHolder) holder;
            viewHolder.tvGiftGameName.setText(bean.getProduct_name());
            viewHolder.tvCaseGameDescribe.setText(bean.getIntroduction());
            if (!TextUtils.isEmpty(bean.getIcon())) {
                Glide.with(mContext).load(bean.getIcon()).into(viewHolder.hyGiftGameIcon);
            }
            viewHolder.childListview.setAdapter(new ChildAdapter(mContext, parentIndex, bean.getGift(), new OnChildClickListener() {
                @Override
                public void onChildClick(int parentIndex, int childIndex) {
                    if (onChildClickListener != null)
                        onChildClickListener.onChildClick(parentIndex, childIndex);
                }
            }));
            viewHolder.moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.put(parentIndex, false);
                    instance.notifyDataSetChanged();
                }
            });
            viewHolder.tvGiftGameMore.setText(String.format("收起更多礼包(共%d个)", bean.getGift() == null ? 0 : bean.getGift().size()));
            isInstalled(bean.getPackage_name(), ((OpenViewHolder) holder).tvGameDownload);
            setDefaultViewButtonOnClickListener(bean.getPackage_name(), ((OpenViewHolder) holder).tvGameDownload, bean.getDownload_url(),bean.getRecommend_id());
        }
    }

    OnChildClickListener onChildClickListener;

    public void setOnOnChildClickListener(OnChildClickListener listener) {
        this.onChildClickListener = listener;
    }

    void isInstalled(String packageName, TextView textView) {
        if (Utils.isInstalled(mContext, packageName)) {
            textView.setText("立即打开");
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_button_gift_see));
        } else {

            textView.setText("立即下载");
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_button_gift_download));
        }
    }

    void setDefaultViewButtonOnClickListener(final String packageName, final TextView textView, final String downloadUrl,String recommend_id) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("立即打开".endsWith(textView.getText().toString().trim())) {
                    AppUtils.openOtherApp(mContext, packageName);
                } else {
                    DownloadManagerUtils.getInstance((Activity) mContext).download(downloadUrl);
                    //下载事件上报
                    AppEventUploadUtils.getInstance(mContext).eventUpLoad(recommend_id, AppEventType.GAME_DOWNLOAD);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlGameInfo;
        private ImageView hyGiftGameIcon;
        private TextView tvGiftGameName;
        private TextView tvCaseGameDescribe;
        private TextView tvGameDownload;
        private TextView tvGameGiftTitle;
        private TextView tvGiftGameInfo;
        private TextView viewBtn;
        private RelativeLayout moreView;
        private TextView tvGiftGameMore;
        private ImageView imgGiftMoreArrow;


        public DefaultViewHolder(View view) {
            super(view);
            rlGameInfo = (RelativeLayout) view.findViewById(R.id.rl_game_info);
            hyGiftGameIcon = (ImageView) view.findViewById(R.id.hy_gift_game_icon);
            tvGiftGameName = (TextView) view.findViewById(R.id.tv_gift_game_name);
            tvCaseGameDescribe = (TextView) view.findViewById(R.id.tv_case_game_describe);
            tvGameDownload = (TextView) view.findViewById(R.id.tv_game_download);
            tvGameGiftTitle = (TextView) view.findViewById(R.id.tv_game_gift_title);
            tvGiftGameInfo = (TextView) view.findViewById(R.id.tv_gift_game_info);
            viewBtn = (TextView) view.findViewById(R.id.view_btn);
            moreView = (RelativeLayout) view.findViewById(R.id.more_view);
            tvGiftGameMore = (TextView) view.findViewById(R.id.tv_gift_game_more);
            imgGiftMoreArrow = (ImageView) view.findViewById(R.id.img_gift_more_arrow);
        }
    }


    public static class OpenViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlGameInfo;
        ImageView hyGiftGameIcon;
        TextView tvGiftGameName;
        TextView tvCaseGameDescribe;
        TextView tvGameDownload;
        ChildListView childListview;
        RelativeLayout moreView;
        TextView tvGiftGameMore;
        ImageView imgGiftMoreArrow;

        public OpenViewHolder(View convertView) {
            super(convertView);
            rlGameInfo = (RelativeLayout) convertView.findViewById(R.id.rl_game_info);
            hyGiftGameIcon = (ImageView) convertView.findViewById(R.id.hy_gift_game_icon);
            tvGiftGameName = (TextView) convertView.findViewById(R.id.tv_gift_game_name);
            tvCaseGameDescribe = (TextView) convertView.findViewById(R.id.tv_case_game_describe);
            tvGameDownload = (TextView) convertView.findViewById(R.id.tv_game_download);
            childListview = (ChildListView) convertView.findViewById(R.id.child_listview);
            moreView = (RelativeLayout) convertView.findViewById(R.id.more_view);
            tvGiftGameMore = (TextView) convertView.findViewById(R.id.tv_gift_game_more);
            imgGiftMoreArrow = (ImageView) convertView.findViewById(R.id.img_gift_more_arrow);
        }
    }

    public static final class ChildAdapter extends BaseAdapter {
        private Context mContext;
        private int parentIndex;
        private List<GameGiftBagData.DataBean.GiftBean> mDataList;

        public ChildAdapter(Context mContext, int parentIndex, List<GameGiftBagData.DataBean.GiftBean> mDataList, OnChildClickListener onChildClickListener) {
            this.mContext = mContext;
            this.parentIndex = parentIndex;
            this.mDataList = mDataList;
            this.onChildClickListener = onChildClickListener;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList == null ? null : mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = LayoutInflater.from(mContext).inflate(R.layout.welfare_game_gift_exlv_children_item, null);
                viewHolder.tvGameGiftTitle = (TextView) convertView.findViewById(R.id.tv_game_gift_title);
                viewHolder.tvGiftGameInfo = (TextView) convertView.findViewById(R.id.tv_gift_game_info);
                viewHolder.viewBtn = (TextView) convertView.findViewById(R.id.view_btn);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mDataList == null || mDataList.size() == 0) return null;

            GameGiftBagData.DataBean.GiftBean bean = mDataList.get(position);
            viewHolder.tvGameGiftTitle.setText(bean.getName());
            viewHolder.tvGiftGameInfo.setText(bean.getContent());

            if (bean.getIs_receive() == 0) {
                viewHolder.viewBtn.setText("领取");
                viewHolder.viewBtn.setBackground(mContext.getResources().getDrawable(R.drawable.shape_button_gitf_receive));
            } else {
                viewHolder.viewBtn.setText("查看");
                viewHolder.viewBtn.setBackground(mContext.getResources().getDrawable(R.drawable.shape_button_gift_see));
            }
            viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onChildClickListener != null)
                        onChildClickListener.onChildClick(parentIndex, position);
                }
            });

            return convertView;
        }

        static class ViewHolder {
            TextView tvGameGiftTitle;
            TextView tvGiftGameInfo;
            TextView viewBtn;

        }

        OnChildClickListener onChildClickListener;
    }


    public interface OnChildClickListener {
        void onChildClick(int parentIndex, int childIndex);
    }
}
