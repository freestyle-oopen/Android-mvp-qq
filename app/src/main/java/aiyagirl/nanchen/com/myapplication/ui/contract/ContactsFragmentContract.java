package aiyagirl.nanchen.com.myapplication.ui.contract;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.entity.Wether;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ContactsFragmentContract {
    public interface Modle extends BaseModle{
    }

    public interface View extends BaseView {
        void setDialogState(boolean isShow);

        void showSuccess(List<String> contacts);

        void delectContactResult(boolean isSuccess, String msg);
    }

   public static  abstract  class Presenter extends BasePresenter<View, Modle> {

       public abstract void getContactsList(String currentUser);

       public abstract void delectContact(String name);
   }
}
