package com.huiyao.gamecenter.module.vip.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.data.entity.VipInfo;

import butterknife.Bind;

/**
 * 文件名：MyVipActivity
 * 创建日期：2022/8/2 9:56
 * 描述：TODO
 */
public class MyVipActivity extends BaseActivity {
    //    MyVipContract.MyPresenter mPresenter;
    @Bind(R.id.hy_header_img)
    ImageView hyHeaderImg;
    @Bind(R.id.user_level_img)
    ImageView userLevelImg;
    @Bind(R.id.current_score_tv)
    TextView currentScoreTv;
    @Bind(R.id.all_score_tv)
    TextView allScoreTv;
    @Bind(R.id.level_info)
    TextView levelInfo;
    @Bind(R.id.start_tv)
    TextView startTv;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.end_tv)
    TextView endTv;
    @Bind(R.id.level_rule_img)
    ImageView levelRuleImg;
    @Bind(R.id.tequan_rule_img)
    ImageView tequanRuleImg;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_vip;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("我的等级");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
//        new MyVipContract.MyPresenter(this);
        VipInfo.DataBean data = (VipInfo.DataBean) getIntent().getExtras().getSerializable("data");
        VipInfo.DataBean.DetailBean detail = data.getDetail();
        Glide.with(this).load(detail.getMiddle_url()).placeholder(R.drawable.ic_my_vip_level_rule).into(levelRuleImg);
        Glide.with(this).load(detail.getDown_url()).placeholder(R.drawable.ic_my_vip_tequan).into(tequanRuleImg);
        startTv.setText(detail.getStart_grouth());
        endTv.setText(detail.getEnd_grouth());
        currentScoreTv.setText(String.format("当前积分： %d",detail.getCurrent_score()));
        allScoreTv.setText(String.format("累计积分： %d",detail.getSum_score()));
        levelInfo.setText(String.format("当前成长值： %d", data.getGrouth()));
        Glide.with(this).load(detail.getVip_img()).placeholder(R.drawable.ic_vip_level).into(userLevelImg);
//        Glide.with(this).load(detail.getVip_img()).into(userLevelImg);
        progressBar.setProgress(data.getGrouth());
    }

}
