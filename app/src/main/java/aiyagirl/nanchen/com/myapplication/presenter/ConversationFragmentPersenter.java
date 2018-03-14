package aiyagirl.nanchen.com.myapplication.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import aiyagirl.nanchen.com.myapplication.ui.contract.ConversationFragmentContract;

/**
 * Created by renyukai on 2017/6/12.
 */

public class ConversationFragmentPersenter extends ConversationFragmentContract.Presenter {



    @Override
    public  Map<String, EMConversation>  initConversations(String currentUser) {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        return allConversations;
    }
}
