package com.huiyao.gamecenter.module.main;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.CheckAppUpdateEntity;
import com.huiyao.gamecenter.util.DownloadManagerUtils;
import com.huiyao.gamecenter.util.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 精美手游页面 区服选择弹窗
 */
public class ApkUpdateDialog extends Dialog {
    @Bind(R.id.tv_apk_update_title)
    TextView tvApkUpdateTitle;
    @Bind(R.id.hy_update_quite)
    ImageView hyUpdateQuite;
    @Bind(R.id.tv_apk_update_info)
    TextView tvApkUpdateInfo;
    @Bind(R.id.btn_apk_update)
    TextView btnApkUpdate;
    private Activity context;

    public static ApkUpdateDialog apkUpdateDialog;
    CheckAppUpdateEntity.MessageBean messageBean;
    //0 更新 1 强更
    public int isForceUpdate = 0;

    public ApkUpdateDialog(@NonNull Context context) {
        super(context, R.style.base_pop);
        this.context = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogWindow(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_apk_update);
        ButterKnife.bind(this);
        initView();
    }

    public static ApkUpdateDialog getInstance(Context context) {
        if (apkUpdateDialog == null) {
            apkUpdateDialog = new ApkUpdateDialog(context);
        }
        return apkUpdateDialog;
    }


    private void initView() {
        if (messageBean != null) {
            tvApkUpdateInfo.setText(messageBean.getDescribe());
            isForceUpdate = messageBean.getForce_update();
            if (isForceUpdate == 1) {
                hyUpdateQuite.setVisibility(View.GONE);
            } else {
                hyUpdateQuite.setVisibility(View.VISIBLE);
            }
        }

    }


    @Override
    public void dismiss() {
        super.dismiss();
        ButterKnife.unbind(this);

    }

    /**
     * 判断弹窗是否显示
     *
     * @return
     */
    public void showDialog(CheckAppUpdateEntity.MessageBean messageBean) {
        //this.tipsType = tipsType;
        this.messageBean = messageBean;
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        if (!context.isFinishing()) show();
    }


    public void setDialogWindow(final Dialog dialog) {
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 8);
        WindowManager.LayoutParams lp = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        Configuration cf = dialog.getContext().getResources().getConfiguration();

        lp.width = (int) (display.getWidth() * 0.7);
        lp.height = (int) (display.getHeight() * 0.5);
        window.setGravity(Gravity.CENTER);

        //window.setWindowAnimations(R.style.HYGame_BottomDialogAnimation);
        window.setAttributes(lp);
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ButterKnife.unbind(dialog);

            }
        });
    }


    @OnClick({R.id.hy_update_quite, R.id.btn_apk_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hy_update_quite:
                dismiss();
                break;
            case R.id.btn_apk_update:
                downLoadApk();
                dismiss();
                break;
        }
    }


    private void downLoadApk() {
        if (messageBean == null) {
            return;
        }
        Logger.i("版本更新下载app>>>" + messageBean.getDownload_url());
        String apkFileName = "hygamecenter." + messageBean.getVersion_no() + ".apk";
        DownloadManagerUtils.getInstance(getOwnerActivity()).download(messageBean.getDownload_url(), apkFileName, "游戏中心下载", "");
    }

}
