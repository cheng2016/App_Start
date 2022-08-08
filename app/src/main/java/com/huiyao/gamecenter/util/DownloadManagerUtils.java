package com.huiyao.gamecenter.util;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;


/**
 * 系统自带 DownloadManager 文件下载工具类
 */
public class DownloadManagerUtils {
    public static final String TAG = "DownloadManagerUtils";
    private static DownloadManagerUtils downloadManagerUtils;
    public WeakReference<Activity> weakReference;

    private DownloadManager downloadManager;


    private DownloadManagerUtils() {

    }

    private DownloadManagerUtils(Activity context) {
        this.weakReference = new WeakReference<>(context);

    }

    public static DownloadManagerUtils getInstance(Activity context) {
        if (downloadManagerUtils == null) {
            downloadManagerUtils = new DownloadManagerUtils(context);
        }
        return downloadManagerUtils;
    }

    public void download(String downLoadUrl) {
        String name = downLoadUrl.split("/")[downLoadUrl.split("/").length - 1];
        Logger.i(TAG, "download name : " + name);
        download(downLoadUrl, name, null, null);
    }

    /**
     * @param downLoadUrl     下载地址
     * @param apkFileName     apk 文件名称 xxx.apk
     * @param taskTitle       下载任务标题
     * @param taskDescription 下载任务描述
     */
    public void download(String downLoadUrl, String apkFileName, String taskTitle, String taskDescription) {
        if (!TextUtils.isEmpty(apkFileName)) {
            fileName = apkFileName;
        }
        Logger.i("下载apk地址>>>" + downLoadUrl);
        if (!checkPermissions()) {
            Logger.i("DownloadManager 下载应用未检查到存储卡读写权限");
            return;
        }
        createSaveApkFilePath();

        try {
            //创建下载任务，url即任务链接
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
            //指定下载的文件类型为APK
            request.setMimeType("application/vnd.android.package-archive");
            //指定下载路径及文件名
            //request.setDestinationInExternalPublicDir(mSavePath, fileName);
            request.setDestinationInExternalFilesDir(weakReference.get(), mSavePath, fileName);
            //表示允许MediaScanner扫描到这个文件，默认不允许。
            request.allowScanningByMediaScanner();
            //获取下载管理器
            downloadManager = (DownloadManager) weakReference.get().getSystemService(Context.DOWNLOAD_SERVICE);
            //一些配置
            //允许移动网络与WIFI下载
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            //是否在通知栏显示下载进度
            //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            if (!TextUtils.isEmpty(taskTitle)) {
                request.setTitle(taskTitle);
            }
            if (!TextUtils.isEmpty(taskDescription)) {
                request.setDescription(taskDescription);
            }
            //设置可见及可管理
            //注意，Android Q之后不推荐使用
            request.setVisibleInDownloadsUi(true);
            //将任务加入下载队列
            //assert downloadManager != null;
            final long id = downloadManager.enqueue(request);
            Toast.makeText(weakReference.get(), "开始下载！", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Logger.i("下载任务异常>>>" + e.getMessage());
        }
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context mContext, Intent intent) {
            //获取下载id
            long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            checkStatus(mContext, downloadManager, myDwonloadID, intent);
        }
    };


    //注册广播接收者，监听下载状态

    //检查下载状态
    private void checkStatus(Context context, DownloadManager downloadManager, long id, Intent intent) {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(id);
        Cursor cursor = downloadManager.query(query);
        if (cursor == null) {
            Logger.i("DownloadManager cursor 查询异常!");
            return;
        }
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    Logger.i("DownloadManager 下载暂停");
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    Logger.i("DownloadManager 下载延迟");
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    Logger.i("DownloadManager 正在下载");
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    Logger.i("DownloadManager 下载完成安装apk");
                    //设置用户当前安装 apk 文件名
                    SharedPreferenceUtils.setPrefString(weakReference.get(), SharedPreferenceConstants.CURRENT_INSTALL_FILENAME, fileName);
                    //下载完成安装APK
                    Uri uri = downloadManager.getUriForDownloadedFile(id);
                    installApk(context, uri);
                    cursor.close();
                    //unregisterDownLoadReceiver(id);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    //unregisterDownLoadReceiver(id);
                    break;

            }
        }
    }


    public void registerDownloadReceiver(Activity activity) {
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        activity.getApplication().registerReceiver(receiver, filter);
    }


    /**
     * 解绑 下载通知广播接收器
     *
     * @param
     */
    public void unregisterDownLoadReceiver(Activity activity) {
        activity.getApplication().unregisterReceiver(receiver);
    }


    String[] PermissArr = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 检查存储卡读写权限
     */
    private boolean checkPermissions() {
        if (!PermissionHelper.hasPermissions(weakReference.get(), PermissArr)) {
            ToastUtils.showShort(weakReference.get(), "请设置下载应用必要存储权限！");
            ActivityCompat.requestPermissions(weakReference.get(), PermissArr, 0);
            return false;
        }
        return true;
    }


    private void installApk(Context context, Uri uri) {
        if (uri == null) {
            Log.i("HY", "DownloadManager 下载完apk安装路径为空");
            return;
        }
        Logger.i("下载完成为用户自动安装应用!");
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        context.startActivity(install);
    }


    private String mSavePath;

    private void createSaveApkFilePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //String sdPath = Environment.getExternalStorageDirectory().getPath() + "/";

            String sdPath = Environment.DIRECTORY_DOWNLOADS + "/";
            //文件保存路径
            mSavePath = sdPath + "hygamecenter/";
            File dir = new File(mSavePath);
            if (!dir.exists()) {
                dir.mkdir();
            }

            Logger.i("下载apk保存路径>>>" + mSavePath);
        } else {
            Logger.i("外部存储卡不可用!");
        }
    }


    private IDownloadlister lister = null;
    //默认下载apk文件名
    private String fileName = "huiyaogame.apk";


    public DownloadManagerUtils setLister(IDownloadlister lister) {
        this.lister = lister;
        return this;
    }


    public DownloadManagerUtils setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }


    public interface IDownloadlister {
        void success(Uri uri);
    }

}