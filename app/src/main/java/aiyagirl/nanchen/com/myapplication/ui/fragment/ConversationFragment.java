package aiyagirl.nanchen.com.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.modle.ConversationFragmentModle;
import aiyagirl.nanchen.com.myapplication.presenter.ConversationFragmentPersenter;
import aiyagirl.nanchen.com.myapplication.ui.activity.ChatActivity;
import aiyagirl.nanchen.com.myapplication.ui.adapter.ConversationAdapter;
import aiyagirl.nanchen.com.myapplication.ui.contract.ConversationFragmentContract;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by renyukai on 2017/6/7.
 */

public class ConversationFragment extends BaseFragment<ConversationFragmentPersenter, ConversationFragmentModle> implements ConversationFragmentContract.View {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.conversation_list)
    RecyclerView conversationList;

    private Map<String, EMConversation> conversationMap;
    private ConversationAdapter adapter;

    @Override
    public int layoutResId() {
        return R.layout.fragment_conversation;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        title.setText("消息");
        conversationMap=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        //注册EventBus  接收新消息
        EventBus.getDefault().register(this);
        conversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ConversationAdapter(conversationMap);
        conversationList.setAdapter(adapter);
        adapter.setOnConversationItemClickListener(new ConversationAdapter.OnConversationItemClickListener() {
            @Override
            public void onItemClick(String username) {
                Intent intent =new Intent(ConversationFragment.this.getContext(), ChatActivity.class);
                intent.putExtra("chat_name",username);
                startActivity(intent);
            }
        });
        initConversation();

    }

    private void initConversation() {
        Map<String, EMConversation> map = getmPresenter().initConversations(EMClient.getInstance().getCurrentUser());

        conversationMap.clear();
        conversationMap.putAll(map);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage emMessage) {
        initConversation();
    }

    @Override
    public void onResume() {
        super.onResume();
        initConversation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
