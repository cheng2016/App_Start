package com.huiyao.gamecenter.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.huiyao.gamecenter.R;


public class CustomDialog extends Dialog {

    protected Context mContext;

    protected WindowManager.LayoutParams mLayoutParams;

    private Button cancelBtn,confirmBtn;

    private TextView titleTv,contentTv;

    public CustomDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private CustomDialog initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.mipmap.transparent_bg);
        mContext = context;
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
        mContext = context;

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom, null);
        titleTv = (TextView) dialogView.findViewById(R.id.title);
        contentTv = (TextView) dialogView.findViewById(R.id.content);
        confirmBtn = (Button) dialogView.findViewById(R.id.confirm);
        cancelBtn = (Button) dialogView.findViewById(R.id.cancel);
//        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(dialogView);

        return this;
    }

    public CustomDialog setTitle(String message){
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText(message);
        return this;
    }

    public CustomDialog setMessage(String message){
        contentTv.setVisibility(View.VISIBLE);
        contentTv.setText(message);
        return this;
    }

    public CustomDialog setPositiveButton(String str, final View.OnClickListener listener){
        confirmBtn.setText(str);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dismiss();
            }
        });
        return this;
    }


    public CustomDialog setCancelButton(String str, final View.OnClickListener listener){
        cancelBtn.setText(str);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dismiss();
            }
        });
        return this;
    }

    public CustomDialog showDialog() {
        show();
        return this;
    }
}
