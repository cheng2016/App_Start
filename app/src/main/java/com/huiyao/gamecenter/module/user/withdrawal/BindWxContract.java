package com.huiyao.gamecenter.module.user.withdrawal;

import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

/**
 * 福利页面
 */
public interface BindWxContract {

    interface View extends BaseView<Presenter> {

        void notifyBindWxResult(int resultCode,String message);

    }

    interface Presenter extends BasePresenter {

    }
}
