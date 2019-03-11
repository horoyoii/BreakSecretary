package com.beacon.breaksecretary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beacon.breaksecretary.Observer;
import com.beacon.breaksecretary.R;
import com.beacon.breaksecretary.Util.FirebaseUtil;
import com.beacon.breaksecretary.activity.MainActivity;
import com.beacon.breaksecretary.activity.ReservingActivity;
import com.beacon.breaksecretary.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;


// In this case, the fragment displays simple text based on the page
public class QuickReserveFragment extends Fragment implements Observer {
    private static final String TAG = QuickReserveFragment.class.getName();
    private View view;

    Button quick_btn;
    private FirebaseUtil mFirebaseUtil;
    private User mUser;
    private boolean isFlag = false;
    private MainActivity mActivity;

    CountDownTimer mCountDownTimer;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        view = inflater.inflate(R.layout.fragment_quickreserve, container, false);


        ViewInit();
        return view;
    }

    public void ViewInit(){
        quick_btn = view.findViewById(R.id.btn_quick_res);
        quick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startService(1, 2);
            }
        });



        ImageView imageView = view.findViewById(R.id.imageView2);
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        DrawableImageViewTarget target = new DrawableImageViewTarget(imageView);
        Glide.with(this).setDefaultRequestOptions(requestOptions.override(300,500)).load(R.raw.bgbg).into(target);



    }

    @Override
    public void update(User.Status_user status){
            Log.d("HEEH", "update in fragement");
            switch (status){
                case ONLINE:
                    quick_btn.setText("예약하기");
                    quick_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mActivity.startService(1, 2);
                        }
                    });
                    break;
                case RESERVING:
                    isFlag = true;

                    break;
                case RESERVING_OVER:

                    break;
                case OCCUPYING:
                    isFlag = false;
                    quick_btn.setText("사용중");
                    quick_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mActivity.startService(1002, 20);
                        }
                    });

                    break;
                case STEPPING_OUT:
                    break;
                case STEPPING_OUT_OVER:

                    break;
                case SUBSCRIBING:
                    break;
            }


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            // Restore last state for checked position.

        }

        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

}
