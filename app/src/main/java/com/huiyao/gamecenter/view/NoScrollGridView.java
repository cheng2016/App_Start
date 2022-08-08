package com.huiyao.gamecenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Author: chengzj
 * @CreateDate: 2022/6/8 15:55
 * @ClassName: NoScrollGridView
 * @Description: 无滑动GridView
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}