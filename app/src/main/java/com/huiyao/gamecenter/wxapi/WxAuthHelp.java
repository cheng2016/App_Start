package com.huiyao.gamecenter.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.huiyao.gamecenter.App;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;


public class WxAuthHelp {

    private static WxAuthHelp wxAuthHelp;
    private IWXAPI api;

    private static String APP_ID = "";

    private WxAuthHelp() {

    }


    public static WxAuthHelp getInstance() {
        if (wxAuthHelp == null) {
            wxAuthHelp = new WxAuthHelp();
        }

        return wxAuthHelp;

    }

    //去微信注册
    public void regToWx(Activity activity) {
        if (!isWxInstall(App.getInstance())) return;
        APP_ID = Utils.getManifestMeta(activity, "WX_APP_ID");
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(activity, APP_ID, false);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
      /*  registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));*/
    }


    //微信认证
    public void toWxAuth() {
        if (!isWxInstall(App.getInstance())) {
            ToastUtils.showShort("请先安装微信！");
            return;
        }
        if (api != null) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "none";
            api.sendReq(req);
            Log.i("微信认证", "拉起微信");
        } else {
            Log.i("微信认证", "微信认证注册失败或者未注册");
        }

    }


    public void toWxCode() {
        toWxCode("gh_3e6f9c88a2ab", "?ext_info=509537");
    }

    public void toWxCode(String id, String path) {
        if (!isWxInstall(App.getInstance())) {
            ToastUtils.showShort("请先安装微信！");
            return;
        }
        if (api != null) {
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = id; // 填小程序原始id
            req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
            api.sendReq(req);
            Log.i("微信认证", "拉起微信");
        } else {
            Log.i("微信认证", "微信认证注册失败或者未注册");
        }
    }

    /**
     * 检测是否安装微信 * * @param context * @return
     */
    public static boolean isWxInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
