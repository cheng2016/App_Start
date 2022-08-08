package com.huiyao.gamecenter.module.antiaddiction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.SharedPreferenceUtils;
import com.huiyao.gamecenter.util.Utils;


/**
 * Created by hanke
 *
 * 未成年用户 在线时长充值限制通用 dialog
 * Describe：防沉迷 用户在线时长 充值限制提示通用 dialog
 */
public class HY_Privacy_Agree_Dialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private TextView btn_agree;
    private TextView btn_disagree;
    private TextView tv_privacy_content;
    private Window dialogWindow;


    private PrivaceyAgreeCallback privaceyAgreeCallback;

    //标识是不是第一次展示隐私协议弹窗 key
    private String agreePrivacyKey = "agreePrivacyKey";

    @SuppressLint("ValidFragment")
        //    style引用style样式
    public HY_Privacy_Agree_Dialog(Context context, PrivaceyAgreeCallback privaceyAgreeCallback) {
            super(context, Utils.getStyleId(context,"HY_antiaddiction_Dialog"));
            this.mActivity = (Activity) context;
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            this.privaceyAgreeCallback = privaceyAgreeCallback;

            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(Utils.getLayoutId(mActivity,"hygame_privacy_agree_dialog"));
        dialogWindow = getWindow();
       initView();
       setDefaultPostion();
    }


    private void initView() {
        tv_privacy_content = (TextView) findViewById(Utils.getId(mActivity, "hy_privacy_agree_content"));
        btn_agree = (TextView) findViewById(Utils.getId(mActivity, "btn_agree"));
        btn_disagree = (TextView) findViewById(Utils.getId(mActivity, "btn_disagree"));
        btn_agree.setOnClickListener(this);
        btn_disagree.setOnClickListener(this);

        setPrivacyContentTv();
    }


    private void setPrivacyContentTv(){
        String privacyHintStr = "我们非常重视您的个人信息和隐私保护，为了保障您的个人权益，在使用前，请务必阅读";
        String t = "《用户协议》";
        String s = "《隐私协议》";
        SpannableString spanttt = new SpannableString(t);
        SpannableString spansss = new SpannableString(s);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //super.updateDrawState(ds);
                ds.setColor(Color.RED);
            }
            @Override
            public void onClick(@NonNull View view) {
                String url = "https://sdk.hyhygame.com/h5game/userAgreement.html";
                Logger.i("HY","闪屏权限提示弹窗跳转用户协议地址："+ url);
                toServiceTermActivity(url);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //super.updateDrawState(ds);
                ds.setColor(Color.RED);
            }
            @Override
            public void onClick(@NonNull View view) {
                String url = "https://res.huiyaohuyu.com/hsyjl/syyx_protocol.html";
                Logger.i("HY","闪屏权限提示弹窗跳转隐私协议地址："+ url);
                toServiceTermActivity(url);

            }
        };

        spanttt.setSpan(clickableSpan1,0,t.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spansss.setSpan(clickableSpan2,0,s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tv_privacy_content.setText(privacyHintStr);
        tv_privacy_content.append(spanttt);
        tv_privacy_content.append("和");
        tv_privacy_content.append(spansss);
        tv_privacy_content.append("\n\n\n如果您同意此协议，请点击“同意”按钮");
        tv_privacy_content.setMovementMethod(LinkMovementMethod.getInstance());//设置此方法点击事件生效
        tv_privacy_content.setHighlightColor(Color.TRANSPARENT);//设置点击后颜色
    }



    private void toServiceTermActivity(String url){
        /*Intent intent = new Intent(mActivity, HYGame_ServiceTermActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isAntiaddiction", true);
        mActivity.startActivity(intent);*/
    }





   /**
     * 判断弹窗是否显示
     * @return
     */
    public void showDialog(){
        //this.tipsType = tipsType;
        if (isShowing()){
            return;
        }

        boolean agree = getValueByKey(mActivity,agreePrivacyKey,false);
        if (agree){
            if (privaceyAgreeCallback!=null){
                privaceyAgreeCallback.agree();
            }
            return;
        }else {
            super.show();
        }
    }




    /**
     * 设置横竖屏时的默认位置
     */
    private void setDefaultPostion() {

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        if (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            lp.width = mActivity.getResources().getDisplayMetrics().widthPixels * 9/10;
        } else {
            // 横屏
            lp.width = mActivity.getResources().getDisplayMetrics().widthPixels * 2/5;
        }
        dialogWindow.setAttributes(lp);
    }




    @Override
    public void onClick(View v) {
        if (v == btn_agree) {
            setValueByKey(mActivity, agreePrivacyKey, true);
            dismiss();
            if (privaceyAgreeCallback!=null){
                privaceyAgreeCallback.agree();
            }

        }

        if (v == btn_disagree){
            setValueByKey(mActivity, agreePrivacyKey, false);
            dismiss();
            if (privaceyAgreeCallback!=null){
                privaceyAgreeCallback.disAgree();
            }

        }

    }

    public interface PrivaceyAgreeCallback{
        void agree();
        void disAgree();

    }

    /*********************sp 保存请求权限 标识******************/


    public Boolean getValueByKey(Context context, String key,
                                 Boolean defaults) {
        boolean isAgree = SharedPreferenceUtils.getPrefBoolean(context,key,false);
        return isAgree;
    }

    public void setValueByKey(Context context, String key, Boolean value) {
        SharedPreferenceUtils.setPrefBoolean(context,key,value);
    }

}
