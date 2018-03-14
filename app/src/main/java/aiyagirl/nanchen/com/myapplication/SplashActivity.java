package aiyagirl.nanchen.com.myapplication;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import aiyagirl.nanchen.com.myapplication.modle.SplashModle;
import aiyagirl.nanchen.com.myapplication.presenter.SplashPresenter;
import aiyagirl.nanchen.com.myapplication.ui.activity.BaseActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.LoginActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.MainActivity;
import aiyagirl.nanchen.com.myapplication.ui.contract.SplashContract;
import aiyagirl.nanchen.com.myapplication.utils.ActivityUtil;
import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/28.
 */

public class SplashActivity extends BaseActivity<SplashPresenter,SplashModle> implements SplashContract.View{

    private static final long STOP_TIME = 1000;
    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.splash_image)
    ImageView splashImage;


    @Override
    public int layoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onBind(Bundle savedInstanceState) {
        //判断是否登录
     getmPresenter().checkLogin();
    }


    @Override
    public void checkLogined(boolean isLogined) {
        if(isLogined){//已经登录
            ActivityUtil.startActivity(SplashActivity.this,MainActivity.class);
            finish();
        }else { //未登录过
            ObjectAnimator.ofFloat(splashImage,"alpha",0,1).setDuration(STOP_TIME).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityUtil.startActivity(SplashActivity.this,LoginActivity.class);
                    finish();
                }
            },STOP_TIME);
        }
    }

    @Override
    public void showMessage(String msg) {

    }
}
