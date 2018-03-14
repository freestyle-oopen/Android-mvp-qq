package aiyagirl.nanchen.com.myapplication.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.modle.RegistModle;
import aiyagirl.nanchen.com.myapplication.presenter.RegistPresenter;
import aiyagirl.nanchen.com.myapplication.ui.contract.RegistContract;
import aiyagirl.nanchen.com.myapplication.utils.SharedPreferencesUtil;
import aiyagirl.nanchen.com.myapplication.utils.StringUtils;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity<RegistPresenter,RegistModle> implements RegistContract.View {
    @BindView(R.id.et_username)
    EditText mUserName;
    @BindView(R.id.et_pwd)
    EditText mPwd;
    @BindView(R.id.btn_regist)
    Button btnRegist;

public static String USER_INFO="user_info";
    @Override
    public int layoutResId() {
        return R.layout.activity_regist;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
    mPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId== EditorInfo.IME_ACTION_GO){
                regist();
                return true;
            }
            return false;
        }
    });
    }
    @OnClick({R.id.btn_regist})
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_regist:
                regist();
                break;

        }
    }

    private void regist() {
        String name = mUserName.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();
        if(!StringUtils.checkUserName(name)){
            showMessage("用户名不合法。3到20位，首字母必须是字母");
            return;
        }
        if(!StringUtils.checkPwd(pwd)){
            showMessage("密码不合法。3到20位的数字");
            return;
        }
        getmPresenter().regist(name,pwd);
    }


    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
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
    public void registSuccess(User user) {
        showMessage("注册成功!");
        SharedPreferencesUtil.getInstance(this).saveData(USER_INFO,user);
        Intent intent=new Intent();
        intent.putExtra("userInf",user);
        setResult(111,intent);
        finish();
    }
}
