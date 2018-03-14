package aiyagirl.nanchen.com.myapplication.ui.activity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.modle.ChatModle;
import aiyagirl.nanchen.com.myapplication.presenter.ChatPersenter;
import aiyagirl.nanchen.com.myapplication.ui.adapter.MessageListAdapter;
import aiyagirl.nanchen.com.myapplication.ui.contract.ChatContract;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity<ChatPersenter, ChatModle> implements ChatContract.View, TextView.OnEditorActionListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.message_content)
    RecyclerView messageList;
    @BindView(R.id.send_message)
    EditText sendMessage;
    @BindView(R.id.send_button)
    Button sendButton;
    private String chatName;
    List<EMMessage> oldMessages;
    private MessageListAdapter adapter;

    @Override
    public int layoutResId() {
        return R.layout.activity_chat;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        title.setError("sdfasdf");

        chatName = getIntent().getStringExtra("chat_name");
        title.setText("与" + chatName + "聊天中...");
        back.setVisibility(View.VISIBLE);
        sendMessage.setOnEditorActionListener(this);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        oldMessages =new ArrayList<>();
        adapter = new MessageListAdapter(oldMessages);
        messageList.setAdapter(adapter);
        messageList.smoothScrollToPosition(oldMessages.size()==0?0:oldMessages.size()-1);
        sendMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()<=0){
                    sendButton.setEnabled(false);
                }else {
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        loadOldMessage();
        //注册EventBus  接收新消息
        EventBus.getDefault().register(this);
    }

    //初始化聊天记录,并且把未读消息设置成已读
    private void loadOldMessage() {
        oldMessages.clear();
        List<EMMessage> emMessages = getmPresenter().loadConversation(chatName);
        if(emMessages==null){
            return;
        }
        oldMessages.addAll(emMessages);
        adapter.notifyDataSetChanged();
        messageList.scrollToPosition(oldMessages.size()==0?0:oldMessages.size()-1);
    }

    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }

    @OnClick({R.id.back, R.id.send_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send_button:
                sendMessage();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_SEND){
            sendMessage();
        }
        return false;
    }

    //发送消息
    private void sendMessage() {
        String message = sendMessage.getText().toString().trim();
        if(TextUtils.isEmpty(message)){
            showMessage("请写上你要说的话");
            return;
        }
        EMMessage emMessage=EMMessage.createTxtSendMessage(message,chatName);
        emMessage.setStatus(EMMessage.Status.INPROGRESS);//设置这条消息正在发送
        oldMessages.add(emMessage);
        adapter.notifyDataSetChanged();
        messageList.smoothScrollToPosition(oldMessages.size()-1);
        sendMessage.getText().clear();
        //然后发送
        getmPresenter().sendMessage(emMessage);
    }


    //有新消息发来时
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage emMessage){
        String userName = emMessage.getUserName();
        if(chatName.equals(userName)){
            oldMessages.add(emMessage);
            adapter.notifyDataSetChanged();
            messageList.smoothScrollToPosition(oldMessages.size()-1);
            //设置消息为已读
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatName);
            conversation.markAllMessagesAsRead();
        }
    }
    //消息发送状态回调
    @Override
    public void sendMessageResult(boolean isSuccess, EMMessage emMessage, String error) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
