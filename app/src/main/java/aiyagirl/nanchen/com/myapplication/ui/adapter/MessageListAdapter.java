package aiyagirl.nanchen.com.myapplication.ui.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;
import java.util.List;

import aiyagirl.nanchen.com.myapplication.R;

/**
 * Created by Administrator on 2017/11/2.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageHolder> {
    List<EMMessage> oldMessages;

    public MessageListAdapter(List<EMMessage> oldMessages) {
        this.oldMessages = oldMessages;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        if (i == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_left, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_right, viewGroup, false);
        }
        MessageHolder messageHolder = new MessageHolder(view);
        return messageHolder;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int i) {
        EMMessage emMessage = oldMessages.get(i);
        long msgTime = emMessage.getMsgTime();
        if (i == 0) {
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText(DateUtils.getTimestampString(new Date(msgTime)));
        } else {
            EMMessage preMsg = oldMessages.get(i - 1);
            long preTime = preMsg.getMsgTime();
            if (DateUtils.isCloseEnough(msgTime, preTime)) {
                holder.time.setVisibility(View.GONE);
            } else {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setText(DateUtils.getTimestampString(new Date(msgTime)));
            }
        }
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            EMTextMessageBody textMessageBody = (EMTextMessageBody) body;
            holder.messageBody.setText(textMessageBody.getMessage());
        } else {
            holder.messageBody.setText("非文本类型：" + body.toString());
        }

        if (getItemViewType(i) == 1) {//发送的消息
            EMMessage.Status status = emMessage.status();
            switch (status) {
                case INPROGRESS:
                    holder.sendStatus.setVisibility(View.VISIBLE);
                    holder.sendStatus.setImageResource(R.drawable.sendmsg_ing);
                    AnimationDrawable drawable = (AnimationDrawable) holder.sendStatus.getDrawable();//帧动画启动
                    if (drawable.isRunning()) {
                        drawable.stop();
                    }
                    drawable.start();
                    break;
                case SUCCESS:
                    holder.sendStatus.setVisibility(View.GONE);
                    break;
                case FAIL:
                    holder.sendStatus.setVisibility(View.VISIBLE);
                    holder.sendStatus.setImageResource(R.mipmap.msg_error);
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {

        return oldMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage emMessage = oldMessages.get(position);
        return emMessage.direct() == EMMessage.Direct.RECEIVE ? 0 : 1;
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView messageBody;
        ImageView sendStatus;

        public MessageHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            messageBody = (TextView) itemView.findViewById(R.id.message_body);
            sendStatus = (ImageView) itemView.findViewById(R.id.send_status);
        }
    }
}
