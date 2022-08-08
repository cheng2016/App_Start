package com.huiyao.gamecenter.module.welfare;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.base.BaseActivity;
import com.huiyao.gamecenter.common.AppEventType;
import com.huiyao.gamecenter.common.AppEventUploadUtils;
import com.huiyao.gamecenter.common.CommonEventMessage;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackData;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackTaskData;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.module.welfare.adapter.WelfareLeveTaskLvAdapter;
import com.huiyao.gamecenter.module.welfare.adapter.WelfarePayTaskLvAdapter;
import com.huiyao.gamecenter.util.AppUtils;
import com.huiyao.gamecenter.util.DownloadManagerUtils;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;
import com.huiyao.gamecenter.util.Utils;
import com.huiyao.gamecenter.view.SimpleRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/***
 * 福利页面现金红包 跳转 "精美手游页面"
 */
public class CaseRedpackDetailActivity extends BaseActivity implements CaseRedpackDetailContract.View {
    @Bind(R.id.back_button)
    RelativeLayout backButton;
    @Bind(R.id.hy_case_game_icon)
    ImageView hyCaseGameIcon;
    @Bind(R.id.tv_case_game_name)
    TextView tvCaseGameName;
    @Bind(R.id.tv_case_game_periods)
    TextView tvCaseGamePeriods;
    @Bind(R.id.tv_case_game_describe)
    TextView tvCaseGameDescribe;
    @Bind(R.id.tv_case_redpack_day)
    TextView tvCaseDay;
    @Bind(R.id.tv_case_redpack_money)
    TextView tvCaseRedpackMoney;
    @Bind(R.id.tv_register_tag)
    TextView tvRegisterTag;
    @Bind(R.id.tv_refrsh)
    TextView tvRefrsh;
    @Bind(R.id.tv_switch_account)
    TextView tvSwitchAccount;
    @Bind(R.id.tv_uninstalled_tips)
    TextView tvUninstalledTips;
    @Bind(R.id.tv_role_name)
    TextView tvRoleName;
    @Bind(R.id.tv_zone_name)
    TextView tvZoneName;
    @Bind(R.id.tv_role_level)
    TextView tvRoleLevel;
    @Bind(R.id.tv_role_recharge_noney)
    TextView tvRoleRechargeNoney;
    @Bind(R.id.rl_top_info)
    RelativeLayout rlTopInfo;
    @Bind(R.id.top_menu)
    RadioGroup topMenu;
    @Bind(R.id.hy_float_redpack_detail_listview)
    ListView hyFloatRedpackDetailListview;
    @Bind(R.id.hy_lv_refreshLayout)
    SimpleRefreshLayout hyLvRefreshLayout;
    @Bind(R.id.btn_start_game)
    Button btnStartGame;
    @Bind(R.id.rl_zone_info)
    RelativeLayout rlZoneInfo;
    @Bind(R.id.tv_no_zone_info_tip)
    TextView tvNoZoneInfoTip;
    private CaseRedpackDetailPresenter caseRedpackDetailPresenter;
    private boolean isInstalledGame = false;
    private String packageName = "";
    private HyCaseRedpackData hyCaseRedpackData;
    //等级红包数据集合
    private List<HyCaseRedpackTaskData.LevelTaskBean> levelTaskList = new ArrayList<>();
    //充值红包数据集合
    private List<HyCaseRedpackTaskData.PayTaskBean> payTaskList = new ArrayList<>();
    private List<HyCaseRedpackData.RoleDataBean> roleDataList = new ArrayList<>();

    private WelfareLeveTaskLvAdapter welfareLeveTaskLvAdapter;
    private WelfarePayTaskLvAdapter welfarePayTaskLvAdapter;

    //当前展示 任务类型  1 等级任务 2 充值任务 3 赢金任务 4 排名任务
    private int currentTaskType = 1;
    //游戏活动id
    private String id;
    //当前选择展示的区服角色信息
    private HyCaseRedpackData.RoleDataBean currentRoleBean;

    private Activity mActivity;
    private String recommend_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        recommend_id = getIntent().getStringExtra("recommend_id");
        Logger.i("跳转现金红包详情页id>>>接收到"+id);
        mActivity = this;
        new CaseRedpackDetailPresenter(this, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonEventMessage eventMessage = new CommonEventMessage();
        eventMessage.setTag(3);
        EventBus.getDefault().post(eventMessage);
    }

