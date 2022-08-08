package com.huiyao.gamecenter.module.login;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.util.DimenUtils;
import com.huiyao.gamecenter.util.ScreenUtils;
import com.huiyao.gamecenter.util.ToastUtils;

import java.lang.reflect.Method;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIClickCallback;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.LoginSettings;
import cn.jiguang.verifysdk.api.PreLoginListener;
import cn.jiguang.verifysdk.api.RequestCallback;
import cn.jiguang.verifysdk.api.VerifyListener;

/***
 * 极光一键登陆 相关方法类
 */
public class HY_JverificationHelp {


    /***
     * 极光一键登陆sdk初始化
     */
    public static void JverificationSdkInit(final Context context){

      /*  try
        {
            Class loadClass = JVerificationInterface.class;
            Method method = loadClass.getDeclaredMethod("setControlWifiSwitch",boolean.class);
            method.setAccessible(true);
            method.invoke(loadClass, false);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }*/


        try {
            Class loadclass = Class.forName("cn.jiguang.verifysdk.api.JVerificationInterface");
            Method method = loadclass.getDeclaredMethod("setControlWifiSwitch", boolean.class);
            method.setAccessible(true);
            method.invoke(loadclass,false);
        }catch (Throwable e){
            e.printStackTrace();
        }
        JVerificationInterface.setDebugMode(true);
        JVerificationInterface.init(context, new RequestCallback<String>() {
            @Override
            public void onResult(int code, String result) {
                Log.i("HY", "[init] code = " + code + " result = " + result);
                /**
                 * 极光登录预取号
                 */
                //HY_JverificationHelp.JerificationPreLogin(context);

            }
        });
    }


    /**
     * 极光一键登录预取号
     */
    public static void JerificationPreLogin(Context context){
        JVerificationInterface.preLogin(context, 5000,new PreLoginListener() {
            @Override
            public void onResult(final int code, final String content) {
                //Log.d(TAG,"[" + code + "]message=" +  content );
            }
        });
    }


    /**
     * 清除极光一键登录预取号缓存
     */
    public static void JerificationClearPreLoginCace(){
        JVerificationInterface.clearPreLoginCache();
    }


    /***
     * 极光登录拉起认证界面
     * @param activity
     * @param authPageEventListener
     * @param verifyListener
     */
    public static void JerificationLoginAuth(Activity activity, AuthPageEventListener authPageEventListener, VerifyListener verifyListener){
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(activity);
        if (!verifyEnable) {
            ToastUtils.showLong(activity,"当前网络环境不支持认证,请打开数据流量");
        }
        LoginSettings settings = new LoginSettings();
        settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
        settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
        settings.setAuthPageEventListener(authPageEventListener);//设置授权页事件监听

        setUIConfig(activity,true,authPageEventListener);
        JVerificationInterface.loginAuth(activity, settings, verifyListener);

    }






    private static void setUIConfig(Activity activity, boolean isDialogMode, AuthPageEventListener authPageEventListener) {
        JVerifyUIConfig portrait = getPortraitConfig(activity,isDialogMode,authPageEventListener);
        JVerifyUIConfig landscape = getLandscapeConfig(activity,isDialogMode,authPageEventListener);

        //支持授权页设置横竖屏两套config，在授权页中触发横竖屏切换时，sdk自动选择对应的config加载。
        JVerificationInterface.setCustomUIWithConfig(portrait, landscape);
    }



