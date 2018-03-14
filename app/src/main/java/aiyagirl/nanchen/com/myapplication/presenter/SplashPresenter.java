package aiyagirl.nanchen.com.myapplication.presenter;

import com.hyphenate.chat.EMClient;

import aiyagirl.nanchen.com.myapplication.ui.contract.SplashContract;

/**
 * Created by Administrator on 2017/9/28.
 */

public class SplashPresenter extends SplashContract.Presenter {
    @Override
    public void checkLogin() {
        if(EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()){
            getView().checkLogined(true);
        }else {
            getView().checkLogined(false);
        }
    }
}
