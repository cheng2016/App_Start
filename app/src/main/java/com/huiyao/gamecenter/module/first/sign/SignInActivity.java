package com.huiyao.gamecenter.module.first.sign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.SignInData;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;
import com.huiyao.gamecenter.view.RuleDialog;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/14 9:59
 * @描述:
 */
public class SignInActivity extends BaseActivity implements SignInContract.View {
    SignInPresenter mPresenter;
    SignGridViewAdapter mSignGridViewAdapter;

    @Bind(R.id.sign_in_head_img)
    ImageView signInHeadImg;
    @Bind(R.id.sign_in_grid_view)
    GridView signInGridView;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("签到");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        smartRefreshLayout = (SmartRefreshLayout)  findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setRefreshHeader((RefreshHeader) new BezierRadarHeader(this));
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.getSignInData();
                }
            }
        });
    }

    @Override
    protected void initData() {
        new SignInPresenter(this);
        mPresenter.getSignInData();
    }


    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = (SignInPresenter) presenter;
    }

    List<SignInData.DataBean.ListBean> mDataList;
    SignInData data;

    @Override
    public void getSignInDataSuccess(SignInData data) {
        mLoadingView.showContent();
        smartRefreshLayout.finishRefresh();
        this.data = data;
        if (!TextUtils.isEmpty(data.getData().getBanner())) {
            Picasso.with(this).load(data.getData().getBanner()).placeholder(R.drawable.ic_sign_in_banner).into(signInHeadImg);
        }
        mDataList = data.getData().getList();
        if (mDataList == null || mDataList.size() == 0) return;
        mSignGridViewAdapter = new SignGridViewAdapter(this, getList(mDataList, index));
        signInGridView.setAdapter(mSignGridViewAdapter);
        mSignGridViewAdapter.setOnClickListener(new SignGridViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                if (mSignGridViewAdapter.getList().get(position).getStatus() == 0||mSignGridViewAdapter.getList().get(position).getStatus() == 1) return;
                mPresenter.sign(mSignGridViewAdapter.getList().get(position).getStatus());
            }
        });
    }

    <T> List<T> getList(List<T> list, int index) {
        if (list == null || list.size() == 0) return null;
        int number = list.size() / 3;
        switch (index) {
            case 0:
                return list.subList(0, number);
            case 1:
                return list.subList(number, number * 2);
            case 2:
                return list.subList(number * 2, list.size());
        }
        return null;
    }

    @Override
    public void getSignInDataFail(String erroMsg) {
        smartRefreshLayout.finishRefresh();
        mLoadingView.setErrorText(erroMsg);
        mLoadingView.showError();
        mLoadingView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getSignInData();
            }
        });
    }

    @Override
    public void signSuccess(String message) {
        ToastUtils.showShort(message);
        mPresenter.getSignInData();
    }

    int position;
    int index;

    @OnClick({R.id.left, R.id.right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left:
                if (position > 0)
                    --position;
                else
                    position += 2;
                break;
            case R.id.right:
                ++position;
                break;
        }
        index = position % 3;
        radioGroup.check(Utils.getId(SignInActivity.this, "radio_" + index));
        if (mDataList == null || mDataList.size() == 0) return;
        mSignGridViewAdapter.updata(getList(mDataList, index));
    }


    @OnClick(R.id.rule_tv)
    public void onViewClicked() {
        new RuleDialog(SignInActivity.this).setMessage(data.getData().getRule()).showDialog();
    }
}
