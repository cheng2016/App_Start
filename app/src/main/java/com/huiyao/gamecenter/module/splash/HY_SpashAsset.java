package com.huiyao.gamecenter.module.splash;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.huiyao.gamecenter.util.ImageDownLoader;
import com.huiyao.gamecenter.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class HY_SpashAsset extends HY_SplashBaseImage {
    private String assetPath;

    private HY_SplashData data;

    public HY_SpashAsset(View layout, ImageView imageView, String assetPath) {
        super(layout, imageView);
        this.assetPath = assetPath;
    }

    public HY_SpashAsset(View layout, ImageView imageView, HY_SplashData data) {
        super(layout, imageView);
        this.data = data;
    }

    @Override
    public void loadImage(final Activity context, final ImageView imageView,
                          final HY_SplashBaseImage.LoadSplashCallback callback) {
        Logger.d(TAG, "start loadImage..  assetPath : " + assetPath);
        if (data == null){
            new LoadImageTask(context, imageView, callback).execute(assetPath);
        } else{
            ImageDownLoader.getInstance(context).load(imageView, data.getUrl(), new ImageDownLoader.OnLoadImageListener() {
                @Override
                public void onSuccess(ImageView imageView, Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    callback.onLoadSuccess();
                }

                @Override
                public void onFailed() {
                    callback.onLoadFailed();

                }
            });
        }
    }

    private static final class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        WeakReference<Application> applicationWeakReference;
        WeakReference<ImageView> imageViewWeakReference;
        LoadSplashCallback callback;

        public LoadImageTask(Activity context, ImageView imageView, LoadSplashCallback callback) {
            this.applicationWeakReference = new WeakReference<>(context.getApplication());
            this.imageViewWeakReference = new WeakReference<>(imageView);
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            if (params == null || params.length == 0) return null;
            Bitmap bitmap = null;
            try {
                InputStream stream = applicationWeakReference.get().getAssets().open(params[0]);
                bitmap = BitmapFactory.decodeStream(stream);
            } catch (IOException e) {
                Logger.e(TAG, "load asset splash failed : "
                        + params[0] + e.toString());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                callback.onLoadFailed();
            } else {
                imageViewWeakReference.get().setImageBitmap(result);
                callback.onLoadSuccess();
            }
        }
    }

}
