package com.huiyao.gamecenter.module.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.web.CustomerWebActivity;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.view.HYGame_EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommonPhoneLoginDialog extends Dialog {
    @Bind(R.id.hy_edit_user_phone)
    HYGame_EditText hyEditUserPhone;
    @Bind(R.id.hy_edit_phone_key)
    HYGame_EditText hyEditPhoneKey;
    @Bind(R.id.hy_get_phone_key)
    TextView hyGetPhoneKey;
    @Bind(R.id.hy_phone_login_cb)
    CheckBox hyPhoneLoginCb;
    @Bind(R.id.hy_login_register_btn)
    Button hyLoginRegisterBtn;
    @Bind(R.id.hy_phone_login_agree_tv)
    TextView hyPhoneLoginAgreeTv;

    private Activity mActivity;


    public String phoneNum;
    public static CommonPhoneLoginDialog commonPhoneLoginDialog;
    public HY_LoginCallBack hyLoginCallBack;
    TimeCount time;
    String agreementUrl;
    String agreementTitle;

    public CommonPhoneLoginDialog(@NonNull Context context) {
        super(context, R.style.base_pop);
        this.mActivity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("HY", "打开手机号验证码onCreate");
        setDialogWindow(this);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_common_phone_login);
        ButterKnife.bind(this);
        initView();
    }

    public static CommonPhoneLoginDialog getInstance(Activity activity) {
        if (commonPhoneLoginDialog == null) {
            commonPhoneLoginDialog = new CommonPhoneLoginDialog(activity);
        }
        return commonPhoneLoginDialog;
    }


    private void initView(){
        agreementUrl = HY_Constants.contractUrl;
        if(TextUtils.isEmpty(agreementUrl)){
            agreementUrl = "https://sdk.hyhygame.com/h5game/userAgreement.html";
        }
        agreementTitle = HY_Constants.contractTitle;
        if(TextUtils.isEmpty(agreementTitle)){
            agreementTitle = "《共享经济合作伙伴协议》";
        }
        hyPhoneLoginAgreeTv.setText(agreementTitle);
    }


    /**
     * 判断弹窗是否显示
     *
     * @return
     */
    public void showDialog(HY_LoginCallBack hyLoginCallBack) {
        this.hyLoginCallBack = hyLoginCallBack;
        Logger.i("HY", "打开手机号验证码登录");
        super.show();
    }


    public void setDialogWindow(final Dialog dialog) {
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 8);
        WindowManager.LayoutParams lp = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        Configuration cf = dialog.getContext().getResources().getConfiguration();

        lp.width = (int) (display.getWidth());
        lp.height = (int) (display.getHeight() * 0.4);
        window.setGravity(Gravity.CENTER | Gravity.BOTTOM);

        window.setWindowAnimations(R.style.HYGame_BottomDialogAnimation);
        window.setAttributes(lp);
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ButterKnife.unbind(dialog);
                Logger.i("登录账号dismiss");
                if (time != null) {
                    time.cancel();
                    time = null;
                    commonPhoneLoginDialog = null;
                }
                commonPhoneLoginDialog = null;
            }
        });

        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Logger.i("登录账号cancel");
                ButterKnife.unbind(dialog);
                if (time != null) {
                    time.cancel();
                    time = null;
                    commonPhoneLoginDialog = null;
                }
            }
        });




    }


    @OnClick({R.id.hy_get_phone_key, R.id.hy_login_register_btn,R.id.hy_phone_login_agree_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hy_get_phone_key:
                if (checkPhoneNum()) {
                    //获取手机验证码
                    getVerifyCode(getContext());
                    setVerifyCodeBtnDisabled();
                }
                break;
            case R.id.hy_login_register_btn:
                //手机号注册
                if (registerInputCheck()) {
                    phoneRegister(getContext());
                }
                break;

            case R.id.hy_phone_login_agree_tv:
                if(!TextUtils.isEmpty(agreementUrl)) {
                    Intent intent = new Intent(mActivity, CustomerWebActivity.class);
                    intent.putExtra("url", agreementUrl);
                    intent.putExtra("title", agreementTitle);
                    mActivity.startActivity(intent);
                    Logger.i("跳转共享经济合作伙伴协议网页"+agreementUrl);
                }else{
                    Logger.i("未配置共享经济合作伙伴协议");
                }
                break;
        }
    }


    /**
     * 注册检查手机号手机验证码是否填写
     */
    private boolean registerInputCheck() {
        if (!checkPhoneNum()) {
            return false;
        }

        String code = hyEditPhoneKey.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show(mActivity, "请输入验证码!", Toast.LENGTH_SHORT);
            return false;
        }

        if (!hyPhoneLoginCb.isChecked()) {
            ToastUtils.showShort(getContext(), "请勾选同意用户协议!");
            return false;
        }


        return true;
    }


    private boolean checkPhoneNum() {
        phoneNum = hyEditUserPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.show(mActivity, "请输入手机号!", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }


    /***
     * 获取验证码 30s 内禁用或者验证码按钮 ，防止重复获取
     */
    private void setVerifyCodeBtnDisabled() {
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象.
        time.start();

    }




    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (time != null) {
                hyGetPhoneKey.setText("重新获取");
                hyGetPhoneKey.setClickable(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (time != null) {
                hyGetPhoneKey.setClickable(false);
                hyGetPhoneKey.setText(millisUntilFinished / 1000 + "秒");
            }
        }
    }


    /**
     * 获取手机登录验证码
     */
    @SuppressLint("CheckResult")
    public void getVerifyCode(Context context) {
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> param = RequestParameterUtils.getRequestBaseParameter(context);
        param.put("action", "register");
        param.put("mobile", hyEditUserPhone.getText().toString());
        httpApi.getPhoneVerifyCode(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("手机号登录注册返回结果解析>>>" + completeInfo);
                        if (status == 0) {

                            ToastUtils.showShort(mActivity, "发送验证码成功！");

                        } else {
                            ToastUtils.showShort(mActivity, message);
                        }
                    }
                }), new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });

    }


    /**
     * 手机号登录/注册
     */
    @SuppressLint("CheckResult")
    public void phoneRegister(final Context context) {
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> param = RequestParameterUtils.getRequestBaseParameter(context);
        param.put("mobile", hyEditUserPhone.getText().toString().trim());
        param.put("code", hyEditPhoneKey.getText().toString().trim());
        httpApi.registerPhone(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("手机号登录注册返回结果解析>>>" + completeInfo);
                        if (status == 0) {
                            BaseResponseMode<UserInfoEntity> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<UserInfoEntity>>() {
                            });
                            loginRequest(context, baseResponseMode.getData());

                        } else {
                            ToastUtils.showShort(context, message);

                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });


    }


    /***
     * 获取平台 userid
     * userInfoEntity 渠道用户信息
     */
    @SuppressLint("CheckResult")
    private void loginRequest(final Context context, final UserInfoEntity userInfoEntity) {
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> param = RequestParameterUtils.getLoginRequestParameter(context, userInfoEntity);
        httpApi.loginRequest(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("手机号登录注册完成获取平台userid 返回>>>" + completeInfo);
                        if (status == 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(completeInfo);
                                if (jsonObject.has("UserId")) {
                                    String userid = jsonObject.optString("UserId");
                                    userInfoEntity.setUserId(userid);
                                    if (hyLoginCallBack != null) {
                                        hyLoginCallBack.onLoginSuccess(userInfoEntity);
                                        dismiss();
                                    }
                                } else {
                                    ToastUtils.showShort(context, "获取平台用户id失败!");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShort(context, message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {

                    }
                });
    }


}
