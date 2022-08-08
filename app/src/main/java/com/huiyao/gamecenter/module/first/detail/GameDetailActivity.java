package com.huiyao.gamecenter.module.first.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.AppEvent;
import com.huiyao.gamecenter.data.entity.GameDetailBean;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.util.AppUtils;
import com.huiyao.gamecenter.util.DownloadManagerUtils;
import com.huiyao.gamecenter.util.GlideRoundTransform;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;
import com.huiyao.gamecenter.wxapi.WxAuthHelp;
import com.squareup.picasso.Picasso;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

/**
 * @作者: chengzj
 * @创建时间: 2022/6/9 14:27
 * @类名: GameDetailActivity
 * @描述:
 */
public class GameDetailActivity extends BaseActivity implements GameDetailContract.View {
    GameDetailContract.Presenter mPresenter;
    private ImageView gameImg;
    private TextView gameNameTv;
    private TextView gameTypeTv;
    private TextView gameSizeTv;
    private TextView gameDescTv;
    private TextView downloadTv;
    private Button btnStartGame;


    private XBanner mXBanner;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("游戏详情");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gameImg = (ImageView) findViewById(R.id.game_img);
        gameNameTv = (TextView) findViewById(R.id.game_name_tv);
        gameTypeTv = (TextView) findViewById(R.id.game_type_tv);
        gameSizeTv = (TextView) findViewById(R.id.game_size_tv);
        gameDescTv = (TextView) findViewById(R.id.game_desc_tv);
        downloadTv = (TextView) findViewById(R.id.download_tv);
        downloadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData != null && (!Utils.isInstalled(GameDetailActivity.this, mData.getPackage_name()))) {
                    DownloadManagerUtils.getInstance(GameDetailActivity.this).download(mData.getUrl());
                }
            }
        });
        btnStartGame = (Button) findViewById(R.id.btn_start_game);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) return;
                if (mData.getGame_type() == 0) {
                    if (Utils.isInstalled(GameDetailActivity.this, mData.getPackage_name())) {
                        AppUtils.openOtherApp(GameDetailActivity.this, mData.getPackage_name());
                    } else {
                        ToastUtils.showShort("请先下载游戏！");
                    }
                    return;
                }
                WxAuthHelp.getInstance().regToWx(GameDetailActivity.this);
                WxAuthHelp.getInstance().toWxCode(mData.getGame_code(), mData.getGame_param());
            }
        });

        mXBanner = (XBanner) findViewById(R.id.xbanner);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                if (view instanceof SimpleDraweeView) {
                    SimpleDraweeView draweeView = (SimpleDraweeView) view;
                    draweeView.setImageURI(Uri.parse(mData.getPicture().get(position)));
                } else {
                    Glide.with(GameDetailActivity.this).load(mData.getPicture().get(position)).bitmapTransform(new GlideRoundTransform(getApplicationContext())).into((ImageView) view);
                }
            }
        });
        mXBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Log.i("onPageSelected===>", i + "");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //代码设置框架占位图，也可在布局中设置
//        mXBanner.setBannerPlaceholderImg(R.drawable.default_image, ImageView.ScaleType.CENTER_CROP);
    }


    String recommend_id;
    int product_id, type;

    @Override
    protected void initData() {
        new GameDetailPresenter(this);
        product_id = getIntent().getIntExtra("product_id", 0);
        recommend_id = getIntent().getStringExtra("recommend_id");
        type = getIntent().getIntExtra("type", 0);
        RequestParameterUtils.putAppEvent(String.valueOf(product_id), new AppEvent(recommend_id, product_id, type));
        mPresenter.getDetailData(product_id);
    }

    GameDetailBean.DataBean mData;

    @Override
    public void loadGameDetailDataSuccess(GameDetailBean data) {
        mLoadingView.showContent();
        mData = data.getData();
        if (!TextUtils.isEmpty(data.getData().getIcon())) {
            Picasso.with(GameDetailActivity.this).load(data.getData().getIcon()).into(gameImg);
        }
        gameNameTv.setText(mData.getName());
        gameTypeTv.setText("游戏类型：" + mData.getType());
        gameSizeTv.setText("游戏大小：" + mData.getSize() / 1024 / 1024 + " M");
        Log.i(TAG, "loadGameDetailData  " + mData.getDirection());
        gameDescTv.setText(mData.getDesc());
        if (Utils.isInstalled(GameDetailActivity.this, mData.getPackage_name()) || mData.getGame_type() == 1) {
            downloadTv.setVisibility(View.INVISIBLE);
        }

        //代码设置框架占位图，也可在布局中设置
//        mXBanner.setBannerPlaceholderImg(R.mipmap.xbanner_logo, ImageView.ScaleType.CENTER_CROP);
        //添加轮播图片数据（图片数据不局限于网络图片、本地资源文件、View 都可以）,刷新数据也是调用该方法
        if (data.getData() == null || data.getData().getPicture() == null || data.getData().getPicture().size() == 0)
            return;
        if (mData.getDirection() == 0) {
            ViewGroup.LayoutParams params = mXBanner.getLayoutParams();
            params.height = params.height * 3 / 5;
            Log.d(TAG, "width : " + params.width + " , height : " + params.height);
            mXBanner.setLayoutParams(params);
            mXBanner.setClipChildrenLeftRightMargin(0, 120);
        }
        mXBanner.setIsClipChildrenMode(true);
        mXBanner.setData(R.layout.layout_fresco_imageview, mData.getPicture(), mData.getPicture());
//        mXBanner.setBannerData();
        mXBanner.setPageTransformer(Transformer.Default);
    }

    @Override
    public void loadGameDetailDataFail(String message) {
        mLoadingView.setErrorText(message);
        mLoadingView.showError();
        mLoadingView.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getDetailData(product_id);
            }
        });
    }

    @Override
    public void setPresenter(GameDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
