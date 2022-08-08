package com.huiyao.gamecenter.module.welfare;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;
import com.huiyao.gamecenter.data.entity.BonusListData;
import com.huiyao.gamecenter.data.entity.GameGiftBagData;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.welfare.adapter.GiftRecyclerViewAdapter;
import com.huiyao.gamecenter.module.welfare.adapter.WelfareCaseRedpackLvAdapter;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.view.GiftDialog;
import com.huiyao.gamecenter.view.SimpleRefreshLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 游戏福利 现金红包 页面
 *
 */
public class WelfareFragment extends BaseFragment implements WelfareContract.View, View.OnClickListener {
    @Bind(R.id.top_menu)
    RadioGroup topMenu;
    @Bind(R.id.hy_rb_game_gift)
    RadioButton hyRbGameGift;
    @Bind(R.id.hy_rb_case_redpack)
    RadioButton hyRbCaseRedpack;
    @Bind(R.id.ll_game_gift_content)
    RelativeLayout llGameGiftContent;
    @Bind(R.id.hy_float_redpack_detail_listview)
    ListView hyFloatRedpackDetailListview;
    @Bind(R.id.hy_lv_refreshLayout)
    SimpleRefreshLayout hyLvRefreshLayout;
    @Bind(R.id.ll_case_pack_content)
    RelativeLayout llCasePackContent;
    @Bind(R.id.game_gift_recycher_view)
    RecyclerView gameGiftRecycherView;
    @Bind(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.tv_gift_pack_no_tip)
    TextView tvGiftPackNoTip;
    @Bind(R.id.tv_case_pack_no_tip)
    TextView tvCasePackNoTip;


    private WelfarePresenter mPresenter;

    public static WelfareFragment newInstance() {
        WelfareFragment mainFragment = new WelfareFragment();
        new WelfarePresenter(mainFragment);
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        //设置 Header 为 贝塞尔雷达 样式
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                resetIndexStatus();
                mPresenter.requestGameGiftListData(currentPage);
            }
        });
        //设置 Footer 为 球脉冲 样式
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                if (isAbleOnload) {
                    currentPage++;
                    mPresenter.requestGameGiftListData(currentPage);
                } else {
                    smartRefreshLayout.setEnableLoadMore(false);
                }
            }
        });

        hyFloatRedpackDetailListview.setAdapter(new WelfareCaseRedpackLvAdapter(getActivity()));
        hyLvRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetIndexStatus();
                mPresenter.requestBonusListData(currentPage);
            }
        });
        hyLvRefreshLayout.setOnLoadListener(new SimpleRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                isRefresh = false;
                if (isAbleOnload) {
                    currentPage++;
                    mPresenter.requestBonusListData(currentPage);
                } else {
                    hyLvRefreshLayout.setLoading(false);
                }
            }
        });
    }

    private boolean isRefresh = true;
    private boolean isAbleOnload = true;
    private int currentPage = 1;

    List<GameGiftBagData.DataBean> mGiftDataList;
    GiftRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void initData() {
        new WelfarePresenter(this);
        mDataList = new ArrayList<>();
        mWelfareCaseRedpackLvAdapter = new WelfareCaseRedpackLvAdapter(getActivity());
        hyFloatRedpackDetailListview.setAdapter(mWelfareCaseRedpackLvAdapter);

        mGiftDataList = new ArrayList<>();


        mRecyclerViewAdapter = new GiftRecyclerViewAdapter(getActivity());
        mRecyclerViewAdapter.setOnOnChildClickListener(new GiftRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(int parentIndex, int childIndex) {
//                ToastUtils.showShort(getContext(),"parentIndex = " + parentIndex + "   childIndex = " + childIndex);
                if (mGiftDataList.get(parentIndex).getGift().get(childIndex).getIs_receive() == 0) {
                    if (LoginUtils.getInstance().isLoginSucess()) {
                        mPresenter.receiveGift(mGiftDataList.get(parentIndex).getGift().get(childIndex).getId(), parentIndex, childIndex);
                    } else {
                        ToastUtils.showShort(getContext(), "登录成功后可领取活动礼包!");
                    }
                } else {
                    GiftDialog.getInstance(getActivity()).showDialog(mGiftDataList.get(parentIndex).getGift().get(childIndex).getCode());
                }
            }

        });
        //必须先设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        gameGiftRecycherView.setLayoutManager(layoutManager);
        //设置分割线
