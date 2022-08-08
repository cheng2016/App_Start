package com.huiyao.gamecenter.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.systrace.FrescoSystrace;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/7 14:20
 * @描述:
 */
public class SimpleDraweeViewFix extends SimpleDraweeView {

    public SimpleDraweeViewFix(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public SimpleDraweeViewFix(Context context) {
        super(context);
    }

    public SimpleDraweeViewFix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleDraweeViewFix(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SimpleDraweeViewFix(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("GenericDraweeView#inflateHierarchy");
        }
        GenericDraweeHierarchyBuilder builder =
                GenericDraweeHierarchyInflater.inflateBuilder(context, attrs);
        setAspectRatio(builder.getDesiredAspectRatio());
        Drawable mPlaceholderImage = builder.getPlaceholderImage();
        if (mPlaceholderImage instanceof BitmapDrawable) {
            ((BitmapDrawable) mPlaceholderImage).getBitmap().setDensity(context.getResources().getDisplayMetrics().densityDpi);
        }
        Drawable mFailureImage = builder.getFailureImage();
        if (mFailureImage instanceof BitmapDrawable) {
            ((BitmapDrawable) mFailureImage).getBitmap().setDensity(context.getResources().getDisplayMetrics().densityDpi);
        }
        setHierarchy(builder.build());
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }
/*
    private static Drawable applyLeafRounding(Drawable drawable, RoundingParams roundingParams, Resources resources) {
        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            bitmap.setDensity(resources.getDisplayMetrics().densityDpi);
            RoundedBitmapDrawable roundedBitmapDrawable = new RoundedBitmapDrawable(resources, bitmap, bitmapDrawable.getPaint());
            applyRoundingParams(roundedBitmapDrawable, roundingParams);
            return roundedBitmapDrawable;
        } else if (drawable instanceof NinePatchDrawable) {
            final NinePatchDrawable ninePatchDrawableDrawable = (NinePatchDrawable) drawable;
            RoundedNinePatchDrawable roundedNinePatchDrawable = new RoundedNinePatchDrawable(ninePatchDrawableDrawable);
            applyRoundingParams(roundedNinePatchDrawable, roundingParams);
            return roundedNinePatchDrawable;
        } else if (drawable instanceof ColorDrawable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RoundedColorDrawable roundedColorDrawable = RoundedColorDrawable.fromColorDrawable((ColorDrawable) drawable);
            applyRoundingParams(roundedColorDrawable, roundingParams);
            return roundedColorDrawable;
        } else {
            Log.w(TAG, "Don't know how to round that drawable: %s", drawable);
        }
        return drawable;
    }*/
}
