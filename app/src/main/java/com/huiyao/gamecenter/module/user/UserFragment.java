package com.huiyao.gamecenter.module.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;
import com.huiyao.gamecenter.common.AppEventType;
import com.huiyao.gamecenter.common.CommonEventMessage;
import com.huiyao.gamecenter.data.entity.UserAccountStateInfoData;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.module.antiaddiction.HY_AuthenticationDialog;
import com.huiyao.gamecenter.module.antiaddiction.OpenAuthenticationDialogHelp;
import com.huiyao.gamecenter.module.first.detail.GameDetailActivity;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.user.score.UserScoreActivity;
import com.huiyao.gamecenter.module.user.wallet.UserWalletActivity;
import com.huiyao.gamecenter.module.user.withdrawal.BindWxActivity;
import com.huiyao.gamecenter.module.user.withdrawal.WithdrawalActivity;
import com.huiyao.gamecenter.module.web.CustomerWebActivity;
import com.huiyao.gamecenter.module.web.WebActivity;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的页面
 */
public class UserFragment extends BaseFragment implements UserContract.View, View.OnClickListener {
    public final static String TAG = "UserFragment";
    @Bind(R.id.hy_center_user_name)
    TextView hyCenterUserNameTv;
    @Bind(R.id.hy_center_user_phone)
    TextView hyCenterUserPhone;
    @Bind(R.id.tv_login_or_unbind)
    TextView tvLoginOrUnbind;
    @Bind(R.id.tv_user_money_title)
    TextView tvUserMoneyTitle;
    @Bind(R.id.tv_user_money)
    TextView tvUserMoney;
    @Bind(R.id.tv_withdraw)
    TextView tvWithdraw;
    @Bind(R.id.tv_user_wallet)
    TextView tvUserWallet;
    @Bind(R.id.tv_user_message)
    TextView tvUserMessage;
    @Bind(R.id.tv_customer_service)
    TextView tvCustomerService;
    @Bind(R.id.tv_bind_phone_status)
    TextView tvBindPhoneStatus;
    @Bind(R.id.rl_bind_phone)
    RelativeLayout rlBindPhone;
    @Bind(R.id.tv_authentication_status)
    TextView tvAuthenticationStatus;
    @Bind(R.id.rl_authentication)
    RelativeLayout rlAuthentication;
    @Bind(R.id.img_bind_phone_arrow)
    ImageView imgBindPhoneArrow;
    @Bind(R.id.img_authentication_arrow)
    ImageView imgAuthenticationArrow;
    @Bind(R.id.img_bind_wx_arrow)
    ImageView imgBindWxArrow;
    @Bind(R.id.tv_bind_wx_status)
    TextView tvBindWxStatus;
    @Bind(R.id.rl_bind_weixin)
    RelativeLayout rlBindWeixin;
    @Bind(R.id.img_game)
    ImageView imgGame;
    private TextView tvUserScore;


    private SmartRefreshLayout smartRefreshLayout;


    private UserInfoEntity mUserInfoEntity;
    private UserAccountStateInfoData.ImgBean imgBean;
    //是否登录
    private boolean isLogin = false;
    private boolean isBindPhone = false;
    private boolean isBindWx = false;
    private boolean isAuthentication = false;
    private String customerUrl = "";//客服链接
    private UserContract.Presenter userPresenter;

