package aiyagirl.nanchen.com.myapplication.modle;



import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.net.Response;
import aiyagirl.nanchen.com.myapplication.rx.RxHelper;
import aiyagirl.nanchen.com.myapplication.ui.contract.LoginContract;
import aiyagirl.nanchen.com.myapplication.ui.contract.RegistContract;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/28.
 */

public class RegistModle implements RegistContract.Modle {
    @Override
    public Observable<Response<Boolean>> addUserToDB(User user) {
        return RxHelper.wrap(RxHelper.getService().addUserToDB(user.getName(),user.getPwd()) ,false);
    }

    @Override
    public Observable<Response<Boolean>> delectUser(User user) {
        return RxHelper.wrap(RxHelper.getService().delectUser(user.getName(),user.getPwd()),true);
    }
}
