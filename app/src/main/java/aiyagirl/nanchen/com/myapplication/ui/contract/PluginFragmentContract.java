package aiyagirl.nanchen.com.myapplication.ui.contract;

import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/6/12.
 */

public class PluginFragmentContract {
    public interface Modle extends BaseModle {
    }

    public interface View extends BaseView {
        void logoutResult(boolean isSuccess, String msg);
    }

    public static  abstract  class Presenter extends BasePresenter<View, Modle> {
        public abstract void logout();
    }
}
