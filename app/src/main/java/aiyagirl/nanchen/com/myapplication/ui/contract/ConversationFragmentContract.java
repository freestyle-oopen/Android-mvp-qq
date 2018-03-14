package aiyagirl.nanchen.com.myapplication.ui.contract;

import com.hyphenate.chat.EMConversation;

import java.util.List;
import java.util.Map;

import aiyagirl.nanchen.com.myapplication.entity.Wether;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ConversationFragmentContract {
    public interface Modle extends BaseModle{
       Observable<Wether>  getWhear();
    }

    public interface View extends BaseView {

    }

   public static  abstract  class Presenter extends BasePresenter<View, Modle> {
       public abstract Map<String, EMConversation> initConversations(String currentUser);
   }
}
