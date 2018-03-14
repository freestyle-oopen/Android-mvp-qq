package aiyagirl.nanchen.com.myapplication.ui.contract;

import com.hyphenate.chat.EMMessage;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ChatContract {
    public interface Modle extends BaseModle{

    }

    public interface View extends BaseView {
        void sendMessageResult(boolean isSuccess, EMMessage emMessage, String error);
    }

   public static abstract  class Presenter extends BasePresenter<View, Modle> {
       public abstract void sendMessage(EMMessage emMessage);

       public abstract List<EMMessage>  loadConversation(String chatName);
   }
}
