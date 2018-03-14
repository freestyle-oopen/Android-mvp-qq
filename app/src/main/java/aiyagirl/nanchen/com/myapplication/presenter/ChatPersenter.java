package aiyagirl.nanchen.com.myapplication.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.ui.contract.ChatContract;
import aiyagirl.nanchen.com.myapplication.utils.ThreadUtil;

/**
 * Created by renyukai on 2017/6/12.
 */

public class ChatPersenter extends ChatContract.Presenter {

    @Override
    public void sendMessage(final EMMessage emMessage) {
        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {//发送成功
                sendMessageResult(true,emMessage,null);
            }

            @Override
            public void onError(int i, String s) {//发送失败
                sendMessageResult(false,emMessage,"错误码："+i+"，错误信息："+s);
            }

            @Override
            public void onProgress(int i, String s) {//正在发送、没用
            }
        });
        EMClient.getInstance().chatManager().sendMessage(emMessage);
    }

    private void sendMessageResult(final boolean isSuccess, final EMMessage emMessage, final String error) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().sendMessageResult(isSuccess,emMessage,error);
            }
        });
    }

    @Override
    public List<EMMessage> loadConversation(String chatName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatName);
        if (conversation==null){
            //没有聊过天
             return null;
        }
            //以前聊过天
            //List<EMMessage> allMessages = conversation.getAllMessages();
            EMMessage lastMessage = conversation.getLastMessage();
            List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), 19);
            emMessages.add(lastMessage);
          //设置消息为已读
          conversation.markAllMessagesAsRead();
        return emMessages;
    }
}
