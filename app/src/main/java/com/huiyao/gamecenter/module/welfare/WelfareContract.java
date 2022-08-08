package com.huiyao.gamecenter.module.welfare;

import com.huiyao.gamecenter.data.entity.BonusListData;
import com.huiyao.gamecenter.data.entity.GameGiftBagData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

/**
 * 福利页面
 */
public interface WelfareContract {
    interface View extends BaseView<Presenter> {
        void loadBonusListData(BonusListData data);

        void loadBonusListDataFail();

        void loadGameGiftBagData(GameGiftBagData data);
        void loadGameGiftBagDatafail();

        void receiveGiftSuccess(int parentIndex,int childIndex);
    }

    interface Presenter extends BasePresenter {
        void requestBonusListData(int page);
        void requestGameGiftListData(int page);

        void receiveGift(int gift_id,int parentIndex,int childIndex);
    }
}
