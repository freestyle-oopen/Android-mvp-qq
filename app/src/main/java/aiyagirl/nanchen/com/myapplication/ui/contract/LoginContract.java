package aiyagirl.nanchen.com.myapplication.ui.contract;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/6/12.
 */

public class LoginContract {
    public interface Modle extends BaseModle{

    }

    public interface View extends BaseView {
        void setDialogState(boolean isShow);
        void loginSuccess(User user);
    }

   public static  abstract  class Presenter extends BasePresenter<View, Modle> {
       public abstract void login(String name, String pwd);
   }
}
