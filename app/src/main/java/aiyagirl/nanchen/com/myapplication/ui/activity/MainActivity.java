package aiyagirl.nanchen.com.myapplication.ui.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.entity.ContactsEvent;
import aiyagirl.nanchen.com.myapplication.modle.MainModle;
import aiyagirl.nanchen.com.myapplication.presenter.MainPersenter;
import aiyagirl.nanchen.com.myapplication.ui.contract.MainContract;
import aiyagirl.nanchen.com.myapplication.ui.fragment.ContactsFragment;
import aiyagirl.nanchen.com.myapplication.ui.fragment.ConversationFragment;
import aiyagirl.nanchen.com.myapplication.ui.fragment.PluginFragment;
import aiyagirl.nanchen.com.myapplication.utils.Logger;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPersenter, MainModle> implements MainContract.View {
    private static final String TAG ="MainActivity";
    @BindView(R.id.bottom_tab)
    BottomNavigationBar tab;
    @BindView(R.id.content)
    FrameLayout content;
    private ConversationFragment conversationFragment;
    private ContactsFragment contactsFragment;
    private PluginFragment pluginFragment;
    private EMContactListener emContactListener;
    private TextBadgeItem badgeItem;

    @Override
    public int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        initTab();
        initFragment();
        initContactsListener();
    }


    private void initFragment() {
        conversationFragment = new ConversationFragment();
        contactsFragment = new ContactsFragment();
        pluginFragment = new PluginFragment();
        showFragment(0);
    }

    private void initTab() {
        tab.clearAll();
        badgeItem = new TextBadgeItem();
        badgeItem.setBackgroundColor(Color.RED)
                .setTextColor(Color.WHITE)
                .setGravity(Gravity.RIGHT)
                .setHideOnSelect(false)
                .setAnimationDuration(500)
                .hide();
        tab.setActiveColor("#058fff")
                .setInActiveColor("#ABADBB")
                .addItem(new BottomNavigationItem(R.mipmap.conversation_selected_2, "消息").setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.contact_selected_2, "联系人"))
                .addItem(new BottomNavigationItem(R.mipmap.plugin_selected_2, "动态"))
                .initialise();
        tab.setFirstSelectedPosition(0);
        tab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void showFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                if (conversationFragment == null) {
                    conversationFragment = new ConversationFragment();
                }
                fragmentTransaction.hide(contactsFragment);
                fragmentTransaction.hide(pluginFragment);
                if (!conversationFragment.isAdded()) {
                    fragmentTransaction.add(R.id.content, conversationFragment, index + "");
                }
                fragmentTransaction.show(conversationFragment);
                break;
            case 1:
                if (contactsFragment == null) {
                    contactsFragment = new ContactsFragment();
                }
                fragmentTransaction.hide(conversationFragment);
                fragmentTransaction.hide(pluginFragment);
                if (!contactsFragment.isAdded()) {
                    fragmentTransaction.add(R.id.content, contactsFragment, index + "");
                }
                fragmentTransaction.show(contactsFragment);
                break;
            case 2:
                if (pluginFragment == null) {
                    pluginFragment = new PluginFragment();
                }
                fragmentTransaction.hide(conversationFragment);
                fragmentTransaction.hide(contactsFragment);
                if (!pluginFragment.isAdded()) {
                    fragmentTransaction.add(R.id.content, pluginFragment, index + "");
                }
                fragmentTransaction.show(pluginFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    //添加通讯录监听器
    private void initContactsListener() {
        emContactListener = new EMContactListener() {
            @Override
            public void onContactAdded(String s) {//增加联系人时回调此方法
                //运行在子线程

                EventBus.getDefault().post(new ContactsEvent(s, true));

                System.out.println("onContactAdded===========================" + s);
            }

            @Override
            public void onContactDeleted(String s) {//被删除时回调此方法
                EventBus.getDefault().post(new ContactsEvent(s, false));
                System.out.println("onContactDeleted===========================" + s);
            }

            @Override
            public void onContactInvited(String s, String s1) {//收到好友邀请
                try {
                    //自动同意好友请求
                    EMClient.getInstance().contactManager().acceptInvitation(s);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                System.out.println("onContactInvited===========================" + s + "----" + s1);
            }

            @Override
            public void onFriendRequestAccepted(String s) {//好友请求被同意
                System.out.println("onFriendRequestAccepted===========================" + s);
            }

            @Override
            public void onFriendRequestDeclined(String s) {//好友请求被拒绝
                System.out.println("onFriendRequestDeclined===========================" + s);
            }
        };
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
        //注册EventBus  接收新消息
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBadgeView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage emMessage){
        setBadgeView();
    }

    private void setBadgeView() {
        int count = EMClient.getInstance().chatManager().getUnreadMessageCount();
        Logger.i(TAG,"未读消息数："+count);
        if(count>99){
            badgeItem.setText("99+");
            badgeItem.show();
            badgeItem.show();
        }else if(count>0){
            badgeItem.setText(count+"");
            badgeItem.show();
            badgeItem.show();
        }else{
            badgeItem.hide();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        conversationFragment = null;
        contactsFragment = null;
        pluginFragment = null;
        EventBus.getDefault().unregister(this);
        EMClient.getInstance().contactManager().removeContactListener(emContactListener);
    }
}
