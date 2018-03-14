package aiyagirl.nanchen.com.myapplication.modle;

import aiyagirl.nanchen.com.myapplication.entity.Wether;
import aiyagirl.nanchen.com.myapplication.ui.contract.ConversationFragmentContract;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ConversationFragmentModle implements ConversationFragmentContract.Modle {

    @Override
    public Observable<Wether> getWhear() {
        return null;
    }
}
