package com.huiyao.gamecenter.module.welfare;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huiyao.gamecenter.R;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackData;
import com.huiyao.gamecenter.module.welfare.adapter.GameZoneSelectorAdapter;
import com.huiyao.gamecenter.util.Logger;
import com.huiyao.gamecenter.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 精美手游页面 区服选择弹窗
 */
public class GameZoneSelectorDialog extends Dialog {
    @Bind(R.id.hy_gamezone_selector_lv)
    ListView hyGamezoneSelectorLv;
    private Activity mActivity;
    private GameZoneSelectorAdapter gameZoneSelectorAdapter;

    private List<HyCaseRedpackData.RoleDataBean> dataList = new ArrayList<>();
    private ZoneSelectorCallback zoneSelectorCallback;
    private HyCaseRedpackData.RoleDataBean currentSelectorData;

    public static GameZoneSelectorDialog gameZoneSelectorDialog;

    public GameZoneSelectorDialog(@NonNull Context context) {
        super(context, R.style.base_pop);
        this.mActivity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogWindow(this);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_zone_selector_layout);
        ButterKnife.bind(this);
        initView();
    }

    public static GameZoneSelectorDialog getInstance(Activity activity) {
        if (gameZoneSelectorDialog == null) {
            gameZoneSelectorDialog = new GameZoneSelectorDialog(activity);
        }
        return gameZoneSelectorDialog;
    }




    private void initView(){
        gameZoneSelectorAdapter = new GameZoneSelectorAdapter(mActivity);
        hyGamezoneSelectorLv.setAdapter(gameZoneSelectorAdapter);
        gameZoneSelectorAdapter.updateData(dataList);
        //gameZoneSelectorAdapter.notifyDataSetChanged();
        hyGamezoneSelectorLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                //获取数据实体对象传递
                if(zoneSelectorCallback!=null){
                    zoneSelectorCallback.selectorData(dataList.get(i));
                }
                dismiss();
            }
        });
    }






    /**
     * 判断弹窗是否显示
     *
     * @return
     */
    public void showDialog(ZoneSelectorCallback zoneSelectorCallback,List<HyCaseRedpackData.RoleDataBean> roleDataList) {
        if(roleDataList==null){
            Logger.i("区服选择弹窗 区服信息为空不展示!");
            return;
        }
        if(roleDataList.isEmpty()){
            ToastUtils.showShort(getContext(),"当前无可选择角色！");
            return;
        }
        Logger.i("HY", "打开区服选择弹窗");
        //this.tipsType = tipsType;
        dataList.clear();
        dataList.addAll(roleDataList);
        this.zoneSelectorCallback = zoneSelectorCallback;
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                gameZoneSelectorDialog = null;
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ButterKnife.unbind(this);
                gameZoneSelectorDialog = null;

            }
        });
        super.show();
    }


    public void setDialogWindow(final Dialog dialog) {
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 8);
        WindowManager.LayoutParams lp = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        Configuration cf = dialog.getContext().getResources().getConfiguration();

        lp.width = (int) (display.getWidth()*0.7);
        lp.height = (int) (display.getHeight() * 0.25);
        window.setGravity(Gravity.CENTER);
        //window.setWindowAnimations(R.style.HYGame_BottomDialogAnimation);
        window.setAttributes(lp);
    }






    interface ZoneSelectorCallback{
        void selectorData(HyCaseRedpackData.RoleDataBean roleDataBean);
    }



}
