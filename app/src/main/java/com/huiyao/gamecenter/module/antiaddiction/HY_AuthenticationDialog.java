package com.huiyao.gamecenter.module.antiaddiction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hanke
 *
 * Describe：实名认证 dialog
 */
public class HY_AuthenticationDialog extends Dialog implements View.OnClickListener {
    private Activity mActivity;
    private View mRootView;
    private EditText name;
    private EditText number;
    private ImageView quite;
    private TextView btn_authenticate;
    private TextView txt_autenticate_gifg;
    private TextView txt_tips_top;
    private String user_name;
    private String user_number;
    private Window dialogWindow;
    private static HY_AuthenticationDialog hyGameAuthenticationDialog;
    /**
     * S是否登录验证成功
     * **/
    private boolean isAuthenticateSucces = false;
    /***
     * 1."float_authenticate" 为悬浮球打开的实名认证
     * 2."authenticate" 为除悬浮球以外地方打开实名认证
     */

    private int success_verify_gift = 0;//实名认证成功以后是否有奖励

    private AuthenticationCallback authenticationCallback;


    public HY_AuthenticationDialog(Context context) {
        super(context, Utils.getStyleId(context,"HYGame_Login_Dialog"));
        this.mActivity = (Activity) context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }



    public static HY_AuthenticationDialog getInstance(Activity activity){

        hyGameAuthenticationDialog = new HY_AuthenticationDialog(activity);
        return hyGameAuthenticationDialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        setContentView(Utils.getLayoutId(mActivity,"hygame_authentication_dialog"));
        dialogWindow = getWindow();
        initView();

        setDefaultPostion(mActivity);
    }




    private void initView() {
        name = (EditText) findViewById(Utils.getId(mActivity,
                "hy_authentication_name"));
        number = (EditText) findViewById(Utils.getId(mActivity,
                "hy_authentication_number"));
        btn_authenticate = (TextView) findViewById(Utils.getId(mActivity,
                "btn_authenticate"));
        quite = (ImageView) findViewById(Utils.getId(mActivity,
                "hy_authentication_quite"));

        txt_autenticate_gifg = (TextView) findViewById(Utils.getId(mActivity,
                "txt_autenticate_gift"));

        txt_tips_top = (TextView) findViewById(Utils.getId(mActivity,
                "txt_tips_top"));

        btn_authenticate.setOnClickListener(this);
        quite.setOnClickListener(this);


        String authenticate_hint = Utils.getStringId(mActivity,"u9pay_login_authentication_title_hint");
        txt_tips_top.setText(Html.fromHtml(authenticate_hint));

        String authenticate_gift_hint = Utils.getStringId(mActivity,"u9pay_login_authentication_gift_hint");
        txt_autenticate_gifg.setText(Html.fromHtml(authenticate_gift_hint));



        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Logger.d("实名认证dialog销毁");
                //Logger.d("实名认证返回结果:"+result.backString);
                OpenAuthenticationDialogHelp.isShowing = false;
                if (authenticationCallback!=null){
                    if (isAuthenticateSucces) {
                        authenticationCallback.authenticationSuccess(verifyResultMessage);
                    }/*else{
                        authenticationCallback.authenticationFail(verifyResultMessage);
                    }*/
                }
                hyGameAuthenticationDialog = null;
                //super.onDismiss(dialog);
            }
        });

        if (success_verify_gift==1){
            txt_autenticate_gifg.setVisibility(View.VISIBLE);
        }else {
            txt_autenticate_gifg.setVisibility(View.GONE);
        }

    }






    /**
     * 设置横竖屏时的默认位置
     */
    private void setDefaultPostion(Activity activity) {
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            lp.width = activity.getResources().getDisplayMetrics().widthPixels;
            //lp.height = getResources().getDisplayMetrics().heightPixels * 2 / 3;
        } else {
            // 横屏
            lp.width = activity.getResources().getDisplayMetrics().widthPixels * 1 / 2;

        }
        dialogWindow.setAttributes(lp);
    }




    @Override
    public void onClick(View v) {
        if (!Utils.isFastDoubleClick()) {
            if (v == btn_authenticate) {
                user_name = name.getText().toString();
                user_number = number.getText().toString();
                String reg = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)";
                String reg2 = "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
                if (TextUtils.isEmpty(user_number)) {
                    ToastUtils.showShort(mActivity,"请填写身份证号码!");
                } else if (TextUtils.isEmpty(user_name)) {
                    ToastUtils.showShort(mActivity,"请填写真实姓名!");
                } else if (user_number.matches(reg) || user_name.matches(reg2)) {
                    setCancelEnabled(false);
                    realNmaeVerifyRequest(mActivity,user_number,user_name);

                } else {
                    ToastUtils.showShort(mActivity,"请填写正确的身份证号码!");
                }
            }
            if (v == quite) {
                dismiss();
            }
        }
    }






    private void setCancelEnabled(boolean enabled){
        quite.setEnabled(enabled);
        btn_authenticate.setEnabled(enabled);
    }








    /***
     *
     * @param mActivity
     * @param authenticationCallback 认证结果回调监听
     */
    public void showAuthentication(Activity mActivity, AuthenticationCallback authenticationCallback, int success_verify_gift){
        if (isShowing()){
            return;
        }
        this.authenticationCallback = authenticationCallback;
        isAuthenticateSucces = false;
        this.success_verify_gift = success_verify_gift;
        OpenAuthenticationDialogHelp.isShowing = true;
        super.show();
    }




    private String verifyResultMessage = "实名认证取消";

    @SuppressLint("CheckResult")
    public void realNmaeVerifyRequest(final Activity mActivity, String idCard, String realName){
        LoginUtils loginUtils = LoginUtils.getInstance();
        if(!loginUtils.isLoginSucess()){
            ToastUtils.showShort(mActivity,"请先登录账号,再进行实名认证!");
            return;
        }
        UserInfoEntity userInfoEntity = loginUtils.getUser();
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params =  RequestParameterUtils.getRequestBaseParameter(mActivity);
        if(userInfoEntity!=null){
            params.put("uid",userInfoEntity.getUid());
            Logger.d("用户不为空");
        }
        params.put("id_card",idCard);
        params.put("realname",realName);
        params.put("channel_id", HY_Constants.CHANNEL_CODE);
        params.put("app_id", HY_Constants.APPID);

        httpApi.realNameVerify(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("实名认证返回数据>>>"+completeInfo);
                        setCancelEnabled(true);
                        if(status==0){
                            isAuthenticateSucces = true;
                            verifyResultMessage = message;

                            dismiss();
                        }else{
                            isAuthenticateSucces = false;
                            ToastUtils.showShort(mActivity,message);
                            verifyResultMessage = message;
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        isAuthenticateSucces = false;
                        setCancelEnabled(true);
                        ToastUtils.showShort(mActivity,message);
                    }
                });


    }





    public interface AuthenticationCallback{
        void authenticationSuccess(String msg);//验证身份信息成功
        void authenticationFail(String msg);//验证身份信息失败

    }


}