//        gameGiftRecycherView.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
        gameGiftRecycherView.setAdapter(mRecyclerViewAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.requestGameGiftListData(currentPage);
            }
        }, 1000);
    }

    @Override
    public void setPresenter(WelfareContract.Presenter presenter) {
        this.mPresenter = (WelfarePresenter) presenter;
    }


    @OnClick({R.id.hy_rb_game_gift, R.id.hy_rb_case_redpack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hy_rb_game_gift:
                llCasePackContent.setVisibility(View.GONE);
                llGameGiftContent.setVisibility(View.VISIBLE);
                resetIndexStatus();
                mPresenter.requestGameGiftListData(1);
                break;
            case R.id.hy_rb_case_redpack:
                llCasePackContent.setVisibility(View.VISIBLE);
                llGameGiftContent.setVisibility(View.GONE);
                resetIndexStatus();
                mPresenter.requestBonusListData(1);
                break;

        }
    }

    void resetIndexStatus() {
        isRefresh = true;
        isAbleOnload = true;
        currentPage = 1;
    }

    WelfareCaseRedpackLvAdapter mWelfareCaseRedpackLvAdapter;
    List<BonusListData.DataBean> mDataList;

    @Override
    public void loadBonusListData(BonusListData data) {
        if (isRefresh) {
            mDataList.clear();
            hyLvRefreshLayout.setRefreshing(false);
            mDataList = data.getData();
            mWelfareCaseRedpackLvAdapter.updateData(mDataList);

            setTvCasePackNoTip();
            return;
        }
        hyLvRefreshLayout.setLoading(false);
        if (data.getData() == null || data.getData().size() < 10) {
            isAbleOnload = false;
        } else {
            isAbleOnload = true;
        }
        mDataList.addAll(data.getData());
        mWelfareCaseRedpackLvAdapter.updateData(mDataList);

        setTvCasePackNoTip();

    }

    /**
     * 设置现金红包无数据时候提示
     */
    private void setTvCasePackNoTip(){
        //现金红包页面数据为空时候设置无数据提示
        if (mWelfareCaseRedpackLvAdapter.getCount() == 0) {
            tvCasePackNoTip.setVisibility(View.VISIBLE);
        }else {
            tvCasePackNoTip.setVisibility(View.GONE);
        }
    }


    @Override
    public void loadBonusListDataFail() {
        if (isRefresh)
            hyLvRefreshLayout.setRefreshing(false);
        else
            hyLvRefreshLayout.setLoading(false);

        setTvCasePackNoTip();
    }


    @Override
    public void loadGameGiftBagData(GameGiftBagData data) {
        if (smartRefreshLayout == null) return;
        if (isRefresh) {
            mGiftDataList.clear();
            mGiftDataList = data.getData();
            smartRefreshLayout.finishRefresh(200);
            mRecyclerViewAdapter.updateData(mGiftDataList);

            if (isShowGifDialog) {
                isShowGifDialog = false;
                GiftDialog.getInstance(getContext()).showDialog(mGiftDataList.get(parentIndex).getGift().get(childIndex).getCode());
            }
            setTvGiftPackNoTip();
            return;
        }

        if (data.getData() == null || data.getData().size() < 10) {
            isAbleOnload = false;
        } else {
            isAbleOnload = true;
        }
        mGiftDataList.addAll(data.getData());
        smartRefreshLayout.setEnableLoadMore(isAbleOnload);
        smartRefreshLayout.finishLoadMore(200);
        mRecyclerViewAdapter.updateData(mGiftDataList);

        setTvGiftPackNoTip();
    }


    /**
     * 设置礼包列表无数据时候提示
     */
    private void setTvGiftPackNoTip(){
        if(mRecyclerViewAdapter.getItemCount()==0){
            tvGiftPackNoTip.setVisibility(View.VISIBLE);
        }else {
            tvGiftPackNoTip.setVisibility(View.GONE);
        }
    }


    @Override
    public void loadGameGiftBagDatafail() {
        setTvGiftPackNoTip();
        if (smartRefreshLayout == null) return;
        if (isRefresh) {
            smartRefreshLayout.finishRefresh(200);
        } else {
            smartRefreshLayout.setEnableLoadMore(isAbleOnload);
            smartRefreshLayout.finishLoadMore(200);
        }
    }

    int parentIndex, childIndex;
    boolean isShowGifDialog = false;

    @Override
    public void receiveGiftSuccess(int parentIndex, int childIndex) {
        this.parentIndex = parentIndex;
        this.childIndex = childIndex;
        ToastUtils.showShort("领取成功");
        resetIndexStatus();
        mPresenter.requestGameGiftListData(1);
        isShowGifDialog = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
