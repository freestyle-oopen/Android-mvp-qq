package aiyagirl.nanchen.com.myapplication.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import aiyagirl.nanchen.com.myapplication.modle.BaseModle;
import aiyagirl.nanchen.com.myapplication.presenter.BasePresenter;
import aiyagirl.nanchen.com.myapplication.utils.InstanceUtils;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Renyukai on 2017/6/13.
 */

public abstract class BaseFragment<P extends BasePresenter , M extends BaseModle> extends Fragment{
    private P mPresenter;
    private Unbinder mBind;

    private ProgressDialog progressDialog;
    public abstract int layoutResId();
    public abstract void onBind(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutResId()==0){
            throw new RuntimeException("Fragment--No layout is set");
        }
      View  mContentView = inflater.inflate(layoutResId(), container, false);
        mBind = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = InstanceUtils.getInstance(this, 0);
        if (mPresenter != null) {
            mPresenter.onCreate(this);
        }
        progressDialog = new ProgressDialog(getActivity());
        this.onBind(savedInstanceState);
    }

    public P getmPresenter() {
        return mPresenter;
    }
    public void showDialog(boolean isCancel){
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(isCancel);
        if(progressDialog.isShowing()){
            return;
        }
        progressDialog.show();
    }

    public void dismissDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mPresenter != null){
            mPresenter.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        if (mPresenter != null) mPresenter.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mPresenter != null) mPresenter.onPause();
        super.onPause();
    }


    @Override
    public void onStop() {
        if (mPresenter != null) mPresenter.onStop();
        super.onStop();
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        if (mBind != null) mBind.unbind();
        super.onDestroy();
    }
}
