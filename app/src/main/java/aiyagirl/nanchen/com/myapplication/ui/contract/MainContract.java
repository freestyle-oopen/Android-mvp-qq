package aiyagirl.nanchen.com.myapplication.ui.contract;

import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;

public class MainContract {
    public interface Modle extends BaseModle {

    }

    public interface View extends BaseView {
    }

    public static abstract class Presenter extends BasePresenter<View, Modle> {
    }
}
