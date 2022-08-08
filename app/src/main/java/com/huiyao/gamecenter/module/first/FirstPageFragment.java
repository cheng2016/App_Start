package com.huiyao.gamecenter.module.first;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseFragment;
import com.huiyao.gamecenter.data.entity.FirstData;
import com.huiyao.gamecenter.data.entity.FirstGameListData;
import com.huiyao.gamecenter.module.first.detail.GameDetailActivity;
import com.huiyao.gamecenter.module.first.sign.SignInActivity;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.user.InviteFriendsActivity;
import com.huiyao.gamecenter.module.web.WebActivity;
import com.huiyao.gamecenter.util.GlideRoundTransform;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.view.NoScrollGridView;
import com.huiyao.gamecenter.view.SimpleRefreshLayout;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.BaseBannerInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 首页页面
 */
public class FirstPageFragment extends BaseFragment implements FirstPageContract.View, View.OnClickListener {
    FirstPagePresenter mPresenter;
    Banner mBanner;
    XBanner mHotBanner;
    NoScrollGridView gridView;

    @Bind(R.id.game_listview)
    ListView gameListview;
    @Bind(R.id.hy_lv_refreshLayout)
    SimpleRefreshLayout refreshLayout;
    TextView signIn;
    TextView inviteFriends;
    TextView activityCenter;


    public static FirstPageFragment newInstance() {
        FirstPageFragment mainFragment = new FirstPageFragment();
        new FirstPagePresenter(mainFragment);
        return mainFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_page;
    }


    private boolean isAbleOnload = true;
    private int currentPage = 1;

