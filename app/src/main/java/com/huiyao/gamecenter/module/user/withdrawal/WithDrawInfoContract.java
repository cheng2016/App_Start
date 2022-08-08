package com.huiyao.gamecenter.module.user.withdrawal;

import android.content.Context;

import com.huiyao.gamecenter.data.entity.WithDrawInfoData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

/**
 * 福利页面
 */
public interface WithDrawInfoContract {

    interface View extends BaseView<Presenter> {

        void notifiRefreshUI(WithDrawInfoData withDrawInfoData);

        void getWithDrawInfoFail(String msg);
    }

    interface Presenter extends BasePresenter {
        void getWithDrawInfo(Context context);
        void withdrawal(String amount);
    }
    
}
