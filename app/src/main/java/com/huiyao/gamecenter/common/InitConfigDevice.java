package com.huiyao.gamecenter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.huiyao.gamecenter.util.HY_OaidHelper;

public class InitConfigDevice {

    public static void init(Context context){
        getOaid(context);
        HY_SimResolve.getDeviceInfo(context);
        HY_Config.getInstance(context);
        HY_Constants.init(context);
    }


    private static void getOaid(Context context){
        HY_OaidHelper hy_oaidHelper = new HY_OaidHelper(new HY_OaidHelper.AppIdsUpdater() {
            @Override
            public void OnIdsAvalid(@NonNull boolean isSupport, String oaid, String vaid, String aaid) {
                StringBuilder builder=new StringBuilder();
                builder.append("support: ").append(isSupport?"true":"false").append("\n");
                builder.append("OAID: ").append(oaid).append("\n");
                builder.append("VAID: ").append(vaid).append("\n");
                builder.append("AAID: ").append(aaid).append("\n");
                String idstext=builder.toString();

                Log.d("HY_OaidHelper",idstext);
                if (!TextUtils.isEmpty(oaid)){
                    HY_SimResolve.oaid = oaid;
                }
            }
        });
        hy_oaidHelper.getDeviceIds(context);
    }
}
