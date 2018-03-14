package aiyagirl.nanchen.com.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.modle.PluginFragmentModle;
import aiyagirl.nanchen.com.myapplication.presenter.PluginFragmentPersenter;
import aiyagirl.nanchen.com.myapplication.ui.activity.BaseActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.LoginActivity;
import aiyagirl.nanchen.com.myapplication.ui.contract.PluginFragmentContract;
import aiyagirl.nanchen.com.myapplication.utils.ActivityUtil;
import aiyagirl.nanchen.com.myapplication.utils.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/7.
 */

public class PluginFragment extends BaseFragment<PluginFragmentPersenter, PluginFragmentModle> implements PluginFragmentContract.View {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_logout)
    TextView btn_logout;

    @Override
    public int layoutResId() {
        return R.layout.fragmen_plugin;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        title.setText("动态");
        String currentUser = EMClient.getInstance().getCurrentUser();
        btn_logout.setText("退（" + currentUser + "）出");
    }

    @OnClick({R.id.btn_logout})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_logout) {
            getmPresenter().logout();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toastor.show(msg);
    }

    @Override
    public void logoutResult(boolean isSuccess, String msg) {
        if (!isSuccess) {
            showMessage(msg);
        }
        ActivityUtil.startActivity(getActivity(), LoginActivity.class);
        getActivity().finish();
    }
}
