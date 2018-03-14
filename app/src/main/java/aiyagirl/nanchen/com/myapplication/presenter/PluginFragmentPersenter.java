package aiyagirl.nanchen.com.myapplication.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import aiyagirl.nanchen.com.myapplication.ui.contract.PluginFragmentContract;
import aiyagirl.nanchen.com.myapplication.utils.ThreadUtil;

/**
 * Created by renyukai on 2017/6/12.
 */

public class PluginFragmentPersenter extends PluginFragmentContract.Presenter {

    @Override
    public void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
              logouted(true,null);
            }

            @Override
            public void onError(int i, String s) {
                logouted(false,s);
            }

            @Override
            public void onProgress(int i, String s) {
//没用
            }
        });
    }

    private void logouted(final boolean islogoutSuccess, final String msg) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().logoutResult(islogoutSuccess,msg);
            }
        });
    }
}