    public static UserFragment newInstance() {
        UserFragment mainFragment = new UserFragment();
        new UserPresenter(mainFragment);
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvUserScore = (TextView) view.findViewById(R.id.tv_user_score);
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setRefreshHeader((RefreshHeader) new MaterialHeader(getActivity()));
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (userPresenter != null) {
                    userPresenter.getUserInfo(getActivity());
                }
            }
        });
    }


    @Override
    protected void initData() {
        boolean isLoginSucess = LoginUtils.getInstance().isLoginSucess();
        if (isLoginSucess) {
            Logger.i("userFragment判断登录拿数据");
            mUserInfoEntity = LoginUtils.getInstance().getUser();
        } else {
            //登录
            Logger.i("userFragment判断未登录走登录");
            LoginUtils.getInstance().doLogin(getActivity(), true);
        }
        setUserInfoUI();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //清除登录状态
        LoginUtils.getInstance().clearLoginStatus();
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        userPresenter = presenter;

    }

    private void getUserInfo() {
        if (mUserInfoEntity == null) return;
        setUserInfoUI();
        //获取用户信息
        if (userPresenter != null) userPresenter.getUserInfo(getActivity());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CommonEventMessage event) {
        Logger.i("userFragment 接收到信息");
        if (event.getTag() == 1) {
            Logger.i("userFragment 接收到登录信息");
            //登录成功通知
            Object messageBody = event.getData();
            if (messageBody instanceof UserInfoEntity) {
                mUserInfoEntity = (UserInfoEntity) messageBody;
                getUserInfo();
            }
        }
        if (event.getTag() == 2) {
            //绑定微信完通知
            tvBindWxStatus.setText("已绑定");
            rlBindWeixin.setClickable(false);
            Logger.i("userFragment 接收到微信绑定信息");
        }
        if (event.getTag() == 3) {
            //提现成功 或者精美手游页面结束 通知重新获取用户信息
            Logger.i("userFragment 接收到提现信息");
            getUserInfo();
        }
    }


    /***
     * 设置用户信息相关视图
     */
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void setUserInfoUI() {
        if (mUserInfoEntity != null) {
            tvLoginOrUnbind.setText("切换账号");
            hyCenterUserNameTv.setText("用户号:" + mUserInfoEntity.getUsername());
            hyCenterUserPhone.setVisibility(View.VISIBLE);
            hyCenterUserPhone.setText("绑定手机:" + mUserInfoEntity.getMobile());
            isLogin = true;
            if (!TextUtils.isEmpty(mUserInfoEntity.getMobile())) {
                tvBindPhoneStatus.setText("已绑定");
            }
        } else {
            tvLoginOrUnbind.setText("点击登录");
            hyCenterUserNameTv.setText("未注册用户");
            hyCenterUserPhone.setVisibility(View.GONE);
        }
    }


    @Override
    public void notifityAccountStateUI(UserAccountStateInfoData userAccountStateInfoData) {
        smartRefreshLayout.finishRefresh();
        if (userAccountStateInfoData != null) {
            isBindWx = userAccountStateInfoData.isIs_wx();
            isAuthentication = userAccountStateInfoData.isIs_id();
            isBindPhone = userAccountStateInfoData.isMobile();
            if (isBindWx) {
                tvBindWxStatus.setText("已绑定");
                rlBindWeixin.setClickable(false);
            } else {
                tvBindWxStatus.setText("未绑定");
                rlBindWeixin.setClickable(true);
            }
            if (isAuthentication) {
                rlAuthentication.setClickable(false);
                tvAuthenticationStatus.setText("已认证");
            } else {
                rlAuthentication.setClickable(true);
                tvAuthenticationStatus.setText("未认证");
            }
            tvUserMoney.setText(userAccountStateInfoData.getBalance() + "元");
            tvUserScore.setText(userAccountStateInfoData.getScore());
            customerUrl = userAccountStateInfoData.getCustomer_service();
            imgBean = userAccountStateInfoData.getImg();
            setBannerImg(imgBean);
        }
    }

    @Override
    public void getUserInfoFail() {
        smartRefreshLayout.finishRefresh();
    }

    /**
     * 设置banner 图
     *
     * @param imgBean
     */
    private void setBannerImg(UserAccountStateInfoData.ImgBean imgBean) {
        if (imgBean != null) {
            Glide.with(getActivity()).load(imgBean.getImg()).into(imgGame);
        }
    }


    @OnClick({R.id.tv_login_or_unbind, R.id.tv_user_wallet, R.id.tv_user_message, R.id.tv_customer_service,
            R.id.rl_bind_phone, R.id.rl_authentication, R.id.tv_withdraw, R.id.rl_bind_weixin, R.id.img_game, R.id.tv_user_score_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_or_unbind:
                //账号登录或者切换
                if (isLogin) {
                    LoginUtils.getInstance().switchAccountLogin(getActivity());
                } else {
                    LoginUtils.getInstance().doLogin(getActivity(), false);
                }
                break;
            case R.id.tv_user_wallet:
                //跳转钱包页面
                if (isLogin) {
                    startActivity(new Intent(getActivity(), UserWalletActivity.class));
                } else {
                    ToastUtils.showShort(getActivity(), "请先登录账号！");
                }
                break;
            case R.id.tv_user_score_center:
                if (isLogin)
                    startActivity(new Intent(getActivity(), UserScoreActivity.class));
                else
                    ToastUtils.showShort(getActivity(), "请先登录账号！");
                break;
            case R.id.tv_user_message:
                break;
            case R.id.tv_customer_service:
                //跳转客服页面
                toCustomerService();
                break;
            case R.id.rl_bind_phone:
                //手机号绑定
                break;
            case R.id.rl_authentication:
                //实名认证
                if (!isAuthentication) {
                    toAuthenticationDialog();
                }
                break;
            case R.id.tv_withdraw:
                //跳转提现页面
                if (isLogin) {
                    startActivity(new Intent(getActivity(), WithdrawalActivity.class));
                } else {
                    ToastUtils.showShort(getActivity(), "请先登录账号！");
                }
                break;
            case R.id.rl_bind_weixin:
                //绑定微信
                if (isLogin) {
                    if (!isBindWx) {
                        startActivity(new Intent(getActivity(), BindWxActivity.class));
                    }
                } else {
                    ToastUtils.showShort(getActivity(), "请先登录账号！");
                }
                break;
            case R.id.img_game:
                //跳转banner图关联页面
                toBannerGameDetail(imgBean);
                break;
        }
    }


    /**
     * 跳转bannner 图关联的页面
     */
    private void toBannerGameDetail(UserAccountStateInfoData.ImgBean imgBean) {
        if (imgBean == null) {
            Logger.i("banner图imgBean为空 不跳转");
            return;
        }
        int link_type = imgBean.getLink_type();
        String url = imgBean.getUrl();
        String product_id = imgBean.getProduct_id();
        if (link_type == 0) {
            Intent intent = new Intent(getActivity(), GameDetailActivity.class);
            intent.putExtra("product_id", Integer.valueOf(product_id));
            intent.putExtra("recommend_id", imgBean.getRecommend_id() + "");
            intent.putExtra("type", AppEventType.FROM_MY_INFO);

            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("title", "游戏详情");
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }


    /**
     * 打开实名认证弹窗界面
     */
    private void toAuthenticationDialog() {
        if (!isLogin) {
            ToastUtils.showShort(getActivity(), "请先登录账号！");
            return;
        }

        OpenAuthenticationDialogHelp.toAuthenticationActivity(getActivity(), new HY_AuthenticationDialog.AuthenticationCallback() {
            @Override
            public void authenticationSuccess(String msg) {
                ToastUtils.showShort(getActivity(), msg);
                tvAuthenticationStatus.setText("已认证");
            }

            @Override
            public void authenticationFail(String msg) {
                //ToastUtils.showShort(getActivity(),msg);
            }
        }, 0);
    }


    /**
     * 打开客服页面
     */
    private void toCustomerService() {
        if (!isLogin) {
            ToastUtils.showShort(getActivity(), "请先登录账号！");
            return;
        }
        if (!TextUtils.isEmpty(customerUrl)) {
            Intent intent = new Intent(getActivity(), CustomerWebActivity.class);
            intent.putExtra("url", customerUrl);
            intent.putExtra("title", "游戏中心客服");
            startActivity(intent);
        } else {
            Logger.i("客服地址为空");
        }
    }
}
