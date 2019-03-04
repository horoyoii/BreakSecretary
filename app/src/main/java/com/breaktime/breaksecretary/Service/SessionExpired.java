package com.breaktime.breaksecretary.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;

public class SessionExpired extends Service {
    private static final String TAG = SessionExpired.class.getName();
    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    public SessionExpired() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        mFirebaseUtil = new FirebaseUtil();
        mUser = new User(mFirebaseUtil);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved()");
        mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
            @Override
            public void onCallback(User.Status_user value) {

                if (value == User.Status_user.ONLINE) {
                    Log.d(TAG, value.name() + "1");
                    mUser.get_user_ref().removeValue();
                    Log.d(TAG, value.name() + "2");
                    mUser.user_logout();
                }
            }
        });

        stopSelf();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();

    }
}
