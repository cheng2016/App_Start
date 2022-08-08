package com.huiyao.gamecenter.module.web;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;

import java.io.File;


public class CustomerWebActivity extends Activity implements
        OnClickListener {
    /**
     * 拍照/选择文件请求码
     */
    private static final int REQ_CAMERA = 1;
    private static final int REQ_CHOOSE = REQ_CAMERA + 1;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArray;

    private static final String PATH = Environment.getExternalStorageDirectory() + "/DCIM";
    private String imageName;
    private Activity mActivity;
    private String mCSUrl;
    private String mTitle;

    private TextView mCSTitle;
    private ImageView mBackBtn;
    private WebView mCSWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);
        mActivity = this;
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(Utils.getLayoutId(mActivity,
                "hygame_customer_service_layout"));

        Intent intent = mActivity.getIntent();
        mCSUrl = intent.getStringExtra("url");
        mTitle = intent.getStringExtra("title");
        initView(mCSUrl);
        //获取企点客服链接地址
        //csInfoRequest();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(String csUrl) {
        mCSTitle = (TextView) findViewById(Utils.getId(mActivity,
                "hygame_cs_title_text"));
        mCSTitle.setText(TextUtils.isEmpty(mTitle) ? "客服中心" : mTitle);
        mBackBtn = (ImageView) findViewById(Utils.getId(mActivity,
                "hygame_cs_back_btn"));
        mCSWebView = (WebView) mActivity.findViewById(Utils.getId(mActivity,
                "hygame_cs_webview"));
        mProgressBar = (ProgressBar) mActivity.findViewById(Utils.getId(
                mActivity, "hygame_cs_pro"));

        mCSWebView.loadUrl(csUrl);
        mCSWebView.getSettings().setUseWideViewPort(true);
        mCSWebView.getSettings().setLoadWithOverviewMode(true);
        mCSWebView.getSettings().setJavaScriptEnabled(true);
        mCSWebView.getSettings().setAllowContentAccess(true);//是否可访问Content Provider的资源，默认值 true
        mCSWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mCSWebView.getSettings().setDomStorageEnabled(true); // 开启 DOM storage API 功能
        mCSWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);  //支持通过JS打开新窗口
        mCSWebView.getSettings().setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提
        mCSWebView.getSettings().setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放


        mCSWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

		/*mCSWebView.resumeTimers();

		mCSWebView.setDrawingCacheEnabled(true);
		mCSWebView.buildDrawingCache();
		mCSWebView.buildLayer();*/

        mCSWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mCSWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                // For Android >=3.0
                if (acceptType.equals("image/*")) {
                    if (mUploadMessage != null) {
                        mUploadMessage.onReceiveValue(null);
                        return;
                    }
                    mUploadMessage = uploadMsg;
                    selectImage();

                } else {
                    onReceiveValue();
                }
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Logger.i("打开相册选择点击<3.0");
                openFileChooser(uploadMsg, "image/*");
            }

            // For Android  >= 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Logger.i("打开相册选择点击>4.1");
                openFileChooser(uploadMsg, acceptType);
            }

            // For Android  >= 5.0
            @Override
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                //打不开图片选择 所以屏蔽判断条件 && fileChooserParams.getAcceptTypes()[0].equals("image/*");

                Logger.i("打开相册选择点击>5.0");
                if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                        && fileChooserParams.getAcceptTypes().length > 0) {
                    if (mUploadMessageArray != null) {
                        mUploadMessageArray.onReceiveValue(null);
                    }
                    mUploadMessageArray = filePathCallback;
                    selectImage();
                } else {
                    onReceiveValue();
                }
                return true;
            }

        });

        mCSWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("error:" + e.toString());
                    return false;
                }
                Logger.i("url:" + url);
                return true;
            }
        });
        Logger.d("csUrl:" + mCSWebView.getUrl());
        mBackBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // 按钮控制-3s
        if (!Utils.isFastDoubleClick()) {
            if (v == mBackBtn) {
                mCSWebView = null;
                mActivity.finish();// 返回按钮
            }
        }
    }

	/*@Override
	public void onBackPressed() {
		if (mCSWebView.canGoBack()) {
			mCSWebView.goBack();
		} else {
			mActivity.finish();
		}
	}*/

    private void selectImage() {
        Logger.i("打开相册选择");
        String[] selectPicTypeStr = {"拍摄", "从相册中选择"};
        new AlertDialog.Builder(this)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        onReceiveValue();
                    }
                })
                .setItems(selectPicTypeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        switch (which) {
                            // 相机拍摄，此处调用系统相机拍摄图片，开发者可根据实际情况选用系统相机还是自己在这之上封装一层从而符合APP风格
                            case 0:
                                    	/*if (HY_IsSimulator.isHasBlueTooth()||HY_IsSimulator.isHasLightSensorManager(mActivity)||HY_IsSimulator.checkIsNotRealPhone()){
                                    		HY_ToastUtils.show(mActivity,"模拟器暂不支持该功能");
										}else{
                                        	openCamera();
										}*/
                                openCamera();

                                break;
                            // 手机相册，此处调用系统相册选择图片，开发者可根据实际情况选用系统相册还是自己在这之上封装一层从而符合APP风格
                            case 1:
                                openAlbum();
                                break;
                            default:
                                break;
                        }

                    }
                })
                .show();
    }

    private void openAlbum() {
        Logger.d("openAlbum");
        if (!hasSDcard()) {
            return;
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CHOOSE);
        }
    }


    private boolean checkCameraPrimss() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1001);
                Logger.d("获取权限");
                return false;
            }
        }

        return true;
    }

    private void openCamera() {
        Logger.d("openCamera");
        if (!checkCameraPrimss()) {
            Logger.i("无摄像权限");
            ToastUtils.showShort(this, "请允许摄像头使用权限");
            onReceiveValue();
            return;
        }
        if (!hasSDcard()) {
            return;
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                imageName = System.currentTimeMillis() + ".png";
                Logger.d("imageName===" + imageName);
                File file = new File(PATH);
                Logger.d("PATH===" + PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(PATH, imageName)));
                startActivityForResult(intent, REQ_CAMERA);
            }


        }
    }

    private boolean hasSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!flag) {
            ToastUtils.showShort(mActivity, "请插入手机存储卡再使用本功能");
            onReceiveValue();
        }
        return flag;
    }

    /**
     * 返回文件选择
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            onReceiveValue();
            return;
        }
        switch (requestCode) {
            case REQ_CAMERA:
                Logger.d("onActivityResult===>REQ_CAMERA");
                File fileCamera = new File(PATH, imageName);
                handleFile(fileCamera);
                break;
            case REQ_CHOOSE:
                Logger.d("onActivityResult===>REQ_CHOOSE");
                Uri uri = intent.getData();
                String absolutePath = getAbsolutePath(this, uri);
                Logger.d("absolutePath=" + absolutePath);
                File fileAlbum = new File(absolutePath);
                handleFile(fileAlbum);
                break;
        }
    }

    private void handleFile(File file) {
        if (file.isFile()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (null == mUploadMessageArray) {
                    return;
                }
                Uri uri = Uri.fromFile(file);
                Uri[] uriArray = new Uri[]{uri};
                mUploadMessageArray.onReceiveValue(uriArray);
                mUploadMessageArray = null;
            } else {
                if (null == mUploadMessage) {
                    return;
                }
                Uri uri = Uri.fromFile(file);
                mUploadMessage.onReceiveValue(uri);
                mUploadMessage = null;
            }
        } else {
            onReceiveValue();
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCSWebView != null && mCSWebView.canGoBack()) {
                mCSWebView.goBack();
            }
            return true;
        } else {
            mCSWebView = null;
            mActivity.finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    public String getAbsolutePath(final Context context,
                                  final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(
                            MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void onReceiveValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadMessageArray != null) {
                mUploadMessageArray.onReceiveValue(null);
                mUploadMessageArray = null;
            }
        } else {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
        }
    }
}
