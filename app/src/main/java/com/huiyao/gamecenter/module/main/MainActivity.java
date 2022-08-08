package com.huiyao.gamecenter.module.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.source.remote.AppInitHelp;
import com.huiyao.gamecenter.util.DownloadManagerUtils;
import com.huiyao.gamecenter.util.Logger;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.vp_horizontal_ntb)
    ViewPager viewPager;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;


    public static final int DEFAULT_INDEX = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                radioGroup.check(position + 1);
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.first_page);
                        break;
                    case 1:
                        radioGroup.check(R.id.vip);
                        break;
                    case 2:
                        radioGroup.check(R.id.message);
                        break;
                    case 3:
                        radioGroup.check(R.id.me);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        radioGroup.check(R.id.first_page);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Logger.i(TAG, "onCheckedChanged：" + checkedId);
//                viewPager.setCurrentItem(checkedId - 1);
                switch (checkedId) {
                    case R.id.first_page: //
                        checkedId = 0;
                        break;
                    case R.id.vip: //
                        checkedId = 1;
                        break;
                    case R.id.message: //
                        checkedId = 2;
                        break;
                    case R.id.me: //发现
                        checkedId = 3;
                        break;
                    default:
                        break;
                }
                viewPager.setCurrentItem(checkedId);
            }
        });
        DownloadManagerUtils.getInstance(this).registerDownloadReceiver(this);
    }

    @Override
    protected void initData() {
        Logger.d("请求初始化接口...");
        AppInitHelp.initAppData(this);
        //检查是否有app版本更新
        CheckAppUpdateUtils.getInstance(this).checkAppUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadManagerUtils.getInstance(this).unregisterDownLoadReceiver(this);
    }

    //双击返回键 退出
    //----------------------------------------------------------------------------------------------
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            ToastUtils.showShort("再次点击返回键退出");
        }
        mBackPressed = System.currentTimeMillis();
    }

}
