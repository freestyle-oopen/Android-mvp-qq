package aiyagirl.nanchen.com.myapplication.modle;



import java.util.List;
import aiyagirl.nanchen.com.myapplication.entity.User;
import aiyagirl.nanchen.com.myapplication.net.Response;
import aiyagirl.nanchen.com.myapplication.rx.RxHelper;
import aiyagirl.nanchen.com.myapplication.ui.contract.AddContactContract;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/28.
 */

public class AddContactModle implements AddContactContract.Modle {

    @Override
    public Observable<Response<List<User>>> searchUser(String keyword,String currentUser) {
        return RxHelper.wrap(RxHelper.getService().searchUser(keyword,currentUser) , true);
    }
}
