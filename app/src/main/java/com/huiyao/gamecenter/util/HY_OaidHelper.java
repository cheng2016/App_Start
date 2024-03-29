package com.huiyao.gamecenter.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.JLibrary;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;

/**
 * Created by zheng on 2019/8/22.
 */

public class HY_OaidHelper implements IIdentifierListener {

    private AppIdsUpdater _listener;
    public HY_OaidHelper(AppIdsUpdater callback){
        _listener=callback;

    }


    public void getDeviceIds(Context cxt){
        /*if (Build.VERSION.SDK_INT<29){
            Log.d(getClass().getSimpleName(),"android系统小于29(10.0)不获取oaid");
            return;
        }*/
        try {
            JLibrary.InitEntry(cxt);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(getClass().getSimpleName(),"初始化JLibrary失败!");

        }

        long timeb= System.currentTimeMillis();
        int nres = CallFromReflect(cxt);
//      int nres=DirectCall(cxt);
        long timee= System.currentTimeMillis();
        long offset=timee-timeb;
        if(nres == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT){//不支持的设备

        }else if( nres == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE){//加载配置文件出错

        }else if(nres == ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT){//不支持的设备厂商

        }else if(nres == ErrorCode.INIT_ERROR_RESULT_DELAY){//获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程

        }else if(nres == ErrorCode.INIT_HELPER_CALL_ERROR){//反射调用出错

        }
        Log.d(getClass().getSimpleName(),"return value: "+ String.valueOf(nres));

    }


    /*
    * 通过反射调用，解决android 9以后的类加载升级，导至找不到so中的方法
    *
    * */
    private int CallFromReflect(Context cxt){
        return MdidSdkHelper.InitSdk(cxt,true,this);
    }

    /*
    * 直接java调用，如果这样调用，在android 9以前没有题，在android 9以后会抛找不到so方法的异常
    * 解决办法是和JLibrary.InitEntry(cxt)，分开调用，比如在A类中调用JLibrary.InitEntry(cxt)，在B类中调用MdidSdk的方法
    * A和B不能存在直接和间接依赖关系，否则也会报错
    *
    * */
    private int DirectCall(Context cxt){
       /* MdidSdk sdk = new MdidSdk();
        return sdk.InitSdk(cxt,this);*/
       return 0;
    }
    @Override
    public void OnSupport(boolean isSupport, IdSupplier _supplier) {
        if(_supplier==null) {
            return;
        }
        String oaid=_supplier.getOAID();
        String vaid=_supplier.getVAID();
        String aaid=_supplier.getAAID();
        StringBuilder builder=new StringBuilder();
        builder.append("support: ").append(isSupport?"true":"false").append("\n");
        builder.append("OAID: ").append(oaid).append("\n");
        builder.append("VAID: ").append(vaid).append("\n");
        builder.append("AAID: ").append(aaid).append("\n");
        String idstext=builder.toString();

        Log.d(getClass().getSimpleName(),"return value: "+ String.valueOf(idstext));

        if(_listener!=null){
            _listener.OnIdsAvalid(isSupport,oaid,vaid,aaid);
        }
    }
    public interface AppIdsUpdater{
        void OnIdsAvalid(@NonNull boolean isSupport, String oaid, String vaid, String aaid);
    }

}
