package com.huiyao.gamecenter.module.splash;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huiyao.gamecenter.module.antiaddiction.HY_Privacy_Agree_Dialog;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.PermissionHelper;
import com.huiyao.gamecenter.util.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class HY_SplashActivity extends Activity {
    public static final String TAG = HY_SplashActivity.class.getSimpleName();
    private RelativeLayout mRel_lin;
    private ImageView imageView;
    RelativeLayout.LayoutParams mRel_lin_params;
    private HY_SplashSequence sequence = new HY_SplashSequence();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(this.mRel_lin, mRel_lin_params);
        getSplashImageResourse();
    }

    public void beforeSetContentView() {
        Logger.d(TAG, "beforeSetContentView");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 相对全布局
        this.mRel_lin = new RelativeLayout(this);
        // 设置背景颜色,回调给用户进行设置
        this.mRel_lin.setBackgroundColor(Utils.getBackgroundColor(this));
        this.mRel_lin.setVisibility(View.VISIBLE);

        this.mRel_lin_params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        // 相对布局内的图片布局
        this.imageView = new ImageView(this);
        this.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        this.mRel_lin.addView(this.imageView, imageViewParams);
    }

    void getSplashImageResourse() {
        // 需要展示的闪屏图片保存到assets文件下assetDir下面
        String assetDir = "splash_photo";
        String[] assetPaths = new String[0];
        try {
            assetPaths = getAssets().list(assetDir);
        } catch (IOException e) {
            Logger.e(TAG, "load assets splash error" + e.toString());
        }
        Logger.d(TAG, "assetsPaths size " + assetPaths.length);
        // 对数组assetPaths进行排序，按照顺序显示
        Arrays.sort(assetPaths);
        for (String str : assetPaths) {
            Logger.d(TAG, "assets splash " + str);
        }
        this.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "imageView click " + sequence.getIndex());
                if (onSplashClickListener != null)
                    onSplashClickListener.onClick(sequence.getIndex());
            }
        });

        // 资源文件前缀
        String resourcePrefix = "splash_photo";
        int count = 0;
        if (hasServerSplashImage()) {
            getServerSplashImage(new LoadServerSplashListener() {
                @Override
                public void onLoadSuccess(List data) {
                    addServerSplash(data);
                    startSplash();
                }

                @Override
                public void onLoadFailed() {
                    startSplash();
                }
            });
            return;
        }
        while (true) {
            if (count < assetPaths.length) {
                this.sequence.addSplash(new HY_SpashAsset(this.mRel_lin,
                        this.imageView, assetDir + "/" + assetPaths[count]));
            } else {
                int id = getResources().getIdentifier(resourcePrefix + count,
                        "drawable", getPackageName());
                if (id == 0) {
                    break;
                }
                Logger.d(TAG, "此代码不执行，因为闪屏图片默认都要求用户保存到assets下，并非drawable下");
            }
            count++;
        }
    }

    public interface OnSplashClickListener {
        void onClick(int index);
    }

    OnSplashClickListener onSplashClickListener;

    public void setOnSplashClickListener(OnSplashClickListener onSplashClickListener) {
        this.onSplashClickListener = onSplashClickListener;
    }

    abstract void getServerSplashImage(LoadServerSplashListener listener);

    abstract boolean hasServerSplashImage();

    public interface LoadServerSplashListener<T extends HY_SplashData> {
        void onLoadSuccess(List<T> data);

        void onLoadFailed();
    }

    private void addServerSplash(List<? extends HY_SplashData> list) {
        if (list == null || list.isEmpty()) return;
        for (HY_SplashData data : list) {
            this.sequence.addSplash(new HY_SpashAsset(this.mRel_lin,
                    this.imageView, data));
        }
    }

    boolean isFirstOnResume = true;

    @Override
    protected void onResume() {
        super.onResume();

        Logger.d(TAG, "Splash--->onResume");
        if (isFirstOnResume == false) {
            return;
        }
        if (hasServerSplashImage()) return;
        startSplash();
    }

    void startSplash() {
        this.sequence.play(this, new HY_SplashListener() {
            public void onFinish() {
                String hy_privacy_dialog_show = Utils.getManifestMeta(HY_SplashActivity.this, "HY_PRIVACY_DIALOG_SHOW");
                if (TextUtils.equals(hy_privacy_dialog_show, "1")) {
                    //展示隐私合规弹窗
                    showPrivacyDialog();
                } else {
                    //检查
                    checkPermission();
                }
            }
        });
        isFirstOnResume = false;
    }


    private void showPrivacyDialog() {
       new HY_Privacy_Agree_Dialog(HY_SplashActivity.this, new HY_Privacy_Agree_Dialog.PrivaceyAgreeCallback() {
            @Override
            public void agree() {
                checkPermission();

            }

            @Override
            public void disAgree() {
                HY_SplashActivity.this.finish();
            }
        }).showDialog();
    }


    private PermissionHelper permissionHelper;

    //判断是否需要拉权限请求
    private void checkPermission() {
        String hy_permission_need = Utils.getManifestMeta(HY_SplashActivity.this, "HY_PERMISSION_NEED");
        Logger.i(TAG, "permiss>>>" + hy_permission_need);

        if (TextUtils.equals(hy_permission_need, "1")) {
            permissionHelper = new PermissionHelper(HY_SplashActivity.this);
            permissionHelper.requestPermissions("请授予运行必要权限",
                    new PermissionHelper.PermissionListener() {
                        @Override
                        public void doAfterGrand(String... permission) {
                            //成功回调
                            HY_SplashActivity.this.onSplashStop();
                        }

                        @Override
                        public void doAfterDenied(String... permission) {
                            //失败回调
                            HY_SplashActivity.this.onSplashStop();
                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
        } else {
            HY_SplashActivity.this.onSplashStop();
        }
    }

    public abstract void onSplashStop();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
