package com.huiyao.gamecenter.module.vip.my;



import com.huiyao.gamecenter.module.BaseContract;


/**
 * @Created by: chengzj
 * @创建时间: 2022/8/2 10:45
 * @描述:
 */
public interface MyVipContract  {

    interface View extends BaseContract.BaseView<Presenter> {

    }

    interface Presenter extends BaseContract.BasePresenter {

    }


    public static class MyPresenter  implements Presenter{
        View view;

        public MyPresenter(View view) {
            this.view = view;
            view.setPresenter(this);
        }

        @Override
        public void clear() {

        }

    }
}
