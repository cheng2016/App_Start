package com.huiyao.gamecenter.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.util.Utils;

import java.lang.ref.WeakReference;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/15 18:16
 * @描述:
 */
public class RuleDialog extends Dialog {
    private static final String TAG = "RuleDialog";

    WeakReference<Activity> weakReference;


    private TextView titleTv, contentTv;
    private TextView copyBtn;

    private ImageView closeBtn;

    public RuleDialog(@NonNull Activity context) {
        this(context, Utils.getStyleId(context, "HYGame_base_fragment_pop"));
    }

    private RuleDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        this.weakReference = new WeakReference<Activity>(context);
        initView(context);
    }

    private RuleDialog initView(Context context) {


        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sign_rule, null);
        titleTv = (TextView) dialogView.findViewById(R.id.title);
        contentTv = (TextView) dialogView.findViewById(R.id.content);
        copyBtn = (TextView) dialogView.findViewById(R.id.copy_btn);
        closeBtn = (ImageView) dialogView.findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuleDialog.this.dismiss();
            }
        });
//        setCancelable(false);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setContentView(dialogView);
        setDialogWith(context);
        return this;
    }

    void setDialogWith(Context context){
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        Display display = window.getWindowManager().getDefaultDisplay();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels  *9 /10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    public RuleDialog setMessage(String content) {
        contentTv.setText(content);
        return this;
    }

    public RuleDialog setHtmlMessage(String content) {
        CharSequence charSequence;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(content);
        }

        contentTv.setText(charSequence);
        return this;
    }


    public RuleDialog showDialog() {
        if (!weakReference.get().isFinishing()) {
            show();
        }
        return this;
    }
}