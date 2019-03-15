# BreakSecretary

## 1. Briedf Development Environmnet

### Application  
Native App on Android
JAVA

### DATABASE  
Firebase - Realtime Database

### H/W Device
BLE Beacon ( iBeacon )
![image](https://user-images.githubusercontent.com/34915108/54450123-028bed00-4793-11e9-885c-d762afc43631.png)


## 2. Purpose of This Project  

 공공 도서관 등 자리가 한정된 공간에서 개인 소유물만 놓아두고 실점유를 하지 않는 경우를 방지하기 위한 어플리케이션.  
각각의 자리에 설치된 BLE 비콘으로부터의 신호를 받아 거리를 측정하여 
사용자의 현재 상태(실제로 사용중이거나 이탈한 경우 등)를 판단한다. 
이후 제한된 이탈시간 동안 돌아오지 않는다면 자동으로 자리가 반납되는 등 공공장소에서 자리의 실제 점유를 장려하기 위한 어플리케이션.



## 3. Demonstration ScreenShot  

https://blog.naver.com/demonic3540/221489455231



## 4. What I've Experienced.

1. [서비스]  
서버가 따로 없었기에 안드로이드 컴포넌트 중 하나인 서비스에 크게 의존할 수밖에 없었다. 그렇기에 포그라운드 서비스를 구현해야 했으며
Always on Notification 등 서비스에 대한 더 깊은 이해가 가능했다.

2. [디자인 패턴]  
 enum으로 정의된 유저의 상태는 액티비티, 프래그먼트, 파이어베이스, 서비스 등 여러 컴포넌트에서 공유되는 값이다. 그렇기에 Consistency를 유지하는게 중요하였고 또한 어떻게 각각의 컴포넌트에 변하는 값들을 일정한 규칙으로 알려줄지에 대한 고심 끝에 Observer Pattern을 적용하여 꽤나 깔끔한 코드를 구현할 수 있었다.
뿐만 아니라 Singleton Pattern을 적용하여(이 패턴에 동반되는 여러 문제들은 해결하고) 설정정보 등을 효율적으로 관리할 수 있었다.

3. [비동기적 네트워킹에 대한 처리]  
 파이어베이스로부터 여러 데이터를 받아오는 작업은 비동기적이다. 그렇기에 데이터가 도착하기 전 그 데이터를 사용하는 안드로이드 컴포넌트가 onCreate로 진입하면 안되었다. 그렇기에 동기화를 위하여 Splash Activity 를 추가함으로써 네트워크 작업에 대한 동기화를 할 수 있었다.




## 5. Team Members  
 최광영 이희관 이영민

