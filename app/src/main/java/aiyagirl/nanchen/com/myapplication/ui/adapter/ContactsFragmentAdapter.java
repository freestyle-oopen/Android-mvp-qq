package aiyagirl.nanchen.com.myapplication.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.util.HanziToPinyin;

import java.util.List;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.utils.StringUtils;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ContactsFragmentAdapter extends RecyclerView.Adapter<ContactsFragmentAdapter.ContactsHolder> {
    private List<String> contactsList;
    private OnItemClickListener listener;
    public ContactsFragmentAdapter(List<String> contactsList){
        this.contactsList=contactsList;
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        ContactsHolder contactsHolder = new ContactsHolder(view);
        return contactsHolder;
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, final int position) {
        String name = contactsList.get(position);
        String first_pinyin = StringUtils.getFirstIndex(name);
        if(position==0){
            holder.first_index.setVisibility(View.VISIBLE);
            holder.first_index.setText(first_pinyin);
        }else {
            String prename = contactsList.get(position - 1);
            String preIndex = StringUtils.getFirstIndex(prename);
            if(preIndex.equals(first_pinyin)){
                holder.first_index.setVisibility(View.GONE);
            }else {
                holder.first_index.setVisibility(View.VISIBLE);
                holder.first_index.setText(first_pinyin);
            }
        }
        holder.contact_name.setText(name);
        holder.item_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(v,position);
                }
            }
        });
        holder.item_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener!=null){
                    listener.onLongClick(v,position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ContactsHolder extends RecyclerView.ViewHolder{
         TextView first_index;
         TextView contact_name;
        View item_content;
        public ContactsHolder(View itemView) {
            super(itemView);
            first_index= (TextView) itemView.findViewById(R.id.first_pingyin);
            contact_name= (TextView) itemView.findViewById(R.id.contact_name);
            item_content=itemView.findViewById(R.id.item_content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
