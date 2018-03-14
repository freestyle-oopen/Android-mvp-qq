package aiyagirl.nanchen.com.myapplication.ui.fragment;

import android.os.Bundle;
import aiyagirl.nanchen.com.myapplication.modle.MainModle;
import aiyagirl.nanchen.com.myapplication.presenter.MainPersenter;
import aiyagirl.nanchen.com.myapplication.ui.contract.MainContract;

public class TestFragment extends BaseFragment<MainPersenter,MainModle> implements MainContract.View {
    @Override
    public void showMessage(String msg) {

    }

    @Override
    public int layoutResId() {
        return 0;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {

    }
}
