package com.huiyao.gamecenter.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 综合相关
 */
public class Utils {
    public static final String TAG = Utils.class.getClass().getSimpleName();

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static String packageName;
    private static long lastClickTime;

    /**
     * @return
     * @description: 当闪屏PNG图片无法铺满部分机型的屏幕时，设置与闪屏颜色配合的背景色会给用户更好的体验
     * @author:smile
     * @return:int
     */
    public static int getBackgroundColor(Context context) {
        // 从AndroidManifest中获取渠道颜色配置
        String splash_color = Utils.getManifestMeta(context, "HY_GAME_COLOR");
        if (!TextUtils.isEmpty(splash_color)) {
            return Color.parseColor(splash_color);
        } else {
            return Color.WHITE;
        }
    }

    public static String getManifestMeta(Context context, String key) {
        String result;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), 128);

            result = appInfo.metaData.get(key) + "";// 可能为null
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("read meta " + key + " error", e);
        } catch (Exception e) {
            result = "";
            // throw new IllegalStateException("read meta " + key + " error",
            // e);
        }
        if ((result == null) || (result.length() == 0)) {
            // throw new IllegalStateException("no meta " + key + " found");
            Logger.d(TAG, "-->IllegalStateException(no meta " + key + " found");
        }
        if (result.equalsIgnoreCase("null")) {
            result = "";
        }
        return result;
    }


    public static String getHYConfig(Context context, String key) {
        Logger.d(TAG,
                "key : " + key + " value : "
                        + AssetsConfigUtils.getInstance(context).get(key));
        String returnStr = AssetsConfigUtils.getInstance(context).get(key);
        if (!TextUtils.isEmpty(returnStr)) {
            return returnStr;
        } else {
            return null;
        }
    }


    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) { // 500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static int getId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources()
                .getIdentifier(id, "id", packageName);
    }

    public static int getId(Context paramActivity, String id, String type) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources()
                .getIdentifier(id, type, packageName);
    }

    public static String getStringId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources().getString(
                paramActivity.getResources().getIdentifier(id, "string",
                        packageName));
    }

    public static int getArrayId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources().getIdentifier(id, "array",
                packageName);
    }

    public static int getLayoutId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources().getIdentifier(id, "layout",
                packageName);
    }

    public static int getDrawableId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources().getIdentifier(id, "drawable",
                packageName);
    }

    public static int getStyleId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources().getIdentifier(id, "style",
                packageName);
    }


    public static int getAnimId(Context paramActivity, String id) {
        String packageName = getPackageName(paramActivity);
        return paramActivity.getResources().getIdentifier(id, "anim",
                packageName);
    }


    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }


    // 获取ManifestCode
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPackageName(Context context) {
        if (!TextUtils.isEmpty(packageName)) {
            return packageName;
        }
        packageName = context.getPackageName();
        return packageName;
    }


    public static boolean isInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException var3) {
            try {
                context.getPackageManager().getApplicationInfo(packageName, 0);
                return true;
            } catch (PackageManager.NameNotFoundException var2) {
                return false;
            }
        }
    }


    public static void toGameActivity(Context context, String packageName, String startActivityName) {
        if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(startActivityName)) {
            Logger.i("跳转>>>包名或者activity为空");
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClassName(packageName, startActivityName);
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.i("跳转>>>包名:" + packageName + "activity:" + startActivityName + "异常");
        }

    }

    /**
     * 判断列表第一个是否显示
     *
     * @param listView
     * @return
     */
    public static boolean isFirstItemVisible(AbsListView listView) {
        Adapter adapter = listView.getAdapter();
        if (null != adapter && !adapter.isEmpty()) {
            int mostTop = listView.getChildCount() > 0 ? listView.getChildAt(0).getTop() : 0;
            return mostTop >= 0;
        } else {
            return true;
        }
    }

    /**
     * 设置listview高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除   listView.setLayoutParams(params); }
    }


    /**
     * 计算ListView宽高
     *
     * @param listView
     */
    public static int calListViewWidthAndHeight(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
            Log.d("数据" + i, String.valueOf(totalHeight));
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        Log.d("数据", "listview总高度=" + params.height);
        listView.setLayoutParams(params);
        listView.requestLayout();
        return totalHeight;
    }

    /**
     * 计算GridView宽高
     *
     * @param gridView
     */
    public static void calGridViewWidthAndHeight( GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int numColumns = gridView.getNumColumns();
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View itemView = listAdapter.getView(i, null, gridView);
            itemView.measure(  0,0);
            if ((i + 1) % numColumns == 0) {
                totalHeight += itemView.getMeasuredHeight(); // 统计所有子项的总高度
            }

            if ((i + 1) == len && (i + 1) % numColumns != 0) {
                totalHeight += itemView.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + gridView.getVerticalSpacing();
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    public static String getPlanId(Context context) {
        String planId= "";
        final String start_flag = "META-INF/hy_plan_";
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(start_flag)) {
                    planId = entryName.replace(start_flag, "");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (planId == null || planId.length() <= 0) {
            planId = "0";// 读不到渠道号就默认是官方渠道
            Logger.e("读不到渠道号就默认是官方渠道:0");
        }

        String planIdStorage = SharedPreferenceUtils.getPrefString(context, "planid", "");

        // 渠道Id 读取优先级 存储数据 > 读取配置
        if (!TextUtils.isEmpty(planIdStorage) && !"0".equals(planIdStorage)) {
            planId = planIdStorage;
            Logger.d("planIdStorage读取成功:" + planId);
        } else if (!TextUtils.isEmpty(planId) && !"0".equals(planId)) {
            Logger.d("planId读取成功:" + planId);
            SharedPreferenceUtils.setPrefString(context, "planid", planId);
        } else {
            Logger.e("planId读取异常:" + planId);
        }
        Logger.d("planId:" + planId);
        return planId;
    }



    public static String getChannelId(Context context) {
        String channel = "";
        final String start_flag = "META-INF/sub_channel_";
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(start_flag)) {
                    channel = entryName.replace(start_flag, "");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (channel == null || channel.length() <= 0) {
            channel = "0";// 读不到渠道号就默认是官方渠道
        }

        String channelIdStorage = SharedPreferenceUtils.getPrefString(context, "sub_channel", "");

        // 渠道Id 读取优先级 存储数据 > 读取配置
        if (!TextUtils.isEmpty(channelIdStorage)
                && !"0".equals(channelIdStorage)) {
            channel = channelIdStorage;
        } else if (!TextUtils.isEmpty(channel) && !"0".equals(channel)) {
            SharedPreferenceUtils.setPrefString(context, "sub_channel", channel);
        }

        return channel;
    }

}









