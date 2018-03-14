package aiyagirl.nanchen.com.myapplication.presenter;

import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.rx.RxSubscriber;
import aiyagirl.nanchen.com.myapplication.ui.contract.AddContactContract;
import aiyagirl.nanchen.com.myapplication.ui.contract.RegistContract;
import aiyagirl.nanchen.com.myapplication.utils.Logger;
import aiyagirl.nanchen.com.myapplication.utils.ThreadUtil;

import static aiyagirl.nanchen.com.myapplication.ui.contract.AddContactContract.*;

/**
 * Created by Administrator on 2017/9/28.
 */

public class AddContactPresenter extends AddContactContract.Presenter {
    @Override
    public void search(String keyword) {
        String currentUser = EMClient.getInstance().getCurrentUser();
        getRxManager().add(getModle().searchUser(keyword,currentUser).subscribe(new RxSubscriber<List<User>>() {
               @Override
               public void onSucceed(List<User> users) {
                  getView().searchSuccess(users);
               }

            @Override
            public void onFailed(Throwable e) {
               getView().showMessage("查询失败："+e.toString());
            }
        }));
    }

    @Override
    public void addFriend(final String addName, final String s) {
        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(addName, s);
                    //添加成功
                    addFriendResult(true,null,addName);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    //添加失败
                    addFriendResult(false,e.toString(),addName);
                }
            }
        });

    }

    private void addFriendResult(final boolean isSuccess,final String msg,final String addName) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().addFriendResult(isSuccess,msg,addName);
            }
        });
    }
}