    private static JVerifyUIConfig getPortraitConfig(Activity activity, boolean isDialogMode, final AuthPageEventListener authPageEventListener) {
        JVerifyUIConfig.Builder configBuilder = new JVerifyUIConfig.Builder();

        //自定义按钮示例1
        /*ImageView mBtn = new ImageView(activity);
        mBtn.setImageResource(R.drawable.hy_authentication_icon);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(dp2Pix(activity, 40), dp2Pix(activity, 40));
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);*/

        //自定义按钮示例2
       /* ImageView mBtn2 = new ImageView(this);
        mBtn2.setImageResource(R.drawable.weixin);
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);*/


        //自定义其他登录方式 按钮

        TextView tvOtherLogin = new TextView(activity);
        tvOtherLogin.setText("其他方式登录");
        tvOtherLogin.setTextSize(16);
        //tvOtherLogin.setTextColor(0xff28bc5c);
        tvOtherLogin.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


       /* ImageView loadingView = new ImageView(activity.getApplicationContext());
        loadingView.setImageResource(R.drawable.umcsdk_load_dot_white);
        RelativeLayout.LayoutParams loadingParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadingView.setLayoutParams(loadingParam);

        Animation animation = AnimationUtils.loadAnimation(activity.getApplicationContext(), HY_Utils.getAnimId(activity,"umcsdk_anim_loading"));*/

        if (isDialogMode) {
            //窗口竖屏
          /*  mLayoutParams1.setMargins(dp2Pix(this, 100), dp2Pix(this, 250.0f), 0, 0);
            mBtn.setLayoutParams(mLayoutParams1);

            mLayoutParams2.setMargins(0, dp2Pix(this, 250.0f), dp2Pix(this, 100.0f), 0);
            mBtn2.setLayoutParams(mLayoutParams2);*/
            mLayoutParams3.setMargins(dp2Pix(activity,40),dp2Pix(activity,40),dp2Pix(activity,40),dp2Pix(activity,70));
            tvOtherLogin.setLayoutParams(mLayoutParams3);

            //自定义返回按钮示例
            /*ImageButton sampleReturnBtn = new ImageButton(activity.getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.umcsdk_return_bg);
            RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            returnLP.setMargins(10, 10, 0, 0);
            sampleReturnBtn.setLayoutParams(returnLP);*/

            String agreementUrl = HY_Constants.contractUrl;
            if(TextUtils.isEmpty(agreementUrl)){
                agreementUrl = "https://sdk.hyhygame.com/h5game/userAgreement.html";
            }

            String agreementTitle = HY_Constants.contractTitle;
            if(TextUtils.isEmpty(agreementTitle)){
                agreementTitle = "《共享经济合作伙伴协议》";
            }
            int widthpx = ScreenUtils.getScreenWidth(activity);
            int widhtdp = DimenUtils.px2dp(activity,widthpx);

            configBuilder.setAuthBGImgPath("hy_radius_white_bg")
                    .setStatusBarHidden(true)
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(0)
                    .setLogoHeight(0)
                    .setLogoHidden(true)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("")
                    //私条款url为网络网页或本地网页地址(sd卡的地址，需自行申请sd卡读权限)，
                    // 如：assets下路径："file:///android_asset/t.html"，
                    // sd卡下路径："file:"+Environment.getExternalStorageDirectory().getAbsolutePath() +"/t.html"
                    .setAppPrivacyOne(agreementTitle,agreementUrl)
                    .setAppPrivacyColor(0xff666666, 0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(25)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(80)
                    .setSloganOffsetY(120)
                    .setLogBtnOffsetY(150)
                    .setNumberSize(18)
                    .setPrivacyState(false)
                    .setNavTransparent(false)
                    .setPrivacyOffsetY(20)
                    .setDialogTheme(widhtdp,340, 0, 0, true)
                    // .setLoadingView(loadingView, animation)
                    .enableHintToast(true, Toast.makeText(activity.getApplicationContext(), "请勾选用户协议！", Toast.LENGTH_SHORT))
                    /* .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                         @Override
                         public void onClicked(Context context, View view) {
                             Toast.makeText(context, "自定义的按钮1，会finish授权页", Toast.LENGTH_SHORT).show();
                         }
                     })
                     .addCustomView(mBtn2, false, new JVerifyUIClickCallback() {
                         @Override
                         public void onClicked(Context context, View view) {
                             Toast.makeText(context, "自定义的按钮2，不会finish授权页", Toast.LENGTH_SHORT).show();
                         }
                     })*/
                    .addCustomView(tvOtherLogin, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            if (authPageEventListener != null){
                                //10001 为定义其他登录方式状态码（非极光原有动作事件）
                                Log.i("HY","其他方式登录");
                                authPageEventListener.onEvent(10001,"其他方式登录");

                            }
                        }
                    });
            configBuilder.setPrivacyCheckboxHidden(true)
                    .setPrivacyState(true);
        }
        return configBuilder.build();
    }






    private static JVerifyUIConfig getLandscapeConfig(Activity activity, boolean isDialogMode, final AuthPageEventListener authPageEventListener) {
        JVerifyUIConfig.Builder configBuilder = new JVerifyUIConfig.Builder();

        //自定义按钮示例1
       /* ImageView mBtn = new ImageView(this);
        mBtn.setImageResource(R.drawable.qq);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        //自定义按钮示例2
        ImageView mBtn2 = new ImageView(this);
        mBtn2.setImageResource(R.drawable.weixin);
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);*/

        TextView tvOtherLogin = new TextView(activity);
        tvOtherLogin.setText("其他方式登录");
        tvOtherLogin.setTextSize(16);
        tvOtherLogin.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


      /*  ImageView loadingView = new ImageView(activity.getApplicationContext());
        loadingView.setImageResource(R.drawable.umcsdk_load_dot_white);
        RelativeLayout.LayoutParams loadingParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadingView.setLayoutParams(loadingParam);

        Animation animation = AnimationUtils.loadAnimation(activity.getApplicationContext(), HY_Utils.getAnimId(activity,"umcsdk_anim_loading"));*/
        String agreementUrl = HY_Constants.contractUrl;
        if(TextUtils.isEmpty(agreementUrl)){
            agreementUrl = "https://sdk.hyhygame.com/h5game/userAgreement.html";
        }

        String agreementTitle = HY_Constants.contractTitle;
        if(TextUtils.isEmpty(agreementTitle)){
            agreementTitle = "《共享经济合作伙伴协议》";
        }

        if (isDialogMode) {
            //窗口横
           /* mLayoutParams1.setMargins(dp2Pix(this, 200), dp2Pix(this, 235.0f), 0, 0);
            mBtn.setLayoutParams(mLayoutParams1);

            mLayoutParams2.setMargins(0, dp2Pix(this, 235.0f), dp2Pix(this, 200.0f), 0);
            mBtn2.setLayoutParams(mLayoutParams2);*/
            mLayoutParams3.setMargins(dp2Pix(activity,40),dp2Pix(activity,40),dp2Pix(activity,40),dp2Pix(activity,70));
            tvOtherLogin.setLayoutParams(mLayoutParams3);
            //自定义返回按钮示例
            /*ImageButton sampleReturnBtn = new ImageButton(activity.getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.umcsdk_return_bg);
            RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            returnLP.setMargins(10, 10, 0, 0);
            sampleReturnBtn.setLayoutParams(returnLP);*/
            int widthpx = ScreenUtils.getScreenWidth(activity);
            int widhtdp = DimenUtils.px2dp(activity,widthpx);

            configBuilder.setAuthBGImgPath("hy_radius_white_bg")
                    .setStatusBarHidden(true)
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(0)
                    .setLogoHeight(0)
                    .setLogoHidden(true)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("")
                    //私条款url为网络网页或本地网页地址(sd卡的地址，需自行申请sd卡读权限)，
                    // 如：assets下路径："file:///android_asset/t.html"，
                    // sd卡下路径："file:"+Environment.getExternalStorageDirectory().getAbsolutePath() +"/t.html"
                    .setAppPrivacyOne(agreementTitle,agreementUrl)
                    .setAppPrivacyColor(0xff666666, 0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(25)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(80)
                    .setSloganOffsetY(120)
                    .setLogBtnOffsetY(150)
                    .setPrivacyOffsetY(20)
                    .setDialogTheme(400, 400, 0, 0, true)
                    .enableHintToast(true, Toast.makeText(activity.getApplicationContext(), "请勾选用户协议！", Toast.LENGTH_SHORT))
                    /*  .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                          @Override
                          public void onClicked(Context context, View view) {
                              Toast.makeText(context, "自定义的按钮1，会finish授权页", Toast.LENGTH_SHORT).show();
                          }
                      }).addCustomView(mBtn2, false, new JVerifyUIClickCallback() {
                  @Override
                  public void onClicked(Context context, View view) {
                      Toast.makeText(context, "自定义的按钮2，不会finish授权页", Toast.LENGTH_SHORT).show();
                  }
              })*/
                    .addCustomView(tvOtherLogin, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            if (authPageEventListener != null){
                                //1001 为定义其他登录方式状态码（非极光原有动作事件）
                                Log.i("HY","其他方式登录");
                                authPageEventListener.onEvent(10001,"其他方式登录");

                            }
                        }
                    });
            configBuilder.setPrivacyCheckboxHidden(true)
                    .setPrivacyState(true);
        }

        return configBuilder.build();
    }






    private static int dp2Pix(Context context, float dp) {
        try {
            float density = context.getResources().getDisplayMetrics().density;
            return (int) (dp * density + 0.5F);
        } catch (Exception e) {
            return (int) dp;
        }
    }





}
