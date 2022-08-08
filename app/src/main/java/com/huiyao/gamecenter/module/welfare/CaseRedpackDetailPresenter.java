package com.huiyao.gamecenter.module.welfare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.huiyao.gamecenter.common.AppEventType;
import com.huiyao.gamecenter.common.HY_Constants;
import com.huiyao.gamecenter.data.entity.BaseResponseMode;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackData;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackTaskData;
import com.huiyao.gamecenter.data.entity.UserInfoEntity;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.data.source.remote.HyStringConsumer;
import com.huiyao.gamecenter.data.source.remote.RequestParameterUtils;
import com.huiyao.gamecenter.data.source.remote.ThrowableConsumer;
import com.huiyao.gamecenter.module.login.LoginUtils;
import com.huiyao.gamecenter.util.GsonUtil;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CaseRedpackDetailPresenter implements CaseRedpackDetailContract.Presenter {

    private CaseRedpackDetailContract.View view;
    private Context context;

    public CaseRedpackDetailPresenter(CaseRedpackDetailContract.View view,Context context){
        this.view = view;
        this.context = context;
        view.setPresenter(this);
    }




    /**
     * 获取 精美手游页面 游戏详情信息及账号下面角色区服相关信息
     * @param activityId  游戏活动id
     */
    @SuppressLint("CheckResult")
    public void getCaseRedpackDetailData(String activityId,String recommend_id) {
        if(TextUtils.isEmpty(activityId)){
            Logger.i("请求现金红包详情activityiId为空");
            return;
        }
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = LoginUtils.getInstance().getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("用户不为空");
        }
        params.put("activity_id",activityId);
        params.put("recommend_id",recommend_id+"");
        params.put("type", AppEventType.FROM_CASE_REDPACK+"");
        params.put("app_id", HY_Constants.APPID);

        httpApi.getCashRedpackDetailData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("现金红包详情页返回数据>>>" + completeInfo);
                        if (status == 0) {
                            BaseResponseMode<HyCaseRedpackData> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<HyCaseRedpackData>>(){});
                            HyCaseRedpackData hyCaseRedpackData = baseResponseMode.getData();
                            view.refreshUI(hyCaseRedpackData);

                        } else {
                            Logger.i("现金红包详情页返回数据失败>>>" + message);
                        }

                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                    }
                });
    }


    /**
     * 获取现金红包 任务列表数据 包含 等级任务 充值任务(一个接口同时返回)
     * @param zoneId 区服id
     * @param uid_role_id 角色id 服务端返回的又一个角色id标识
     */
    @SuppressLint("CheckResult")
    public void getCaseRedpackTaskListData(String activity_id,String uid_role_id, String zoneId,String recommend_id){
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = LoginUtils.getInstance().getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("获取现金红包任务列表用户不为空");
        }
        params.put("uid_role_id",uid_role_id);
        params.put("activity_id",activity_id);
        params.put("zone_id",zoneId);
        params.put("app_id", HY_Constants.APPID);
        params.put("recommend_id",recommend_id+"");
        params.put("type", AppEventType.FROM_CASE_REDPACK+"");

        httpApi.getCashRedpackTaskListData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("现金红包任务列表返回数据>>>" + completeInfo);
                        if (status == 0) {
                            BaseResponseMode<HyCaseRedpackTaskData> baseResponseMode = GsonUtil.stringToBean(completeInfo, new TypeToken<BaseResponseMode<HyCaseRedpackTaskData>>(){});
                            HyCaseRedpackTaskData hyCaseRedpackTaskData = baseResponseMode.getData();
                            view.refreshTaskListUI(hyCaseRedpackTaskData);

                        } else {
                            Logger.i("现金红包任务列表返回数据失败>>>" + message);
                        }

                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                    }
                });
    }


    /**
     * 领取等级任务红包
     * @param
     */
    @SuppressLint("CheckResult")
    public void receiveLeveTaskRedpack(HyCaseRedpackTaskData.LevelTaskBean levelTaskBean,HyCaseRedpackData.RoleDataBean currentRoleBean){
        if(levelTaskBean==null || currentRoleBean==null){
            Logger.i("等级任务数据或者角色信息为空暂停请求数据");
            return;
        }

        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = LoginUtils.getInstance().getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("获取等级现金红包任务列表用户不为空");
        }
        params.put("app_id", HY_Constants.APPID);
        params.put("id",levelTaskBean.getId());
        params.put("activity_id",levelTaskBean.getActivity_id());
        /*params.put("role_id",currentRoleBean.getRole_id());
        params.put("role_name",currentRoleBean.getRole_name());*/
        params.put("zone_id",currentRoleBean.getZone_id());
        /*params.put("zone_name",currentRoleBean.getZone_name());
        params.put("uid_role_id",currentRoleBean.getUid_role_id());*/


        httpApi.getRedpaackRecive(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("领取等级红包返回数据>>>" + completeInfo);
                        if (status == 0) {
                            levelTaskBean.setStatus(3);
                            view.notifyReceiveLvTaskSucess(levelTaskBean);

                        } else {
                            ToastUtils.showShort(message);
                            Logger.i("等级红包领取失败>>>" + message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        Logger.i("等级红包领取异常>>>" + message);
                    }
                });
    }

    /**
     * 领取累充任务红包
     * @param
     */
    @SuppressLint("CheckResult")
    public void receivePayTaskRedpack(HyCaseRedpackTaskData.PayTaskBean payTaskBean,HyCaseRedpackData.RoleDataBean currentRoleBean){
        if(payTaskBean==null || currentRoleBean==null){
            Logger.i("累充任务数据或者角色信息为空暂停请求数据");
            return;
        }
        HttpApi httpApi = HttpFactory.getHttpApiService();
        Map<String, String> params = RequestParameterUtils.getRequestBaseParameter(context);
        UserInfoEntity userInfoEntity = LoginUtils.getInstance().getUser();
        if (userInfoEntity != null) {
            params.put("guid", userInfoEntity.getGuid());
            params.put("token", userInfoEntity.getToken());
            Logger.d("获取累充现金红包任务列表用户不为空");
        }
        params.put("app_id", HY_Constants.APPID);
        params.put("id",payTaskBean.getId());
        params.put("activity_id",payTaskBean.getActivity_id());
        /*params.put("role_id",currentRoleBean.getRole_id());
        params.put("role_name",currentRoleBean.getRole_name());*/
        params.put("zone_id",currentRoleBean.getZone_id());
        /*params.put("zone_name",currentRoleBean.getZone_name());*/
        /*params.put("uid_role_id",currentRoleBean.getUid_role_id());*/

        httpApi.getRedpaackRecive(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HyStringConsumer() {
                    @Override
                    protected void onAcceptComplete(int status, String message, String completeInfo) {
                        Logger.i("累充红包领取返回数据>>>" + completeInfo);
                        if (status == 0) {
                            payTaskBean.setStatus(3);
                            view.notifyReceivePayTaskSucess(payTaskBean);
                        } else {
                            ToastUtils.showShort(message);
                            Logger.i("累充红包任务领取失败>>>" + message);
                        }
                    }
                }, new ThrowableConsumer() {
                    @Override
                    protected void onExceptionInfo(int code, String message) {
                        Logger.i("累充红包任务领取异常>>>" + message);
                    }
                });
    }


}
