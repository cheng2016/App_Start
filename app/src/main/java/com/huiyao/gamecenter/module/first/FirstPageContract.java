package com.huiyao.gamecenter.module.first;

import com.huiyao.gamecenter.data.entity.FirstData;
import com.huiyao.gamecenter.data.entity.FirstGameListData;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

public interface FirstPageContract {
    interface View extends BaseView<Presenter> {
        void refreshData(FirstData data, FirstGameListData gameListData);

        void refreshDataFail();

        void loadGameListMoreData(FirstGameListData gameListData);

        void loadGameListMoreDataFail();
    }

    interface Presenter extends BasePresenter {
        void getFirstData(int page);

        void getGameListMoreData(int page);
    }
}