    FirstGridViewAdapter mFirstGridViewAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        new FirstPagePresenter(this);
        // 添加头部文件
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_first, null);
        signIn = (TextView) headView.findViewById(R.id.sign_in);
        inviteFriends = (TextView) headView.findViewById(R.id.invite_friends);
        activityCenter = (TextView) headView.findViewById(R.id.activity_center);
        signIn.setOnClickListener(this);
        inviteFriends.setOnClickListener(this);
        activityCenter.setOnClickListener(this);
        gameListview.addHeaderView(headView);
        mGameAdapter = new GameAdapter(getActivity());
        mGameAdapter.setListener(new GameAdapter.OnSeeButtonClickListener() {
            @Override
            public void onClick(int position) {
                startToGameDetailActivity(mGameList.get(position).getRecommend_id(), mGameList.get(position).getId(), 4);
            }
        });
        gameListview.setAdapter(mGameAdapter);
        mBanner = (Banner) headView.findViewById(R.id.banner);
        mHotBanner = (XBanner) headView.findViewById(R.id.hot_banner);
        gridView = (NoScrollGridView) headView.findViewById(R.id.gridview);
        gridView.setAdapter(mFirstGridViewAdapter = new FirstGridViewAdapter(getContext()));
        mFirstGridViewAdapter.setOnGameClickListener(new FirstGridViewAdapter.OnGameOnClickListener() {
            @Override
            public void onClick(int position) {
                startToGameDetailActivity(mHotGameList.get(position).getRecommend_id(), mHotGameList.get(position).getProduct_id(), 1);
            }
        });

        refreshLayout.setEnabled(true);
        refreshLayout.setOnLoadListener(new SimpleRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                if (isAbleOnload) {
                    currentPage++;
                    mPresenter.getGameListMoreData(currentPage);
                } else {
                    refreshLayout.setLoading(false);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isAbleOnload = true;
                currentPage = 1;
                mPresenter.getFirstData(currentPage);
            }
        });
    }

    void startToGameDetailActivity(String recommend_id, int product_id, int type) {
        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
        intent.putExtra("recommend_id", recommend_id);
        intent.putExtra("type", type);
        intent.putExtra("product_id", product_id);
        startActivity(intent.putExtra("product_id", product_id));
    }

    @Override
    protected void initData() {
        mPresenter.getFirstData(1);
    }


    private void startBanner(FirstData data) {
        if (data.getData().getBanner() == null || data.getData().getBanner().size() == 0) return;
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new PicassoImageLoader());
        List<String> imgsTest = new ArrayList<>();
        for (FirstData.DataBean.BannerBean bean : data.getData().getBanner()) {
            imgsTest.add(bean.getImg());
        }
        //设置图片集合
        mBanner.setImages(imgsTest);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                FirstData.DataBean.BannerBean bean = data.getData().getBanner().get(position);
                setBannerClick(bean.getLink_type(), bean.getProduct_id(), bean.getUrl(), bean.getRecommend_id(), 0);
            }
        });
        mBanner.start();
    }


    public class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path.toString()).into(imageView);
        }
    }

    private void startHotBanner(final FirstData data) {
        if (data.getData().getHot_banner() == null || data.getData().getHot_banner().size() == 0)
            return;
        List<BaseBannerInfo> list = new ArrayList<>();
        for (FirstData.DataBean.HotBannerBean bean : data.getData().getHot_banner()) {
            list.add(new BaseBannerInfo() {
                @Override
                public Object getXBannerUrl() {
                    return bean.getImg();
                }

                @Override
                public String getXBannerTitle() {
                    return bean.getName();
                }
            });
        }
        mHotBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                if (view instanceof SimpleDraweeView) {
                    SimpleDraweeView draweeView = (SimpleDraweeView) view;
                    draweeView.setImageURI(Uri.parse((String) list.get(position).getXBannerUrl()));
                } else {
                    Glide.with(getActivity()).load(list.get(position).getXBannerUrl()).bitmapTransform(new GlideRoundTransform(getActivity().getApplicationContext())).into((ImageView) view);
                }
            }
        });
        mHotBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                FirstData.DataBean.HotBannerBean bean = data.getData().getHot_banner().get(position);
                setBannerClick(bean.getLink_type(), bean.getProduct_id(), bean.getUrl(), bean.getRecommend_id(), 2);
            }
        });
        mHotBanner.setIsClipChildrenMode(false);
        mHotBanner.setOverlapStyle(true);
        //代码设置框架占位图，也可在布局中设置
        mHotBanner.setBannerData(R.layout.layout_fresco_imageview, list);
        mHotBanner.setPageTransformer(com.stx.xhb.xbanner.transformers.Transformer.Default);
    }

    private void setBannerClick(int link_type, int product_id, String url, String recommend_id, int type) {
        if (link_type == 0) {
            startToGameDetailActivity(recommend_id, product_id, type);
        } else {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("title", "游戏详情");
            intent.putExtra("url", url);
            intent.putExtra("recommend_id", recommend_id);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }

    List<FirstGameListData.DataBean> mGameList;
    GameAdapter mGameAdapter;
    List<FirstData.DataBean.HotGameBean> mHotGameList;

    @Override
    public void refreshData(FirstData data, FirstGameListData gameListData) {
        refreshLayout.setRefreshing(false);
        mHotGameList = data.getData().getHot_game();
        mFirstGridViewAdapter.updata(mHotGameList);
        mGameList = gameListData.getData();
        mGameAdapter.updataData(mGameList);
        startBanner(data);
        startHotBanner(data);
    }

    @Override
    public void refreshDataFail() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadGameListMoreData(FirstGameListData gameListData) {
        refreshLayout.setLoading(false);
        if (gameListData == null || gameListData.getData() == null) {
            isAbleOnload = false;
            return;
        }
        if (gameListData.getData().size() < 10) {
            isAbleOnload = false;
        } else {
            isAbleOnload = true;
        }
        mGameList.addAll(gameListData.getData());
        mGameAdapter.updataData(mGameList);
    }

    @Override
    public void loadGameListMoreDataFail() {
        refreshLayout.setLoading(false);
    }

    @Override
    public void setPresenter(FirstPageContract.Presenter presenter) {
        this.mPresenter = (FirstPagePresenter) presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                toStartActivity(SignInActivity.class);
                break;
            case R.id.invite_friends:
                toStartActivity(InviteFriendsActivity.class);
                break;
            case R.id.activity_center:
                ToastUtils.showShort("a");
                break;
            default:
                break;
        }
    }


    /**
     * 跳转邀请分享给好友页面
     */
    private void toStartActivity(Class c){
        if(!LoginUtils.getInstance().isLoginSucess()){
            ToastUtils.showShort(getContext(),"当前未登录账号，请先登录账号!");
            return;
        }
        Intent intent = new Intent(getActivity(), c);
        getActivity().startActivity(intent);

    }
}
