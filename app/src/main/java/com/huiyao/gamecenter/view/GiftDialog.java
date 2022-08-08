package com.huiyao.gamecenter.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.util.ToastUtils;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/23 11:43
 * @描述:
 */
public class GiftDialog extends Dialog {

    private static GiftDialog instance;

    private Activity context;

    public static GiftDialog getInstance(Context context) {
        if (instance == null) {
            instance = new GiftDialog(context);
        }
        return instance;
    }

    private TextView titleTv, contentTv;
    private TextView copyBtn;

    private GiftDialog(@NonNull Context context) {
        super(context);
        this.context = (Activity) context;
        initView(context);
    }

    private GiftDialog initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.mipmap.transparent_bg);
        Window window = this.getWindow();
        WindowManager.LayoutParams mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_gift, null);
        titleTv = (TextView) dialogView.findViewById(R.id.title);
        contentTv = (TextView) dialogView.findViewById(R.id.content);
        copyBtn = (TextView) dialogView.findViewById(R.id.copy_btn);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = contentTv.getText().toString().trim().split("：").length == 2 ? contentTv.getText().toString().trim().split("：")[1] : "";
                if (TextUtils.isEmpty(str)) return;
                ClipboardManager clipboard = (ClipboardManager)
                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", str);
                clipboard.setPrimaryClip(clip);
                ToastUtils.showShort("礼包码已复制,请在游戏中兑换领取！");
            }
        });
//        setCancelable(false);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setContentView(dialogView);

        return this;
    }


    public GiftDialog showDialog(String content) {
        contentTv.setText("礼包码：" + content);
        return showDialog();
    }

    public GiftDialog showDialog() {
        if (!context.isFinishing()) {
            show();
        }
        return this;
    }
}