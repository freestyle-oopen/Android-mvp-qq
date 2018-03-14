package aiyagirl.nanchen.com.myapplication.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.modle.LoginModle;
import aiyagirl.nanchen.com.myapplication.presenter.LoginPresenter;
import aiyagirl.nanchen.com.myapplication.ui.contract.LoginContract;
import aiyagirl.nanchen.com.myapplication.utils.ActivityUtil;
import aiyagirl.nanchen.com.myapplication.utils.SharedPreferencesUtil;
import aiyagirl.nanchen.com.myapplication.utils.StringUtils;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/28.
 */

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModle> implements LoginContract.View, TextView.OnEditorActionListener {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_PERMISSION_WES = 1;
    @BindView(R.id.et_username)
    EditText mUserName;
    @BindView(R.id.et_pwd)
    EditText mPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    @Override
    public int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        mPwd.setOnEditorActionListener(this);
        User data = SharedPreferencesUtil.getInstance(this).getData(RegistActivity.USER_INFO, User.class);
        if(data !=null){
            mUserName.setText(data.getName());
            mPwd.setText(data.getPwd());
        }


    }

    @OnClick({R.id.btn_login, R.id.tv_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_regist:
                ActivityUtil.startActivityForResult(LoginActivity.this,RegistActivity.class,110);
                break;
        }
    }


    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            login();
            return true;
        }
        return false;
    }

    //登录
    private void login() {
        String name = mUserName.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();
        if (!StringUtils.checkUserName(name)) {
            showMessage("用户名不合法。3到20位，首字母必须是字母");
            return;
        }
        if (!StringUtils.checkPwd(pwd)) {
            showMessage("密码不合法。3到20位的数字");
            return;
        }
        //检查权限
        int i = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(i!= PermissionChecker.PERMISSION_GRANTED){
            //申请权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WES);
           return;
        }else {
            getmPresenter().login(name, pwd);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSION_WES && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
            login();
        }else {
            showMessage("没有权限");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==111){
            User userInf = (User) data.getSerializableExtra("userInf");
            mUserName.setText(userInf.getName());
            mPwd.setText("");
        }
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
    public void loginSuccess(User user) {
        showMessage("登录成功!");
        SharedPreferencesUtil.getInstance(this).saveData(RegistActivity.USER_INFO,user);
        ActivityUtil.startActivity(this,MainActivity.class);
        finish();
    }
}
