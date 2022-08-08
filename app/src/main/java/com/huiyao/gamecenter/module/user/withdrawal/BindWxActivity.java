package com.huiyao.gamecenter.module.user.withdrawal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.common.CommonEventMessage;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.wxapi.WxAuthHelp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @作者: chengzj
 * @创建时间: 2022/6/10 14:54
 * @类名: BindWxActivity
 * @描述: 绑定微信界面
 */
public class BindWxActivity extends BaseActivity implements BindWxContract.View{
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.edt_id_number)
    EditText edtIdNumber;
    @Bind(R.id.tv_wx_bind)
    TextView tvWxBind;
    @Bind(R.id.withdraw_btn)
    TextView withdrawBtn;
    private String wxCode;
    private BindWxPresenter bindWxPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_wx;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("绑定微信");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }




    @Override
    protected void initData() {
        new BindWxPresenter(this);
        WxAuthHelp.getInstance().regToWx(this);
    }



    @Override
    public void setPresenter(BindWxContract.Presenter presenter) {
        this.bindWxPresenter = (BindWxPresenter) presenter;
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendAuth.Resp resp) {
        if (resp == null) {
            Logger.i("微信授权成功返回信息resp为空");
            return;
        }
        wxCode = resp.code;
        tvWxBind.setText("已授权");
        Logger.i("微信授权成功返回wxcode>>>"+wxCode);
    }

    @Override
    public void notifyBindWxResult(int resultCode, String message) {
        if(resultCode==0){
            Logger.i("微信认证服务端认证成功>>>" + message);
            CommonEventMessage commonEventMessage = new CommonEventMessage();
            commonEventMessage.setTag(2);
            EventBus.getDefault().post(commonEventMessage);
            BindWxActivity.this.finish();
        }else{
            Logger.i("微信认证服务端认证失败>>>" + message);
            ToastUtils.showLong(this,message);
            wxCode = "";
            tvWxBind.setText("需重新授权");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bindWxPresenter.unsubscribe();
    }


    @OnClick({R.id.tv_wx_bind, R.id.withdraw_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wx_bind:
                if(TextUtils.isEmpty(wxCode)) {
                    WxAuthHelp.getInstance().toWxAuth();
                }else {
                    tvWxBind.setText("已绑定");
                }
                break;
            case R.id.withdraw_btn:
                if(bindWxPresenter!=null){
                    String userName = edtName.getText().toString();
                    String userNumber = edtIdNumber.getText().toString();
                    bindWxPresenter.sendWxCode(this,userName,userNumber,wxCode);
                }
                break;
        }
    }



}
