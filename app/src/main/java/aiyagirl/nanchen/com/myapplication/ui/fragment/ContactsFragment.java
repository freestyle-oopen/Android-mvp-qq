package aiyagirl.nanchen.com.myapplication.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.entity.ContactsEvent;
import aiyagirl.nanchen.com.myapplication.modle.ContactsFragmentModle;
import aiyagirl.nanchen.com.myapplication.presenter.ContactsFragmentPersenter;
import aiyagirl.nanchen.com.myapplication.ui.activity.AddContactActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.ChatActivity;
import aiyagirl.nanchen.com.myapplication.ui.adapter.ContactsFragmentAdapter;
import aiyagirl.nanchen.com.myapplication.ui.contract.ContactsFragmentContract;
import aiyagirl.nanchen.com.myapplication.utils.ActivityUtil;
import aiyagirl.nanchen.com.myapplication.utils.StringUtils;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import aiyagirl.nanchen.com.myapplication.view.Slidebar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/7.
 */

public class ContactsFragment extends BaseFragment<ContactsFragmentPersenter,ContactsFragmentModle> implements ContactsFragmentContract.View, SwipeRefreshLayout.OnRefreshListener, Slidebar.OnBarIndexChanged ,ContactsFragmentAdapter.OnItemClickListener{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.contacts_list)
    RecyclerView list;
    @BindView(R.id.index_image)
    TextView index_image;
    @BindView(R.id.slidebar)
    Slidebar slidebar;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.add)
    ImageView add;

    private List<String> contactsList;
    private ContactsFragmentAdapter adapter;

    @Override
    public int layoutResId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        title.setText("联系人");
        add.setVisibility(View.VISIBLE);
        contactsList=new ArrayList<>();
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ContactsFragmentAdapter(contactsList);
        adapter.setOnItemClickListener(this);
        list.setAdapter(adapter);
        refresh.setOnRefreshListener(this);
        slidebar.setOnBarIndexChanged(this);
        getmPresenter().getContactsList(EMClient.getInstance().getCurrentUser());
        EventBus.getDefault().register(this);
    }
    @Override
    public void setDialogState(boolean isShow) {
        if(isShow){
            showDialog(false);
        }else {
            dismissDialog();
        }
    }

    @Override
    public void showSuccess(List<String> contacts) {
        contactsList.clear();
        contactsList.addAll(contacts);
        adapter.notifyDataSetChanged();
        refresh.setRefreshing(false);
    }

    @Override
    public void delectContactResult(boolean isSuccess, String msg) {
        if(isSuccess){
            showMessage("已经删除好友："+msg);
        }else {
            showMessage("删除好友失败："+msg);
        }
    }

    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }

    @Override
    public void onRefresh() {
        getmPresenter().getContactsList(EMClient.getInstance().getCurrentUser());
    }
    @OnClick({R.id.add})
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.add:
                ActivityUtil.startActivity(this.getActivity(), AddContactActivity.class);
                break;
        }
    }
    @Override
    public void indexChanged(boolean isShow, String index) {
        if(isShow){
           index_image.setVisibility(View.VISIBLE);
           index_image.setText(index);
            //改变recycleview的位置
            for (int i=0;i<contactsList.size();i++){
                if(StringUtils.getFirstIndex(contactsList.get(i)).equals(index)){
                    list.smoothScrollToPosition(i);
                    return;
                }
            }
        }else {
            index_image.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ContactsEvent event){
        if(event.isAdded){
            showMessage(event.name+":添加成功");
        }else {
            showMessage(event.name+":删除成功");
        }
        getmPresenter().getContactsList(EMClient.getInstance().getCurrentUser());
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent =new Intent(this.getContext(), ChatActivity.class);
        intent.putExtra("chat_name",contactsList.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).setMessage("您确定要和"+contactsList.get(position)+"友尽吗？")
                .setNegativeButton("友尽", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getmPresenter().delectContact(contactsList.get(position));
                    }
                }).setPositiveButton("再续前缘", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissDialog();
                    }
                })
                .create();
        alertDialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
