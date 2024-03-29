package com.huiyao.gamecenter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;
/**
 * @Created by: chengzj
 * @创建时间: 2022/6/16 18:42
 * @描述:
 */
/**
 * Created by libin on 16/7/22.
 * 圆角切片
 */
public class CircleCornerForm implements Transformation {
    private Context mContext;

    public CircleCornerForm(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {

        int widthLight = source.getWidth();
        int heightLight = source.getHeight();
        int radius = DimenUtils.dp2px(mContext, 8); // 圆角半径

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(output);
        Paint paintColor = new Paint();
        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

        canvas.drawRoundRect(rectF, radius, radius, paintColor);
//        canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);

        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(source, 0, 0, paintImage);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "roundcorner";
    }


}
