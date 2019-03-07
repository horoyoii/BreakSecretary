package com.beacon.breaksecretary.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.beacon.breaksecretary.R;
import com.beacon.breaksecretary.Util.FirebaseUtil;
import com.beacon.breaksecretary.Util.Singleton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;


/*

비동기 와 콜백 함수

동기(synchronous : 동시에 일어나는)

 - 동기는 말 그대로 동시에 일어난다는 뜻입니다. 요청과 그 결과가 동시에 일어난다는 약속인데요. 바로 요청을 하면 시간이 얼마가 걸리던지 요청한 자리에서 결과가 주어져야 합니다.
-> 요청과 결과가 한 자리에서 동시에 일어남
-> A노드와 B노드 사이의 작업 처리 단위(transaction)를 동시에 맞추겠다.

비동기(Asynchronous : 동시에 일어나지 않는)

 - 비동기는 동시에 일어나지 않는다를 의미합니다. 요청과 결과가 동시에 일어나지 않을거라는 약속입니다.
-> 요청한 그 자리에서 결과가 주어지지 않음
-> 노드 사이의 작업 처리 단위를 동시에 맞추지 않아도 된다.

개발자는 오래 걸리는 작업들을 외부 Thread를 통해 백그라운드 처리하지 않으면 ANR(Android Not Responding)을 보게 될 것



 */
public class SplashActivity extends AppCompatActivity {
    private FirebaseUtil mFirebaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.loading);
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        DrawableImageViewTarget target = new DrawableImageViewTarget(imageView);
        Glide.with(this).setDefaultRequestOptions(requestOptions.override(400,400)).load(R.raw.loading).into(target);

        // 파이어베이스의 리스너는 비동기 리스너이다.
        InitSetting();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("App", "Call onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HHH", "Call onResume");

        // ====================================
        // 딜레이 주기 how to?
    }

    public void InitSetting(){
        mFirebaseUtil = new FirebaseUtil();
        Singleton.getInstance().Init(mFirebaseUtil, this);
    }
    public void CallBack_SingleTon_Done(){

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();

    }




}
