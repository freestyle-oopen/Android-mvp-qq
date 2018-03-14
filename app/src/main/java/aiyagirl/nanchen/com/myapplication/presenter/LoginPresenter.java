package aiyagirl.nanchen.com.myapplication.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.ui.contract.LoginContract;
import aiyagirl.nanchen.com.myapplication.utils.ThreadUtil;

/**
 * Created by Administrator on 2017/9/28.
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void login(final String name, final String pwd) {
        getView().setDialogState(true);
        EMClient.getInstance().login(name, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                loginSuccess(true,new User(name,pwd),null);
            }

            @Override
            public void onError(int i, String s) {
                loginSuccess(false,null,s);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    private void loginSuccess(final boolean isSuccess, final User user, final String msg) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().setDialogState(false);
                if(isSuccess){
                 getView().loginSuccess(user);
                }else {
                getView().showMessage("登录失败："+msg);
                }
            }
        });

    }
}
