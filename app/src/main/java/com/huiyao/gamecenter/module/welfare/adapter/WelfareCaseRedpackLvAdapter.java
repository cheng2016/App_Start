package com.huiyao.gamecenter.module.welfare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.common.AppEventType;
import com.huiyao.gamecenter.common.AppEventUploadUtils;
import com.huiyao.gamecenter.data.entity.BonusListData;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.welfare.CaseRedpackDetailActivity;
import com.huiyao.gamecenter.util.DownloadManagerUtils;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;

import java.util.List;



/**
 * 现金礼包
 */
public class WelfareCaseRedpackLvAdapter extends BaseAdapter {
    private List<BonusListData.DataBean> list;
    private LayoutInflater mInflater;
    private Context mContext;

    private WelfareOnclickCallback onClickCallback;
    public WelfareCaseRedpackLvAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        onClickCallback = new WelfareOnclickCallback();
    }

    public WelfareCaseRedpackLvAdapter(Context context, List<BonusListData.DataBean> list) {
        this.mContext = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        onClickCallback = new WelfareOnclickCallback();

    }

    public void updateData(List<BonusListData.DataBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }



    @Override
    public int getCount() {
//        return 9;
        return null == list ? 0 : list.size();
    }

    @Override
    public BonusListData.DataBean getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.welfare_case_lv_item, null);
            viewHolder.rlItme = (RelativeLayout) convertView.findViewById(R.id.rl_item_layout);
            viewHolder.hyCaseGameIcon = (ImageView) convertView.findViewById(R.id.hy_case_game_icon);
            viewHolder.tvCaseGameName = (TextView) convertView.findViewById(R.id.tv_case_game_name);
            viewHolder.tvCaseGamePeriods = (TextView) convertView.findViewById(R.id.tv_case_game_periods);
            viewHolder.tvCaseGameDescribe = (TextView) convertView.findViewById(R.id.tv_case_game_describe);
            viewHolder.tvCaseParticipationNumber = (TextView) convertView.findViewById(R.id.tv_case_participation_number);
            viewHolder.hyRight = (LinearLayout) convertView.findViewById(R.id.hy_right);
            viewHolder.tvCaseRedpackMoney = (TextView) convertView.findViewById(R.id.tv_case_redpack_money);
            viewHolder.tvCaseGameStatus = (TextView) convertView.findViewById(R.id.tv_case_game_status);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list == null || list.size() == 0) return convertView;
        final BonusListData.DataBean bean = list.get(position);
        viewHolder.tvCaseGameName.setText(bean.getProduct_name());
        viewHolder.tvCaseGameDescribe.setText(bean.getIntroduction());

        if(onClickCallback!=null){
            Logger.i("设置点击事件");
            viewHolder.tvCaseGameStatus.setTag(position);
            viewHolder.tvCaseGameStatus.setOnClickListener(onClickCallback);
            viewHolder.rlItme.setTag(position);
            viewHolder.rlItme.setOnClickListener(onClickCallback);
        }
        if(Utils.isInstalled(mContext, bean.getPackage_name())){
            viewHolder.tvCaseGameStatus.setText("领取红包");
        }else {
            viewHolder.tvCaseGameStatus.setText("下载游戏");
        }
        if (!TextUtils.isEmpty(bean.getIcon())) {
            Glide.with(mContext).load(bean.getIcon()).into(viewHolder.hyCaseGameIcon);
        }
        viewHolder.tvCaseRedpackMoney.setText(bean.getAmount()+"元");

        return convertView;
    }


    static class ViewHolder {
        RelativeLayout rlItme;
        ImageView hyCaseGameIcon;
        TextView tvCaseGameName;
        TextView tvCaseGamePeriods;
        TextView tvCaseGameDescribe;
        TextView tvCaseParticipationNumber;
        LinearLayout hyRight;
        TextView tvCaseRedpackMoney;
        TextView tvCaseGameStatus;
    }


    class WelfareOnclickCallback implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.tv_case_game_status){
                int postion = (int) view.getTag();
                BonusListData.DataBean bean = list.get(postion);
                if (Utils.isInstalled(mContext, bean.getPackage_name())) {
                    toCaseRedpackDetailActivity(postion);
                }else {
                    DownloadManagerUtils.getInstance((Activity) mContext).download(bean.getDownload_url());
                    //下载事件上报
                    AppEventUploadUtils.getInstance(mContext).eventUpLoad(bean.getRecommend_id(), AppEventType.GAME_DOWNLOAD);
                }
            }else if(view.getId() == R.id.rl_item_layout){
                int postion = (int) view.getTag();
                toCaseRedpackDetailActivity(postion);
            }
        }
    }


    private void toCaseRedpackDetailActivity(int postion){
        if(!LoginUtils.getInstance().isLoginSucess()){
            ToastUtils.showShort(mContext,"请登录完成查看红包任务!");
            return;
        }
        BonusListData.DataBean bean = list.get(postion);
        Intent intent = new Intent(mContext,CaseRedpackDetailActivity.class);
        intent.putExtra("id",bean.getId()+"");
        intent.putExtra("recommend_id",bean.getRecommend_id());
        mContext.startActivity(intent);
    }


}
