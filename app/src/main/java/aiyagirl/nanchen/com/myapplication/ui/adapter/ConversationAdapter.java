package aiyagirl.nanchen.com.myapplication.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aiyagirl.nanchen.com.myapplication.R;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationHolder> {
    Map<String, EMConversation> conversationMap;
    private List<EMConversation> messageList;

    public ConversationAdapter(Map<String, EMConversation> conversationMap) {
        this.conversationMap = conversationMap;
        messageList=new ArrayList<>();
    }

    @Override
    public ConversationHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_conversation, viewGroup, false);
        ConversationHolder conversationHolder = new ConversationHolder(view);
        return conversationHolder;
    }

    @Override
    public void onBindViewHolder(ConversationHolder holder, final int i) {
        Collection<EMConversation> values = conversationMap.values();
        messageList.clear();
        messageList.addAll(values);
        Set<String> keys = conversationMap.keySet();
        final  String[] keyss = keys.toArray(new String[keys.size()]);
        holder.tvName.setText(keyss[i]);
        int unreadMsgCount = messageList.get(i).getUnreadMsgCount();
        if (unreadMsgCount>99){
            holder.tvUnread.setText("99+");
            holder.tvUnread.setVisibility(View.VISIBLE);
        }else if (unreadMsgCount>0){
            holder.tvUnread.setText(unreadMsgCount+"");
            holder.tvUnread.setVisibility(View.VISIBLE);
        }else {
            holder.tvUnread.setVisibility(View.GONE);
        }
        EMMessage lastMessage = messageList.get(i).getLastMessage();
        EMMessageBody body = lastMessage.getBody();
        if(body instanceof EMTextMessageBody){
            EMTextMessageBody textMessageBody= (EMTextMessageBody) body;
            holder.tvMsg.setText(textMessageBody.getMessage());
        }else {
            holder.tvMsg.setText("文件信息："+body.toString());
        }
        holder.tvTime.setText(com.hyphenate.util.DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
        holder.itemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onConversationItemClickListener!=null){
                    onConversationItemClickListener.onItemClick(keyss[i]);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return conversationMap.size();
    }

    class ConversationHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public RelativeLayout itemContent;
        public  TextView tvMsg;
        public TextView tvTime;
        public TextView tvUnread;

        public ConversationHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_username);
            itemContent = (RelativeLayout) itemView.findViewById(R.id.item_content);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvUnread = (TextView) itemView.findViewById(R.id.tv_unread);

        }
    }

    public interface OnConversationItemClickListener{
        void onItemClick(String username);
    }
    private OnConversationItemClickListener onConversationItemClickListener;
    public void setOnConversationItemClickListener(OnConversationItemClickListener onConversationItemClickListener){
        this.onConversationItemClickListener = onConversationItemClickListener;
    }
}
