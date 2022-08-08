package com.huiyao.gamecenter.module.user.score;

import com.huiyao.gamecenter.data.entity.ScoreData;
import com.huiyao.gamecenter.module.BaseContract;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/19 11:16
 * @描述:
 */
public interface UserScoreContract {
    interface View extends BaseContract.BaseView<Presenter>{
        void getScoreDataSuccess(ScoreData data);

        void getScoreDataFail(String message);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void getScoreData(int isIncome);
    }
}
