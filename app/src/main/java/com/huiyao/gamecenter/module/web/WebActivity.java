package com.huiyao.gamecenter.module.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.util.Logger;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/23 10:10
 * @描述:
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleTv;
    private FrameLayout contentLayout;

    private WebView webView;

    private ProgressBar pbProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_third_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        findViewById(R.id.back_button).setOnClickListener(this);
        titleTv = (TextView) findViewById(R.id.title);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        webView = new WebView(this);
        contentLayout.addView(webView, 0);

        WebSettings webSettings = webView.getSettings();
        webSettings.setDatabaseEnabled(true);
        final String dbPath = getApplicationContext().getDir("db", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        final String cachePath = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(5 * 1024 * 1024);

        webView.setWebViewClient(new WebViewClient() {
           /* @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }*/
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("error:" + e.toString());
                    return false;
                }
                Logger.i("webViewUrl:" + url);
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    pbProgress.setVisibility(View.GONE);
                } else {
                    pbProgress.setVisibility(View.VISIBLE);
                    // 加载中
                    pbProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }












        });
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
                        webView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        titleTv.setText(TextUtils.isEmpty(getIntent().getStringExtra("title")) ? "游戏中心" : getIntent().getStringExtra("title"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
            webView.loadUrl(TextUtils.isEmpty(getIntent().getStringExtra("url")) ? "http://www.baidu.com" : getIntent().getStringExtra("url"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            contentLayout.removeView(webView);

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();

            try {
                webView.destroy();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
        }
    }


































}
