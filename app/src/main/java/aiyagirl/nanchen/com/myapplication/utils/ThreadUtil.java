package aiyagirl.nanchen.com.myapplication.utils;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/10/25.
 */

public class ThreadUtil {
   private static Executor executor= Executors.newSingleThreadExecutor();
   private static Handler handler=new Handler(Looper.getMainLooper());

    public static void runOnSubThread(Runnable runnable){
        executor.execute(runnable);
    }
    public static  void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }
}
