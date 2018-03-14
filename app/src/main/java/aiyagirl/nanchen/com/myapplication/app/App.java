package aiyagirl.nanchen.com.myapplication.app;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aiyagirl.nanchen.com.myapplication.R;
import aiyagirl.nanchen.com.myapplication.ui.activity.BaseActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.ChatActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.LoginActivity;
import aiyagirl.nanchen.com.myapplication.ui.activity.MainActivity;
import aiyagirl.nanchen.com.myapplication.ui.adapter.BaseEMMessageListener;
import aiyagirl.nanchen.com.myapplication.utils.DBUtil;


/**
 * Created by renyukai on 2017/6/13.
 */

public class App extends Application {

    private static App app;
    private SoundPool soundPool;
    private int duan;
    private int yulu;
    private NotificationManager notificationManager;

    private List<BaseActivity> activities=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        DBUtil.initDB(this);
        initSoundPool();

        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (!(processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName()))) {
            //初始化
            EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(false);
        }
        initOutLineListener();
        initMessageListener();

         //LeakCanary初始化
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 监听被挤掉线
     */
    private void initOutLineListener() {
        //监听连接状态
        EMClient instance = EMClient.getInstance();
        instance.addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {
            }

            @Override
            public void onDisconnected(int i) {

                if (i==EMError.USER_LOGIN_ANOTHER_DEVICE){
                    for(BaseActivity activity : activities){
                        activity.finish();
                    }
                    activities.clear();

                    Intent intent = new Intent(App.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });
    }


    public void addActivity(BaseActivity activity){
        if(activities.contains(activity)){
            return;
        }
        activities.add(activity);
    }
    public void delectActivity(BaseActivity activity){
        activities.remove(activity);
    }
    private void initSoundPool() {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        duan = soundPool.load(this, R.raw.duan, 1);
        yulu = soundPool.load(this, R.raw.yulu, 1);
    }

    private void initMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new BaseEMMessageListener(){
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                //收到新消息 ，取第一个
                if (isRunningBackgrad()) {
                    soundPool.play(yulu, 1f, 1f, 0, 0, 1);
                    //弹出通知栏
                    showNotification(list.get(0));
                }else {
                    soundPool.play(duan, 1f, 1f, 0, 0, 1);
                }
                EventBus.getDefault().post(list.get(0));
            }
        });
    }

    //弹出通知栏
    private void showNotification(EMMessage emMessage) {
        if(!(emMessage.getBody() instanceof EMTextMessageBody)){
            return;
        }
        EMTextMessageBody body= (EMTextMessageBody) emMessage.getBody();
        if(notificationManager==null){
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        Intent mainIntent=new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chatIntent=new Intent(this, ChatActivity.class);
        chatIntent.putExtra("chat_name",emMessage.getUserName());
        Intent[] intents={mainIntent,chatIntent};

        PendingIntent pendingIntent=PendingIntent.getActivities(this,110,intents,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification=new Notification.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.avatar5))
                .setSmallIcon(R.mipmap.contact_selected_2)
                .setContentTitle("您有一条新消息")
                .setContentText(body.getMessage())
                .setContentInfo("来自："+emMessage.getUserName())
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(1,notification);
    }

    public static App getApp() {
        return app;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


    //判断当前应用是否运行在后台
    public boolean isRunningBackgrad() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
        String topPackageName = runningTasks.get(0).topActivity.getPackageName();
        return !topPackageName.equals(getPackageName());
    }

}
