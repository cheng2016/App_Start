package com.huiyao.gamecenter.module.user;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.InviteFriendsData;
import com.huiyao.gamecenter.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteFriendsActivity extends BaseActivity implements InviteFriendsContract.View {
    InviteFriendsContract.Presenter presenter;
    @Bind(R.id.img_invite_step)
    ImageView imgInviteStep;
    @Bind(R.id.tv_my_invite_code)
    TextView tvMyInviteCode;
    @Bind(R.id.tv_copy_code)
    TextView tvCopyCode;
    @Bind(R.id.bth_share_friends)
    Button bthShareFriends;
    @Bind(R.id.tv_friend_invite_code)
    EditText tvFriendInviteCode;
    @Bind(R.id.bth_friend_confrim)
    Button bthFriendConfrim;
    @Bind(R.id.tv_invite_rule)
    TextView tvInviteRule;
    @Bind(R.id.img_friends_headimg)
    ImageView imgFriendsHeadimg;
    @Bind(R.id.tv_invite_friend_name)
    TextView tvInviteFriendName;
    @Bind(R.id.rl_invite_code_bind_sucess)
    RelativeLayout rlInviteCodeBindSucess;

    private InviteFriendsData inviteFriendsData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("邀请好友");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        new InviteFriendsPresenter(this);


    }


    @Override
    public void notifityRefreshUI(InviteFriendsData inviteFriendsData) {
        if (inviteFriendsData != null) {
            this.inviteFriendsData = inviteFriendsData;
            tvMyInviteCode.setText(inviteFriendsData.getInvite_code());
            tvInviteRule.setText(inviteFriendsData.getContent());
            Glide.with(this).load(inviteFriendsData.getImg()).into(imgInviteStep);
            if(inviteFriendsData.getIs_bind()==0){
                tvFriendInviteCode.setVisibility(View.VISIBLE);
                rlInviteCodeBindSucess.setVisibility(View.GONE);
                bthFriendConfrim.setText("确定");
                bthFriendConfrim.setEnabled(true);
            }else{
                tvFriendInviteCode.setVisibility(View.GONE);
                rlInviteCodeBindSucess.setVisibility(View.VISIBLE);
                tvInviteFriendName.setText(inviteFriendsData.getUsername()+"");
                bthFriendConfrim.setText("绑定成功");
                bthFriendConfrim.setEnabled(false);

            }

        }

    }

    /**
     * 验证好友验证码返回结果
     *
     * @param status
     * @param message
     */
    @Override
    public void verifyInviterCodeResult(int status, String message) {
        if (status == 0) {
            ToastUtils.showShort(this, message);
            presenter.getInviteData(this);
        } else {
            ToastUtils.showShort(this, message);
        }
    }

    @Override
    public void setPresenter(InviteFriendsContract.Presenter presenter) {
        this.presenter = presenter;
        //获取邀请页面数据
        presenter.getInviteData(this);

    }


    @OnClick({R.id.tv_copy_code, R.id.bth_share_friends, R.id.bth_friend_confrim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_copy_code:
                //复制个人邀请码
                copyMyInviteFriendCode();
                break;
            case R.id.bth_share_friends:
                //分享app 链接
                copyShareLink();
                break;
            case R.id.bth_friend_confrim:
                //校验好友邀请码
                verifyInviterCode();
                break;
        }
    }


    /**
     * 填写好友邀请码并验证
     */
    private void verifyInviterCode() {
        String friendInviterCode = tvFriendInviteCode.getEditableText().toString();
        if (TextUtils.isEmpty(friendInviterCode)) {
            ToastUtils.showShort(this, "请填写好友分享的邀请码!");
            return;
        }
        presenter.verifyInviterCode(this, friendInviterCode);
    }


    /**
     * 复制我的
     */
    private void copyMyInviteFriendCode() {
        if (inviteFriendsData != null) {
            if (!TextUtils.isEmpty(inviteFriendsData.getInvite_code())) {
                copyText(inviteFriendsData.getInvite_code());
                ToastUtils.showShort(this, "复制邀请码成功!");
            }
        } else {
            ToastUtils.showShort(this, "获取邀请码失败!");
        }
    }

    /**
     * 分享邀请链接
     */
    private void copyShareLink() {
        if (inviteFriendsData != null) {
            if (!TextUtils.isEmpty(inviteFriendsData.getJump_url())) {
                copyText(inviteFriendsData.getJump_url());
                ToastUtils.showShort(this, "复制分享链接成功!");
            }
        } else {
            ToastUtils.showShort(this, "复制分享链接失败!");
        }

    }


    /**
     * 复制文本到剪切板
     */
    private void copyText(String setClipboardData) {
        ClipboardManager cm = (ClipboardManager) this.getSystemService(this.CLIPBOARD_SERVICE);
        // 将文本数据复制到剪贴板
        cm.setText(setClipboardData);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
