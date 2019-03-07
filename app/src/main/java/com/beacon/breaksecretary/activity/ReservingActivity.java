package com.beacon.breaksecretary.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beacon.breaksecretary.Application.App;
import com.beacon.breaksecretary.Observer;
import com.beacon.breaksecretary.R;
import com.beacon.breaksecretary.Service.MyService;
import com.beacon.breaksecretary.Util.Singleton;
import com.beacon.breaksecretary.model.User;
import com.dinuscxj.progressbar.CircleProgressBar;

public class ReservingActivity extends BaseActivity implements Observer {
    public static final String R_MAJOR = "major";
    public static final String R_MINOR = "minor";
    Button cancel;
    @Override
    public void update(User.Status_user status) {
        Log.d("TTT", "Call update in RA :"+status);
        switch (status){
            case OCCUPYING:
                finish();
                break;
            case RESERVING_OVER:
                show_toast_msg("예약 시간 초과", true);
                finish();
            break;
        }
    }

    CircleProgressBar mProgressBar;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((App)getApplicationContext()).ma.deleteObserver(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserving);

        mProgressBar=findViewById(R.id.progressbar);
        mProgressBar.setMax(Singleton.getInstance().getLimitsReserving());
        mProgressBar.setProgressTextSize(80);
        mProgressBar.setProgressFormatter(new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return max-progress+"s";
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                messageReceiver, new IntentFilter(MyService.ACTION_COUNTER_RES));

        ((App)getApplicationContext()).ma.registerObserver(this);

        Intent intent = getIntent();
        int mj = intent.getIntExtra(R_MAJOR, 0);
        int mi = intent.getIntExtra(R_MINOR, 0);

        TextView textView = findViewById(R.id.rsv_txt);
        String us = "Go To \nMajor : "+String.valueOf(mj)+"\nMinor : "+String.valueOf(mi);
        textView.setText(us);

        cancel = findViewById(R.id.rsv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_toast_msg("예약 취소", true);
                finish();
                ((App) getApplicationContext()).ma.stopService();
            }
        });

    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int dis = intent.getIntExtra("msg", 0);
            mProgressBar.setProgress(dis);
        }
    };

}
