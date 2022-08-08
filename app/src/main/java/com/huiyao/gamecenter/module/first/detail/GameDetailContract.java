package com.huiyao.gamecenter.module.first.detail;

import com.huiyao.gamecenter.data.entity.GameDetailBean;
import com.huiyao.gamecenter.module.BasePresenter;
import com.huiyao.gamecenter.module.BaseView;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/21 15:03
 * @描述:
 */
public class GameDetailContract {
    interface View extends BaseView<Presenter> {
        void loadGameDetailDataSuccess(GameDetailBean data);
        void loadGameDetailDataFail(String message);
    }

    interface Presenter extends BasePresenter {
        void getDetailData(int product_id);
        void getDetail(int product_id);
    }
}
