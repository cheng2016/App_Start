package com.huiyao.gamecenter.module.first.sign;

import com.huiyao.gamecenter.data.entity.SignInData;
import com.huiyao.gamecenter.module.BaseContract;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/14 10:35
 * @描述:
 */
public interface SignInContract {
    interface View extends BaseContract.BaseView<Presenter>{
        void getSignInDataSuccess(SignInData data);
        void getSignInDataFail(String erroMsg);

        void signSuccess(String message);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void getSignInData();
    }
}
