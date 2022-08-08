package com.huiyao.gamecenter.module.antiaddiction;

import android.app.Activity;

public class OpenAuthenticationDialogHelp {



    public static boolean isShowing = false;

    /***
     * 打开实名认证界面
     * @param mActivity
     */
    public static void toAuthenticationActivity(Activity mActivity, HY_AuthenticationDialog.AuthenticationCallback authenticationCallback, int success_verify_gift){

        if (isShowing){
            return;
        }
        HY_AuthenticationDialog authenticationDialog = HY_AuthenticationDialog.getInstance(mActivity);
        authenticationDialog.showAuthentication(mActivity,authenticationCallback,success_verify_gift);

    }






}
