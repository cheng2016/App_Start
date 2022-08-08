package com.huiyao.gamecenter.module.vip.detail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.VipDetail;
import com.huiyao.gamecenter.data.entity.VipInfo;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.web.CustomerWebActivity;

import java.util.List;

import butterknife.Bind;

/**
 * @Created by: chengzj
 * @创建时间: 2022/8/3 10:11
 * @描述:
 */
public class VipDetailActivity extends BaseActivity implements VipDetailContract.View {
    VipDetailContract.MyPresenter presenter;

    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.info)
    TextView info;
    @Bind(R.id.listview)
    ListView listview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        new VipDetailContract.MyPresenter(this);
        presenter.vipDetail(getIntent().getIntExtra("id", 0));
        VipInfo.DataBean.ListBean data = (VipInfo.DataBean.ListBean) getIntent().getExtras().getSerializable("data");
        if (data == null || TextUtils.isEmpty(data.getTitle())) return;
        ((TextView) findViewById(R.id.title)).setText(data.getTitle());
        info.setText(data.getTitle());
        Glide.with(this).load(data.getHead_img()).into(icon);
        if (data.getTitle().contains("客服") && LoginUtils.getInstance().isLoginSucess()) {
            findViewById(R.id.button).setVisibility(View.VISIBLE);
            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(VipDetailActivity.this, CustomerWebActivity.class).putExtra("url", getIntent().getStringExtra("customer_service")));
                }
            });
        }
    }

    @Override
    public void vipDetailSuccess(VipDetail data) {
        if (data == null) return;
        mLoadingView.showContent();
        listview.setAdapter(new DetailAdapter(this, data.getData()));
    }

    @Override
    public void vipDetailFail(String s) {
        mLoadingView.setErrorText(s);
        mLoadingView.showError();
        mLoadingView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.vipDetail(getIntent().getIntExtra("id", 0));
            }
        });
    }

    @Override
    public void setPresenter(VipDetailContract.Presenter presenter) {
        this.presenter = (VipDetailContract.MyPresenter) presenter;
    }


    public class DetailAdapter extends BaseAdapter {
        private Context mContext;
        private List<VipDetail.DataBean> mList;

        public DetailAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public DetailAdapter(Context mContext, List<VipDetail.DataBean> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        public void updataData(List<VipDetail.DataBean> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_vip_detail, null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.info = (TextView) convertView.findViewById(R.id.info);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mList == null || mList.size() == 0) return null;

            VipDetail.DataBean bean = mList.get(position);

            viewHolder.title.setText(bean.getTitle());
            viewHolder.info.setText(bean.getContent());
            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView info;
        }
    }
}
