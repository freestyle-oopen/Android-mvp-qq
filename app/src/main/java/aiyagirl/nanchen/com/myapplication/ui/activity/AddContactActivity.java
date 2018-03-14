package aiyagirl.nanchen.com.myapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.modle.AddContactModle;
import aiyagirl.nanchen.com.myapplication.presenter.AddContactPresenter;
import aiyagirl.nanchen.com.myapplication.ui.adapter.SearchFriendAdapter;
import aiyagirl.nanchen.com.myapplication.ui.contract.AddContactContract;
import aiyagirl.nanchen.com.myapplication.utils.DBUtil;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends BaseActivity<AddContactPresenter, AddContactModle> implements AddContactContract.View, TextView.OnEditorActionListener, SearchFriendAdapter.OnAddButClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search_name)
    EditText searchName;
    @BindView(R.id.search_button)
    ImageView searchButton;
    @BindView(R.id.search_list)
    RecyclerView searchList;
    @BindView(R.id.nodata)
    ImageView nodata;

    private List<User> users;
    private List<String> contacts;
    private SearchFriendAdapter adapter;
    private InputMethodManager inputMethodManager;

    @Override
    public int layoutResId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        searchList.setVisibility(View.GONE);
        nodata.setVisibility(View.VISIBLE);
        searchName.setOnEditorActionListener(this);
        back.setVisibility(View.VISIBLE);
        users = new ArrayList<>();
        contacts = new ArrayList<>();
        searchList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchFriendAdapter(contacts, users);
        adapter.setOnAddButClickListener(this);
        searchList.setAdapter(adapter);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }


    @OnClick({R.id.back, R.id.add, R.id.search_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.search_button:
                search();
                break;
        }
    }

    private void search() {
        String keyword = searchName.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            showMessage("请输入要查的信息");
        }
        getmPresenter().search(keyword);
        //影藏键盘
        if(inputMethodManager.isActive()){
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
            return true;
        }
        return false;
    }

    @Override
    public void searchSuccess(List<User> users) {
        if (users == null || users.size() <= 0) {
            searchList.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            showMessage("没有查到用户！");
            return;
        }
        searchList.setVisibility(View.VISIBLE);
        nodata.setVisibility(View.GONE);
        contacts.clear();
        this.users.clear();
        contacts.addAll(DBUtil.getContacts(EMClient.getInstance().getCurrentUser()));
        this.users.addAll(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addFriendResult(boolean isSuccess, String msg, String addName) {
        //添加好友请求成功
        if(isSuccess){
            showMessage("添加"+addName+"成功，等待好友验证。");
        }else {
            showMessage("添加"+addName+"失败："+msg);
        }

    }

    @Override
    public void onClick(String addName) {
        showMessage("添加好友：" + addName);
        getmPresenter().addFriend(addName,"想和你聊一聊。");
       // EMClient.getInstance().contactManager().addContact(toAddUsername, reason);
    }
}
