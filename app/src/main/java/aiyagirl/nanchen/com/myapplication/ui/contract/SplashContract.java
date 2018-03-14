package aiyagirl.nanchen.com.myapplication.ui.contract;

import aiyagirl.nanchen.com.myapplication.entity.Wether;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/12.
 */

public class SplashContract {
    public interface Modle extends BaseModle{

    }

    public interface View extends BaseView {
       void checkLogined(boolean isLogined);
    }

   public  static abstract  class Presenter extends BasePresenter<View, Modle> {
        public abstract void checkLogin();
    }
}
