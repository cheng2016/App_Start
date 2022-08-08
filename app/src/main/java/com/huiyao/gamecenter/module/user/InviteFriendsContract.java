package com.huiyao.gamecenter.module.user;

import android.content.Context;

import com.huiyao.gamecenter.data.entity.InviteFriendsData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

public interface InviteFriendsContract {
    interface View extends BaseView<Presenter> {
        void notifityRefreshUI(InviteFriendsData inviteFriendsData);
        void verifyInviterCodeResult(int status, String message);

    }

    interface Presenter extends BasePresenter {
        void getInviteData(Context context);
        void verifyInviterCode(Context context,String inviterCode);

    }
}
