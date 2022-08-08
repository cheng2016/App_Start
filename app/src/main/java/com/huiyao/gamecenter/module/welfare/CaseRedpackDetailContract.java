package com.huiyao.gamecenter.module.welfare;

import com.huiyao.gamecenter.data.entity.HyCaseRedpackData;
import com.huiyao.gamecenter.data.entity.HyCaseRedpackTaskData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

/**
 * 福利页面
 */
public interface CaseRedpackDetailContract {

    interface View extends BaseView<Presenter> {
        void refreshUI(HyCaseRedpackData hyCaseRedpackData);
        void refreshTaskListUI(HyCaseRedpackTaskData hyCaseRedpackTaskData);
        void notifyReceiveLvTaskSucess(HyCaseRedpackTaskData.LevelTaskBean levelTaskBean);
        void notifyReceivePayTaskSucess(HyCaseRedpackTaskData.PayTaskBean payTaskBean);

    }

    interface Presenter extends BasePresenter {

    }
}