    @Override
    protected void initData() {
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ((TextView) findViewById(R.id.title)).setText("精美手游");
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hyLvRefreshLayout.setEnabled(false);
        welfareLeveTaskLvAdapter = new WelfareLeveTaskLvAdapter(this);
        welfareLeveTaskLvAdapter.setOnClickCallback(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postion = (int) view.getTag();
                caseRedpackDetailPresenter.receiveLeveTaskRedpack(levelTaskList.get(postion),currentRoleBean);
            }
        });
        welfarePayTaskLvAdapter = new WelfarePayTaskLvAdapter(this);
        welfarePayTaskLvAdapter.setOnClickCallback(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //累充红包点击领取事件
                int postion = (int) view.getTag();
                caseRedpackDetailPresenter.receivePayTaskRedpack(payTaskList.get(postion),currentRoleBean);
            }
        });

        topMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.level_radio) {
                    currentTaskType = 1;
                }
                if (i == R.id.recharge_radio) {
                    currentTaskType = 2;
                }
                refreshTaskListview();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_redpack_detail;
    }


    @Override
    public void setPresenter(CaseRedpackDetailContract.Presenter presenter) {
        this.caseRedpackDetailPresenter = (CaseRedpackDetailPresenter) presenter;
        getCaseRedpackDetailData();
    }




    /**
     * 获取游戏详情 数据 除 任务列表以外数据（游戏基本信息，账号包含的 角色区服信息）
     */
    public void getCaseRedpackDetailData(){
        if(caseRedpackDetailPresenter!=null) {
            caseRedpackDetailPresenter.getCaseRedpackDetailData(id,recommend_id);
        }
    }


    /**
     * 获取等级,累充任务列表数据
     */
    public void getTaskListData(String activity_id,String uid_role_id, String zoneId){
        if(caseRedpackDetailPresenter!=null) {
            //ToastUtils.showShort(this,"获取数据...");
            caseRedpackDetailPresenter.getCaseRedpackTaskListData(activity_id,uid_role_id,zoneId,recommend_id);
        }
    }


    /**
     * 刷新游戏 信息,及账号 角色相关信息
     * @param hyCaseRedpackData
     */
    @Override
    public void refreshUI(HyCaseRedpackData hyCaseRedpackData) {
        if (hyCaseRedpackData != null) {
            this.hyCaseRedpackData = hyCaseRedpackData;
            Glide.with(this).load(hyCaseRedpackData.getIcon()).into(hyCaseGameIcon);
            tvCaseGameName.setText(hyCaseRedpackData.getProduct_name());
            tvCaseRedpackMoney.setText("+" + hyCaseRedpackData.getAmount() + "元");
            tvCaseGameDescribe.setText(hyCaseRedpackData.getType());
            tvCaseDay.setText("剩余"+hyCaseRedpackData.getExtra_day()+"天");
            packageName = hyCaseRedpackData.getPackage_name();
            //检查手机上是否有这个安装包
            checkInstalledApk(packageName);
            //设置角色区服信息
            currentRoleBean = null;
            setRoleZoneInfo(hyCaseRedpackData);
        }
    }


    /**
     *设置显示当前选择选择的区服角色信息
     */
    private void setRoleZoneInfo(HyCaseRedpackData hyCaseRedpackData){
        UserInfoEntity userInfoEntity = LoginUtils.getInstance().getUser();
        if(userInfoEntity==null){
            Logger.i("用户信息为空不设置角色信息");
            return;
        }
        List<HyCaseRedpackData.RoleDataBean> role_data = hyCaseRedpackData.getRole_data();
        if(role_data!=null&role_data.size()>0){
            tvRegisterTag.setText("已绑定(账号ID:"+userInfoEntity.getUsername()+")");
            roleDataList.clear();
            roleDataList.addAll(role_data);
            if(currentRoleBean==null) {
                currentRoleBean = roleDataList.get(0);
            }
            currentRoleBean.setChecked(true);
            //获取等级任务累充任务列表数据
            getTaskListData(id,currentRoleBean.getUid_role_id(),currentRoleBean.getZone_id());
            rlZoneInfo.setVisibility(View.VISIBLE);
            tvNoZoneInfoTip.setVisibility(View.INVISIBLE);
            tvRoleName.setText("角色名称："+currentRoleBean.getRole_name());
            tvZoneName.setText("区服："+currentRoleBean.getZone_name());
            tvRoleLevel.setText("当前等级："+currentRoleBean.getRole_level());
            tvRoleRechargeNoney.setText("累计充值："+currentRoleBean.getTotal_amount()+"元");

        }else{
            tvRegisterTag.setText("尚未注册角色(ID:"+userInfoEntity.getUsername()+")");
            rlZoneInfo.setVisibility(View.INVISIBLE);
            tvNoZoneInfoTip.setVisibility(View.VISIBLE);
            tvNoZoneInfoTip.setText(hyCaseRedpackData.getRole_status()+"");
        }

    }




    /**
     * 刷新 任务列表数据（包含等级任务，充值任务）
     */
    @Override
    public void refreshTaskListUI(HyCaseRedpackTaskData hyCaseRedpackTaskData) {
        //刷新设置任务数据ui
        levelTaskList.clear();
        payTaskList.clear();
        levelTaskList.addAll(hyCaseRedpackTaskData.getLevel_task());
        payTaskList.addAll(hyCaseRedpackTaskData.getPay_task());
        //通知刷新任务listView
        refreshTaskListview();
    }

    //等级红包领取完刷新UI
    @Override
    public void notifyReceiveLvTaskSucess(HyCaseRedpackTaskData.LevelTaskBean levelTaskBean) {
        //levelTaskList.add(levelTaskBean);
        ToastUtils.showShort(this,"领取红包成功!");
        welfareLeveTaskLvAdapter.updateData(levelTaskList);
    }

    //累充红包领取完刷新UI
    @Override
    public void notifyReceivePayTaskSucess(HyCaseRedpackTaskData.PayTaskBean payTaskBean) {
        //payTaskList.add(payTaskBean);
        ToastUtils.showShort(this,"领取红包成功!");
        welfarePayTaskLvAdapter.updateData(payTaskList);
    }


    /**
     * 刷新设置任务数据ui
     */
    private void refreshTaskListview() {
        switch (currentTaskType) {
            case 1:
                if (welfareLeveTaskLvAdapter != null) {
                    hyFloatRedpackDetailListview.setAdapter(welfareLeveTaskLvAdapter);
                    welfareLeveTaskLvAdapter.updateData(levelTaskList);
                }
                break;
            case 2:
                if (welfarePayTaskLvAdapter != null) {
                    hyFloatRedpackDetailListview.setAdapter(welfarePayTaskLvAdapter);
                    welfarePayTaskLvAdapter.updateData(payTaskList);
                }
                break;
        }
    }


    @OnClick({R.id.tv_refrsh, R.id.tv_switch_account, R.id.btn_start_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refrsh:
                //checkInstalledApk(packageName);
                //重新获取用户角色相关数据
                ToastUtils.showShort(this,"刷新数据中");
                getCaseRedpackDetailData();
                break;
            case R.id.tv_switch_account:
                GameZoneSelectorDialog.getInstance(this).showDialog(zoneSelectorCallback,roleDataList);
                break;
            case R.id.btn_start_game:
                if (isInstalledGame) {
                    startGame();
                } else {
                    downLoadGameApk();
                }
                break;
        }
    }


    /**
     * 区服选择回调
     */
    GameZoneSelectorDialog.ZoneSelectorCallback zoneSelectorCallback = new GameZoneSelectorDialog.ZoneSelectorCallback() {
        @Override
        public void selectorData(HyCaseRedpackData.RoleDataBean roleDataBean) {
            if(currentRoleBean == roleDataBean){
                return;
            }
            currentRoleBean.setChecked(false);
            currentRoleBean = roleDataBean;
            currentRoleBean.setChecked(true);
            ToastUtils.showShort(mActivity,"刷新数据中");
            //重新设置角色信息
            setRoleZoneInfo(hyCaseRedpackData);
        }
    };


    private void startGame() {
        //跳转游戏activity
        AppUtils.openOtherApp(this, packageName);
    }


    /**
     *
     * 下载游戏包
     **/
    private void downLoadGameApk() {
        if (hyCaseRedpackData != null) {
            String downLoadUrl = hyCaseRedpackData.getDownload_url();
            String fileName = hyCaseRedpackData.getProduct_name() + ".apk";
            String taskTitle = hyCaseRedpackData.getProduct_name();
            String taskDescribe = hyCaseRedpackData.getType();
            DownloadManagerUtils downloadManagerUtils = DownloadManagerUtils.getInstance(this);
            downloadManagerUtils.download(downLoadUrl, fileName, taskTitle, taskDescribe);
            //下载事件上报
            AppEventUploadUtils.getInstance(this).eventUpLoad(recommend_id, AppEventType.GAME_DOWNLOAD);

        } else {
            Logger.i("现金红包详情页未获取到游戏包相关信息");
        }
    }


    /**
     * 检查是否安装了游戏包
     */
    private void checkInstalledApk(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Logger.i("检查游戏包名为空");
            return;
        }
        if (Utils.isInstalled(this, packageName)) {
            isInstalledGame = true;
            btnStartGame.setText("启动游戏");
        } else {
            isInstalledGame = false;
            btnStartGame.setText("下载游戏");
        }
    }


}
