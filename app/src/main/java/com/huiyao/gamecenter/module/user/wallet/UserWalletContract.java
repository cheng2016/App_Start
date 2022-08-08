package com.huiyao.gamecenter.module.user.wallet;

import com.huiyao.gamecenter.data.entity.WithdrawalLogData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/23 15:37
 * @描述:
 */
public class UserWalletContract {
    interface View extends BaseView<Presenter> {
        void getWithdrawalLogSuccess(WithdrawalLogData data);
        void getWithdrawalLogFail(String message);
    }

    interface Presenter extends BasePresenter {

        void getWithdrawalLog();
    }
}
