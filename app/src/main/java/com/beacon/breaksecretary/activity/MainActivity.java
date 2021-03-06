package com.beacon.breaksecretary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.beacon.breaksecretary.Application.App;
import com.beacon.breaksecretary.Observer;
import com.beacon.breaksecretary.R;
import com.beacon.breaksecretary.Service.MyService;
import com.beacon.breaksecretary.Service.SessionExpired;
import com.beacon.breaksecretary.Subject;
import com.beacon.breaksecretary.Util.FirebaseUtil;
import com.beacon.breaksecretary.Util.Singleton;
import com.beacon.breaksecretary.adapter.FragmentAdapter;
import com.beacon.breaksecretary.fragment.MyStatusFragment;
import com.beacon.breaksecretary.fragment.QuickReserveFragment;
import com.beacon.breaksecretary.fragment.ReserveAndCheckFragment;
import com.beacon.breaksecretary.fragment.SettingFragment;
import com.beacon.breaksecretary.fragment.TimeLineFragment;
import com.beacon.breaksecretary.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity implements View.OnClickListener, Subject {
    private static final String TAG = MainActivity.class.getName();

    public FirebaseUtil mFirebaseUtil;
    public User mUser;
    private TabLayout mTabLayout;
    public ViewPager mViewPager;

    private int[] tabIcons = {
            R.drawable.ic_flash_on_white_24dp,
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_timeline_white_24dp,
            R.drawable.ic_settings_white_24dp
    };

    public ArrayList<Observer> observers;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d("HHH", "called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        observers = new ArrayList<>();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, null));

        mFirebaseUtil = new FirebaseUtil();
        mUser = new User(mFirebaseUtil);

        initViewPager();

        ((App)getApplicationContext()).ref(this);

        if (mFirebaseUtil.getCurrentUser() == null || !mFirebaseUtil.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
            finish();
        } else {
            //mUser.user_login();
            show_snackbar_msg("Successfully signed in : " + mUser.getEmailForSingleEvent(), true);
        }



    }



    @Override
    public void registerObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void deleteObserver(Observer ob) {
        int index = observers.indexOf(ob);
        observers.remove(index);
    }

    @Override
    public void notifyTheStatus(User.Status_user status) {
        Log.d("TEST", "call notify in subject");
        // To observers

        switch (status) {
            case ONLINE:
            case SUBSCRIBING:
                mViewPager.setCurrentItem(0);
                break;
            case RESERVING:
                mViewPager.setCurrentItem(2);
                break;
            case RESERVING_OVER:
            case OCCUPYING:
            case OCCUPYING_OVER:
            case STEPPING_OUT:
            case STEPPING_OUT_OVER:
            case PAYING_PENALTY:
            case BEING_BLOCKED:
                mViewPager.setCurrentItem(2);
                break;
        }


        for(Observer ob : observers){
            ob.update(status);
        }


        // To service
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();


    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        super.onBackPressed();
    }


    private void initViewPager() {
        mTabLayout = findViewById(R.id.tab_layout_main);
        mViewPager = findViewById(R.id.view_pager_main);
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.tab_title_main_1));
        titles.add(getString(R.string.tab_title_main_2));
        titles.add(getString(R.string.tab_title_main_3));
        titles.add(getString(R.string.tab_title_main_4));
        titles.add(getString(R.string.tab_title_main_5));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(4)));
        //mTabLayout

        List<Fragment> fragments = new ArrayList<>();
        QuickReserveFragment quickReserveFragment = new QuickReserveFragment();
        fragments.add(quickReserveFragment);
        registerObserver(quickReserveFragment);

        fragments.add(new ReserveAndCheckFragment());

        MyStatusFragment myStatusFragment = new MyStatusFragment();
        fragments.add(myStatusFragment);
        registerObserver(myStatusFragment);

        fragments.add(new TimeLineFragment());

        fragments.add(new SettingFragment());


        mViewPager.setOffscreenPageLimit(4);
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        Objects.requireNonNull(mTabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(mTabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(mTabLayout.getTabAt(3)).setIcon(tabIcons[3]);
        Objects.requireNonNull(mTabLayout.getTabAt(4)).setIcon(tabIcons[4]);
    }


    @Override
    public void onClick(View view) {

    }

    public void startService(int section , int seat){
        // TODO: 인자로 자리 섹터- 번호 를 받고
        // 여기서 비콘 major minor로 변환한다.`
        mUser.user_reserve(section,seat);
        Intent intent2 = new Intent(this, MyService.class);
        intent2.putExtra(ReservingActivity.R_MAJOR, SectionToMajor(section));
        intent2.putExtra(ReservingActivity.R_MINOR, SeatToMinor(seat));
        ContextCompat.startForegroundService(this, intent2);

        Intent intent = new Intent(this, ReservingActivity.class);
        intent.putExtra("major", section);
        intent.putExtra("minor", seat);
        startActivity(intent);
    }

    public void stopService(){
        //mUser.get_user_ref().child("status").setValue(User.Status_user.ONLINE);
        mUser.user_stop();
        Intent intent = new Intent(this, MyService.class);
        this.stopService(intent);
    }

    public int SectionToMajor(int sectionNum){
        switch (sectionNum){
            case 1:
                return 1002;
            case 2:
                return 1002;
            case 3:
                return 1002;
            case 4:
                return 1002;
        }
        return 0;
    }
    public int SeatToMinor(int seatNum){
        return 20;
    }
}
