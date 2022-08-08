package com.huiyao.gamecenter.module.user;

import android.content.Context;

import com.huiyao.gamecenter.data.entity.UserAccountStateInfoData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

public interface UserContract {
    interface View extends BaseView<Presenter> {
        void notifityAccountStateUI(UserAccountStateInfoData userAccountStateInfoData);

        void getUserInfoFail();
    }

    interface Presenter extends BasePresenter {
        void getUserInfo(Context context);

    }
}
