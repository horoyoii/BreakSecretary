package com.beacon.breaksecretary.Util;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.beacon.breaksecretary.activity.SplashActivity;
import com.beacon.breaksecretary.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
초기 비동기 네트워킹 작업에서 필요한 정보들을 모두 저장한다.

 */

/*
문제
1) 멀티쓰레딩 환경에서의 다중생성  - 동기화로 해결

 */


public class Singleton {
    // 유일한 인스턴스를 담고있는 static 변수
    private static Singleton singleton;
    private ArrayList<Integer> LimitsArray;
    private ArrayList<Integer> PeakTimeArray;
    private ArrayList<Integer> UsingSeatNumber;
    private ArrayList<ArrayList<Integer>> AllUsingSeatPosition;
    private String UserMail;
    private String CurrentSection =null;
    private int CurrentSeatNum = 0;

    public String getCurrentSection() {
        return CurrentSection;
    }

    public int getCurrentSeatNum() {
        return CurrentSeatNum;
    }

    public SplashActivity splashactivityRef;

    private Singleton(){

    }

    // Singleton 인스턴스를 얻기 위한 static 메소드
    public static Singleton getInstance() {
        // 아직 인스턴스가 생성된적이 없음 = 존재하지 않음
        if(singleton == null) {
            synchronized (Singleton.class){
                // 인스턴스 생성. 생성자가 private이니까 클래스 내부에서는 가능.
                singleton = new Singleton();
            }
        }
        return singleton;
    }

    public void setCurrentSection(String currentSection) {
        CurrentSection = currentSection;
    }

    public void setCurrentSeatNum(int currentSeatNum) {
        CurrentSeatNum = currentSeatNum;
    }

    public void Init(FirebaseUtil firebaseUtil, User mUserUtil, SplashActivity sa){
        Log.d("HHH", "called Init in singleton");
        this.splashactivityRef = sa;
        LimitsArray = new ArrayList<>();
        PeakTimeArray = new ArrayList<>();
        UsingSeatNumber = new ArrayList<>();
        AllUsingSeatPosition = new ArrayList<>();

        // 1) [세팅] 제한사항 받아오기
        firebaseUtil.getSettingRef().child("Limits").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("HEE", ds.getKey()+" :"+String.valueOf(ds.getValue(Integer.class)));
                    LimitsArray.add(ds.getValue(Integer.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 2) [세팅] 총 좌석수 받아오기
        firebaseUtil.getCounterRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("HEE", "남은 좌석 : "+ds.getKey()+" :"+String.valueOf(ds.getValue(Integer.class)));
                    UsingSeatNumber.add(ds.getValue(Integer.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 3) [세팅] 사용중인 좌석 정보 받아오기
        firebaseUtil.getSeatsRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Integer> childList = null;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("HEE", "Key"+ds.getKey());
                    childList = new ArrayList<>();
                    for(DataSnapshot child : ds.getChildren()){
                        Log.d("HEE", "사용중인 좌석 정보 : "+child.getKey()+" :"+child.getValue(String.class));
                        if(child.getValue(String.class).equals("None") )
                            childList.add(0);
                        else
                            childList.add(1);
                    }
                    AllUsingSeatPosition.add(childList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 4) 현재 유저 email
        UserMail = firebaseUtil.getCurrentUser().getEmail();



        // 6) [세팅] 피크 타임 시간 받아오기
        firebaseUtil.getSettingRef().child("Peak").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("HEE", ds.getKey()+" :"+String.valueOf(ds.getValue(Integer.class)));
                    PeakTimeArray.add(ds.getValue(Integer.class));
                }

                // 콜백함수를 통하여 비동기 처리가 완료되었음을 Splash activity에 알려준다.

                Log.d("HHH", "데이터 받아오기 done");
                splashactivityRef.CallBack_SingleTon_Done();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("HHH", "end of init func in singleton");
    }

    public int getLimitsReserving(){
        return LimitsArray.get(4);
    }
    public int getLimitsStepOut(){
        return LimitsArray.get(5);
    }

    public String ToSection(int section){
        switch (section){
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
        }
        return null;
    }
    public int getUsingCounterSectionA(){return UsingSeatNumber.get(0);}
    public int getUsingCounterSectionB(){return UsingSeatNumber.get(1);}
    public int getUsingCounterSectionC(){return UsingSeatNumber.get(2);}
    public int getUsingCounterSectionD(){return UsingSeatNumber.get(3);}
    public String getUserMail(){return UserMail;}
    public ArrayList<Integer> getUsingSectorAseat(){return AllUsingSeatPosition.get(0);}
    public ArrayList<Integer> getUsingSectorBseat(){return AllUsingSeatPosition.get(1);}
    public ArrayList<Integer> getUsingSectorCseat(){return AllUsingSeatPosition.get(2);}
    public ArrayList<Integer> getUsingSectorDseat(){return AllUsingSeatPosition.get(3);}
    public int getPeakStartTime(){return PeakTimeArray.get(1);}
    public int getPeakEndTime(){return PeakTimeArray.get(0);}

}
