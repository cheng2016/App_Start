package com.huiyao.gamecenter.module;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/7 10:27
 * @描述:
 */
public interface BaseContract {
    //----------------------------copy-------------------------
/*

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

    */
    // ---------------------------copy end--------------------

    interface BasePresenter {
        void clear();
    }

    interface BaseView<T> {
        void setPresenter(T presenter);
    }


/*

    BaseContract DEFAULT = new BaseContract() {
        @Override
        public void run() {
            Runtime.getRuntime().gc();
        }
    };

    void run();

    */
}
