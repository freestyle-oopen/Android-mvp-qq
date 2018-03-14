package aiyagirl.nanchen.com.myapplication.presenter;

import android.os.AsyncTask;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import aiyagirl.nanchen.com.myapplication.ui.contract.ContactsFragmentContract;
import aiyagirl.nanchen.com.myapplication.utils.DBUtil;
import aiyagirl.nanchen.com.myapplication.utils.ThreadUtil;

/**
 * Created by renyukai on 2017/6/12.
 */

public class ContactsFragmentPersenter extends ContactsFragmentContract.Presenter {

    @Override
    public void getContactsList(String currentUser) {
        //先从数据库缓存中找
        new DBAsyncTask().execute(currentUser);
    }

    @Override
    public void delectContact(final String name) {
        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(name);
                    delectContactResult(true,name);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    delectContactResult(false,e.toString());
                }
            }
        });
    }

    private void delectContactResult(final boolean isSuccess, final String msg) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().delectContactResult(isSuccess,msg);
            }
        });
    }

    public void getContactsListFromNet() {
        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                     final List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Collections.sort(allContactsFromServer, new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            return lhs.compareTo(rhs);
                        }
                    });
                    //缓存数据
                    DBUtil.saveContacts(EMClient.getInstance().getCurrentUser(),allContactsFromServer);
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().showSuccess(allContactsFromServer);
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().showMessage("同步失败："+e.toString());
                        }
                    });
                }
            }
        });
    }

    class DBAsyncTask extends AsyncTask<String ,Integer,List<String>>{
        @Override
        protected void onPreExecute() {
           getView().setDialogState(true);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> contacts = DBUtil.getContacts(params[0]);
            return contacts;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            getView().showSuccess(strings);
            getView().setDialogState(false);
            //设置到view中，然后偷偷的走网络
            getContactsListFromNet();
        }
    }
}
