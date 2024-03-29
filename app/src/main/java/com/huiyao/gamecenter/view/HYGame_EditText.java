package com.huiyao.gamecenter.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 * 输入文本框 右边有自带的删除按钮 当有输入时，显示删除按钮，当无输入时，不显示删除按钮。
 * 
 * 
 */
public class HYGame_EditText extends EditText implements OnFocusChangeListener,
        TextWatcher {
	/**
	 * 删除按钮的引用
	 */
	private Drawable mClearDrawable;
	// 是否为登录
	private boolean isLogin = false;
	/**
	 * 控件是否有焦点
	 */
	private boolean hasFoucs;

	public HYGame_EditText(Context context) {
		this(context, null);
	}

	public HYGame_EditText(Context context, AttributeSet attrs) {
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public HYGame_EditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		init(context);
	}

//	private void init(Context context) {
//		// 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
//
//		mClearDrawable = getCompoundDrawables()[2];
//		if (mClearDrawable != null) {
//			isLogin = true;
//		}
//		if (mClearDrawable == null) {
//			// throw new
//			// NullPointerException("You can add drawableRight attribute in XML");
//			Drawable delect_png = context.getResources().getDrawable(HY_Utils.getDrawableId(context, "u9pay_login_text_delete_normal"));
//			mClearDrawable = delect_png;
//		}
//
//		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
//				mClearDrawable.getIntrinsicHeight());
//		// 默认设置隐藏图标
//		setClearIconVisible(false);
//		// 设置焦点改变的监听
//		setOnFocusChangeListener(this);
//		// 设置输入框里面内容发生改变的监听
//		addTextChangedListener(this);
//	}

	/**
	 * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
	 * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));

				if (touchable) {
					if (isLogin) {

					} else {
						setText("");
					}

				}
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	/**
	 * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
	 * 
	 * @param visible
	 */
	protected void setClearIconVisible(boolean visible) {
		if (!isLogin) {
			Drawable right = visible ? mClearDrawable : null;
			setCompoundDrawables(getCompoundDrawables()[0],
					getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
		}
	}

	/**
	 * 当输入框里面内容发生变化的时候回调的方法
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		if (hasFoucs) {
			setClearIconVisible(s.length() > 0);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

}