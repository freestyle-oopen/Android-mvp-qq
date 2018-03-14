package aiyagirl.nanchen.com.myapplication.ui.contract;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.net.Response;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/12.
 */

public class RegistContract {
    public interface Modle extends BaseModle{
        Observable<Response<Boolean>> addUserToDB(User user);
        Observable<Response<Boolean>> delectUser(User user);
    }

    public interface View extends BaseView {
        void setDialogState(boolean isShow);
        void registSuccess(User user);

    }

   public  static abstract  class Presenter extends BasePresenter<View, Modle> {
       public abstract void regist(String name, String pwd);

   }
}
