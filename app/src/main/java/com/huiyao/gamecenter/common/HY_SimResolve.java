package com.huiyao.gamecenter.common;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.SharedPreferenceConstants;
import com.huiyao.gamecenter.util.SharedPreferenceUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HY_SimResolve {
	/** 设备id */
	public static String deviceId = "";
	/** 设备型号 */
	public static String model = "";
	/** Sims号 */
	public static String simsNum = "";
	/** 手机号码 */
	public static String phoneNum = "";
	/** IMEI号 */
	public static String imei = "";
	/**** uuid 实际 为 IMEI 跟老字段区分开 ***/
	public static String uuid = "";
	/**安卓id ANDROIDID*/
	public static String androidId = "";
	/***oaid **/
	public static String oaid = "";
	/** IMSI */
	public static String imsi = "";
	/** mac地址 */
	public static String macAddress = "";
	/** 运营商 */
	public static String spType = "未知";
	/** SDK版本 */
	public static String sdk_version = "";
	/** 设备的系统版本 */
	public static String release_version = "";
	/** 纬度 */
	public static double longitude;
	/** 纬度 */
	public static double latitude;
	public static Context mContext;
	private static TelephonyManager telMgr;
	/**
	 * @param
	 */
	public static void getDeviceInfo(Context context) {
		Logger.d("------->u9获取设备信息<--------");
		mContext = context;
		try {
			deviceId = SharedPreferenceUtils.getPrefString(context, SharedPreferenceConstants.DEVICE_ID ,"");
		} catch (Exception e) {
			Logger.e("deviceId 获取异常:" + e.toString());
		}

		try {
			model = getPhoneModel();
		} catch (Exception e) {
			Logger.e("getPhoneModel 获取异常:" + e.toString());
		}
		try {
			sdk_version = getVersionSDK();
		} catch (Exception e) {
			Logger.e("getVersionSDK 获取异常:" + e.toString());
		}
		try {
			release_version = getVersionRelease();
		} catch (Exception e) {
			Logger.e("getVersionRelease 获取异常:" + e.toString());
		}
//		getLocation();
		// 获取手机型号
		if (!TextUtils.isEmpty(imei)) {
			return;
		}
		
		try {
			telMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			simsNum = telMgr.getSimSerialNumber();
			imsi = telMgr.getSubscriberId();
		} catch (Exception e) {
			Logger.e("simsNum 获取异常:" + e.toString());
		}
		try {

			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				spType = "中国移动";
			} else if (imsi.startsWith("46001")) {
				spType = "中国联通";
			} else if (imsi.startsWith("46003")) {
				spType = "中国电信";
			}
		} catch (Exception e) {
			Logger.e("imsi 获取异常:" + e.toString());
		}

		try {
			imei = telMgr.getDeviceId();
			/*if (imei==null){
				//android.provider.Settings;
				imei= Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

				//imei = "0";
			}*/
			uuid = imei;

		} catch (Exception e) {
			Logger.e("imei 获取异常:" + e.toString());
			e.printStackTrace();// imei获取不到
		} finally {
			if (imei == null || "".equals(imei)) {
				imei = "";
				uuid = "";
			}
		}

		//获取androidId
		try {
			androidId= Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		}catch (Exception e){
			Logger.e("android 获取异常:" + e.toString());
			androidId = "";
		}

		// 获取手机号码
		try {
			phoneNum = telMgr.getLine1Number();
			Logger.i("手机号 获取:" + phoneNum);

			if (phoneNum != null && phoneNum.contains("+")) {
				phoneNum = phoneNum.substring(3, phoneNum.length());
			}
		} catch (Exception e) {
			Logger.e("手机号 获取异常:" + e.toString());
			e.printStackTrace();// imei获取不到
		}
		// 获取mac地址
		// 在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0

		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				macAddress = info.getMacAddress();
			}
			if (!TextUtils.isEmpty(macAddress)) {
				macAddress = macAddress.replaceAll(":", "");
				macAddress = macAddress.replaceAll("-", "");
			}
		} catch (Exception e) {
			e.printStackTrace(); // mac获取不到
		} finally {
			if (macAddress == null || "".equals(macAddress)) {
				macAddress = "no_macAddress";
			}
		}
		// Logger.d(TAG, "imei:" + imei + "," + "mac" + macAddress);
		showLog();
	}

	/**
	 * 获取手机型号
	 **/
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机型号
	 **/
	@SuppressWarnings("deprecation")
	public static String getVersionSDK() {
		return android.os.Build.VERSION.SDK;
	}

	/**
	 * 获取手机型号
	 **/
	public static String getVersionRelease() {
		return android.os.Build.VERSION.RELEASE;
	}



	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		System.out.println(m.matches() + "---");

		return m.matches();

	}

	public static void showLog() {
		Logger.d("model:" + model + "#simsNum:" + simsNum + "#phoneNum:"
				+ phoneNum + "#imei:" + imei + "#imsi:" + imsi + "#macAddress:"
				+ macAddress + "#spType:" + spType + "#sdk_version:"
				+ sdk_version + "#release_version:" + release_version
				+ "#latitude:" + latitude + "#longitude" + longitude);
	}

}
