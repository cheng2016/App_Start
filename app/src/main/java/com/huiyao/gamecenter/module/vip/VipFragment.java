package com.huiyao.gamecenter.module.vip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;
import com.huiyao.gamecenter.common.CommonEventMessage;
import com.huiyao.gamecenter.data.entity.VipInfo;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.vip.detail.VipDetailActivity;
import com.huiyao.gamecenter.module.vip.my.MyVipActivity;
import com.huiyao.gamecenter.util.ToastUtils;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/7 10:23
 * @描述:
 */
public class VipFragment extends BaseFragment implements VipContract.View {
    VipContract.MyPresenter mPresenter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.hy_user_level)
    ImageView hyUserLevel;
    @Bind(R.id.hy_center_user_info)
    TextView hyCenterUserInfo;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.hy_header_img)
    ImageView hyHeaderImg;
    @Bind(R.id.user_layout)
    LinearLayout userLayout;
    @Bind(R.id.unlogin_tv)
    TextView unloginTv;
    @Bind(R.id.detail_btn)
    Button detailBtn;

    private SmartRefreshLayout smartRefreshLayout;

    public static VipFragment newInstance() {
        VipFragment mainFragment = new VipFragment();
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vip;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setRefreshHeader((RefreshHeader) new MaterialHeader(getActivity()));
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.getVipInfo();
                }
            }
        });
    }

    VipRecyclerViewAdapter adapter;

    @Override
    protected void initData() {
        new VipContract.MyPresenter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VipRecyclerViewAdapter(getActivity());
        adapter.setOnChildClickListener(new VipRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(int i) {
                if (mVipInfo == null || mVipInfo.getData() == null) return;
                Intent intent = new Intent().setClass(getActivity(), VipDetailActivity.class)
                        .putExtra("id", mVipInfo.getData().getList().get(i).getId())
                        .putExtra("customer_service",mVipInfo.getData().getCustomer_service());
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) mVipInfo.getData().getList().get(i));
                startActivity(intent.putExtras(bundle));
            }
        });
        recyclerView.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getVipInfo();
            }
        }, 800);
    }

    @Override
    public void setPresenter(VipContract.Presenter presenter) {
        mPresenter = (VipContract.MyPresenter) presenter;
    }

    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        if (event instanceof CommonEventMessage)
            mPresenter.getVipInfo();
    }

    VipInfo mVipInfo;

    @Override
    public void getVipInfoSuccess(VipInfo data) {
        smartRefreshLayout.finishRefresh();
        mVipInfo = data;
        if (mVipInfo == null || mVipInfo.getData() == null) return;
        adapter.update(data.getData().getList());
        Glide.with(getActivity()).load(data.getData().getVip_img()).placeholder(R.drawable.ic_vip_level).into(hyUserLevel);
        hyCenterUserInfo.setText(String.format("当前成长值： %d", data.getData().getGrouth()));
        progressBar.setProgress(data.getData().getGrouth());
        if (LoginUtils.getInstance().isLoginSucess()) {
            unloginTv.setVisibility(View.GONE);
            userLayout.setVisibility(View.VISIBLE);
            hyHeaderImg.setImageResource(R.drawable.personal_avatar);
            detailBtn.setBackground(getResources().getDrawable(R.drawable.shape_vip_detail_button));
        } else {
            unloginTv.setVisibility(View.VISIBLE);
            userLayout.setVisibility(View.GONE);
            hyHeaderImg.setImageResource(R.drawable.ic_default_head);
            detailBtn.setBackground(getResources().getDrawable(R.drawable.shape_vip_detail_unlogin_button));
        }
    }

    @Override
    public void getVipInfoFail(String s) {
        smartRefreshLayout.finishRefresh();
    }


    @OnClick(R.id.detail_btn)
    public void onViewClicked() {
        if (!LoginUtils.getInstance().isLoginSucess()) {
            ToastUtils.showShort("请先登录账号");
            return;
        }
        if (mVipInfo == null || mVipInfo.getData() == null) return;
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) mVipInfo.getData());
        startActivity(new Intent().setClass(getActivity(), MyVipActivity.class).putExtras(bundle));
    }

    public static class VipRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<VipInfo.DataBean.ListBean> mList;
        Context context;

        public VipRecyclerViewAdapter(Context context) {
            this.context = context;

        }

        public void update(List list) {
            if (list == null) return;
            mList = list;
            this.notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vip_tequan, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onChildClickListener != null)
                        onChildClickListener.onChildClick(position);
                }
            });
            if (mList == null || mList.size() == 0) return;
            Glide.with(context).load(mList.get(position).getImg()).into(viewHolder.imageView);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        OnChildClickListener onChildClickListener;

        public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
            this.onChildClickListener = onChildClickListener;
        }

        public interface OnChildClickListener {
            void onChildClick(int index);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View convertView) {
            super(convertView);
            imageView = convertView.findViewById(R.id.icon);
        }
    }
}
