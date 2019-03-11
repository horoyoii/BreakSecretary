package com.beacon.breaksecretary.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import com.beacon.breaksecretary.Util.FirebaseUtil;
import com.beacon.breaksecretary.activity.MainActivity;
import com.beacon.breaksecretary.model.User;

public class App extends Application {
    public static final String CHANNEL_ID = "ServiceChannel";
    public static final int EMPTY_RANGE = 3;
    public MainActivity ma;
    public FirebaseUtil mFirebaseUtil;
    public User mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "Application Class onCreate");
        createNotificationChannel();
        mFirebaseUtil = new FirebaseUtil();
        mUser = new User(mFirebaseUtil);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("App", "onTerminate");
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            // oreo 와 같거나 높다면 채널을 생성한다.
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        }
    }

    public void ref(MainActivity ma){
        this.ma = ma;
    }
}