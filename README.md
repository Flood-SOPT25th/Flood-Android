# :ocean: Flood

__SOPT 25기 겨울 앱잼 : 안드로이드__


## :building_construction: 프로그램 구조

* **data** : ui에서 화면을 그릴 때 사용하는 데이터를 모아놓은 패키지

* **ui**
  * 기능별 화면
    * adapter : 해당 화면에서 공용으로 사용하는 Adapter들을 모아놓은 패키지
  
* **util** : Util과 관련한 class 모음


## :earth_asia: 개발환경 및 사용 언어

- 안드로이드 스튜디오 3.4.2
- Kotlin


## :wrench: Dependencies
    
#### :point_right: Design, Layout, etc
   
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation "androidx.cardview:cardview:1.0.0"
    implementation 'de.hdodenhof:circleimageview:3.0.1'
    
RecyclerView, CardView, Circled ImageView 커스텀을 위해 사용
   
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    
이미지 로딩을 위해 사용
    
    implementation 'com.github.Deishelon:RoundedBottomSheet:1.0.1'
    
BottomSheet 다이얼로그 커스텀을 위해 사용
    

#### :point_right: 서버 통신
   
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
    
REST API를 이용한 서버 통신을 위해 사용
    
#### :point_right: 권한 허용
    
    implementation "gun0912.ted:tedpermission:2.1.0"
    
권한 체크를 위해 사용


## :page_facing_up: 기능 소개

#### :point_right: 핵심 기능

##### :one: OnSingleClickListener

> ddddddddddddddddddddddddd


##### :two: Kotlin Extension function

> ddddddddddddddddddddddddd

#### :point_right: 역할 분담

|  <center>기능</center> | <center> 우선 순위 </center> | <center> 담당 </center> |
|:--------:|:--------:|:--------:|
|<center> 로그인 </center> |<center> 2순위 </center>|<center> 청하 </center>|
|<center> 회원가입 </center> |<center> 2순위 </center>|<center> 지희 </center>|
|<center> 뉴스피드 </center> |<center> 1순위 </center> |<center> 현주 </center>|
|<center> 북마크 </center> |<center> 2순위 </center> |<center> 청하 </center>|
|<center> 게시글 작성 </center> |<center> 1순위 </center> |<center> 청하 </center>|
|<center> 알림 </center> |<center> 3순위 </center> |<center> - </center>|
|<center> 마이페이지 </center> |<center> 2순위 </center> |<center> 지희 </center>|


## :family_man_woman_girl_boy: 팀원 소개

:surfing_woman: **선지희**<br>

> qmfkqkfqkqkqkfqkfkqfkqkfqkfqkfkqfkqkfqkfkqf

:surfing_woman: **성청하**<br>

> qmfkqkfqkqkqkfqkfkqfkqkfqkfqkfkqfkqkfqkfkqf

:surfing_woman: **이현주**<br>

> SOPT25기 안드로이드 OB파트원이자 안드로이드 리드 개발자로 개발에 참여하게 되었습니다. <br/>
> 플러드라는 좋은 기획으로 좋은 팀원들 만나 좋은 결과물 낼 수 있어서 행복했습니다 :) <br/>
> Flood 최고~~ :heart:
