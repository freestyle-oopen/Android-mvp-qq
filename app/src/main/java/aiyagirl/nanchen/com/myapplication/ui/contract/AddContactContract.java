package aiyagirl.nanchen.com.myapplication.ui.contract;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.net.Response;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/12.
 */

public class AddContactContract {
    public interface Modle extends BaseModle{
        Observable<Response<List<User>>> searchUser(String keyword,String currentUser);
    }

    public interface View extends BaseView {
       void searchSuccess(List<User> users);

        void addFriendResult(boolean isSuccess, String msg, String addName);
    }

   public static  abstract  class Presenter extends BasePresenter<View, Modle> {
       public abstract void search(String trim);

       public abstract void addFriend(String addName, String s);
   }
}
