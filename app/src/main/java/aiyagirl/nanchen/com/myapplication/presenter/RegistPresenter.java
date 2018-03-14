package aiyagirl.nanchen.com.myapplication.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.rx.RxSubscriber;
import aiyagirl.nanchen.com.myapplication.ui.contract.RegistContract;
import aiyagirl.nanchen.com.myapplication.utils.Logger;
import aiyagirl.nanchen.com.myapplication.utils.ThreadUtil;

/**
 * Created by Administrator on 2017/9/28.
 */

public class RegistPresenter extends RegistContract.Presenter {
    private static final String TAG = "RegistPresenter";

    @Override
    public void regist(final String name, final String pwd) {
        final User user = new User(name, pwd);
       getRxManager().add(getModle().addUserToDB(user).subscribe(new RxSubscriber<Boolean>() {
            @Override
            public void onStart() {
                getView().setDialogState(true);
                super.onStart();
            }

            @Override
            public void onSucceed(Boolean aBoolean) {
                //注册环信
                ThreadUtil.runOnSubThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSuccess=false;
                        try {
                            EMClient.getInstance().createAccount(name, pwd);
                            //注册成功
                            isSuccess=true;
                        } catch (HyphenateException e) {
                            Logger.i(TAG,"环信注册失败："+e.toString());
                            e.printStackTrace();
                        }
                        final boolean finalisSus=isSuccess;
                        ThreadUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(finalisSus){
                                   //注册成功
                                    getView().setDialogState(false);
                                    getView().registSuccess(user);
                                }else{
                                   //注册失败，回滚
                                    delectUser(user);
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onFinal() {
                getView().setDialogState(false);
                super.onFinal();
            }
        }));
    }


    public void delectUser(User user) {
        getRxManager().add(getModle().delectUser(user).subscribe(new RxSubscriber<Boolean>() {
            @Override
            public void onSucceed(Boolean aBoolean) {
            getView().showMessage("注册失败！");
            getView().setDialogState(false);
            }
            @Override
            public void onFinal() {
                getView().setDialogState(false);
                super.onFinal();
            }
        }));
    }

}
