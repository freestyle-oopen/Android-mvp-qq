package aiyagirl.nanchen.com.myapplication.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.utils.StringUtils;

/**
 * Created by Administrator on 2017/10/27.
 */

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.AddFriendHolder> {
    private List<String> contactsList;
    private List<User> searchList;
    private OnAddButClickListener listener;
    public SearchFriendAdapter(List<String> contactsList,List<User> searchList){
        this.contactsList=contactsList;
        this.searchList = searchList;
    }

    @Override
    public AddFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        AddFriendHolder contactsHolder = new AddFriendHolder(view);
        return contactsHolder;
    }

    @Override
    public void onBindViewHolder(AddFriendHolder holder, final int position) {
        User user = searchList.get(position);
        boolean contains = contactsList.contains(user.getName());
        holder.add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(searchList.get(position).getName());
                }
            }
        });
        if(contains){
            holder.add_friend.setTextColor(Color.parseColor("#a8a8a8"));
            holder.add_friend.setClickable(false);
            holder.add_friend.setText("已是好友");
        }else {
            holder.add_friend.setTextColor(Color.parseColor("#000000"));
            holder.add_friend.setClickable(true);
            holder.add_friend.setText("添加");
        }
        holder.contact_name.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class AddFriendHolder extends RecyclerView.ViewHolder{
         TextView contact_name;
        TextView add_friend;
        public AddFriendHolder(View itemView) {
            super(itemView);
            contact_name= (TextView) itemView.findViewById(R.id.contact_name);
            add_friend=(TextView) itemView.findViewById(R.id.add_friend);
            add_friend.setVisibility(View.VISIBLE);
        }
    }

    public void setOnAddButClickListener(OnAddButClickListener listener){
        this.listener=listener;
    }

    public interface OnAddButClickListener{
        void onClick(String addName);
    }
}
