# :ocean: Flood

__SOPT 25기 겨울 앱잼 : 안드로이드__

정보를 공유하는 가장 쉬운 방법<br/>
기업에서 어떤 정보들을 공유하는지 알고 싶지 않나요? <br/>
플러드는 기업에서 어떤 정보들을 공유하는지 시각화된 자료로 여러분께 보여드립니다.

<img src="https://user-images.githubusercontent.com/37169252/71724662-668fb700-2e74-11ea-8db1-a4276f9997cb.png" width= "800">

<br/>


## :building_construction: 프로그램 구조

* **data** : ui에서 화면을 그릴 때 사용하는 데이터를 모아놓은 패키지

* **ui**
  * 기능별 화면
    * adapter : 해당 화면에서 공용으로 사용하는 Adapter들을 모아놓은 패키지
  
* **util** : Util과 관련한 class 모음

<br/><br/>

## :earth_asia: 개발환경 및 사용 언어

- 안드로이드 스튜디오 3.4.2
- Kotlin

<br/><br/>

## :wrench: Dependencies
    
#### :point_right: Design, Layout, etc
   
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation "androidx.cardview:cardview:1.0.0"
    implementation 'de.hdodenhof:circleimageview:3.0.1'
    
> RecyclerView, CardView, Circled ImageView 커스텀을 위해 사용
   
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    
> 이미지 로딩을 위해 사용
    
    implementation 'com.github.Deishelon:RoundedBottomSheet:1.0.1'
    
> BottomSheet 다이얼로그 커스텀을 위해 사용
    

#### :point_right: 서버 통신
   
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
    
> REST API를 이용한 서버 통신을 위해 사용
    
#### :point_right: 권한 허용
    
    implementation "gun0912.ted:tedpermission:2.1.0"
    
> 권한 체크를 위해 사용

<br/><br/>

## :page_facing_up: 기능 소개

### :point_right: 핵심 기능

#### :one: OnSingleClickListener

* util package > OnSingleClickListener.kt <br/>

> `
Debounce 
`는 이벤트를 그룹화하여 특정시간이 지난 후 하나의 이벤트만 발생하도록 하는 기술이다. 즉, 순차적 호출을 하나의 그룹으로 "그룹화"할 수 있다.<br/>
여러번 클릭되면 이벤트가 여러번 호출되는 것을 방지하기 위해 중복 클릭 방지 시간(0.6초)을 설정한 후, 중복클릭인 경우 아무 이벤트를 발생하지 않게 하고, 중복 클릭이 아니라면 이벤트를 발생시키도록 했다.


#### :two: Kotlin Extension function

* util package > RetrotiExt.kt <br/>
> 확장함수란 클래스 밖에서도 사용할 수 있는 함수로서 해당되는 여러 클래스에 추가적으로 함수를 넣는 기능을 한다. 서버와 데이터를 주고받는 모든 클래스에서 각 클래스에 필요한 데이터 타입마다 서버와의 데이터 처리 요청을 해주어야 한다. 이러한 과정은 반복되어 해당 클래스들의 코드 길이를 늘려 가독성을 저하시킨다. <br/> 이에 util 패키지 내 RetrotiExt.kt 파일을 생성하여 통신이 실패하였을 때와 응답이 통신이 성공적이지 않을 때, 통신이 성공하고 원하는 데이터를 받았을 때의 경우를 나누어 각각에 대해 공통적인 처리를 진행한다. 이를 통해 기능마다 나뉜 데이터 클래스 아이템에 대한 통합적인 처리를 하여 20줄 이상씩을 줄일 수 있었다.

### :point_right: 역할 분담

|  <center>기능</center> | <center> 우선 순위 </center> | <center> 담당 </center> |
|:--------:|:--------:|:--------:|
|<center> 로그인 </center> |<center> 2순위 </center>|<center> 청하 </center>|
|<center> 회원가입 </center> |<center> 2순위 </center>|<center> 지희 </center>|
|<center> 뉴스피드 </center> |<center> 1순위 </center> |<center> 현주 </center>|
|<center> 북마크 </center> |<center> 2순위 </center> |<center> 청하 </center>|
|<center> 게시글 작성 </center> |<center> 1순위 </center> |<center> 청하 </center>|
|<center> 알림 </center> |<center> 3순위 </center> |<center> - </center>|
|<center> 마이페이지 </center> |<center> 2순위 </center> |<center> 지희 </center>|

<br/><br/>
## :question: 문제점

#### :point_right: 홈 키 관련 문제점

* 현 상황 
> Web에서 공유하기 버튼을 눌러 url을 가져와 Activity 내의 TextView에 띄워줘야 한다.

* 문제점
> 공유하기 버튼을 통해 한번 url을 가져와 입력한 후, app을 종료하지 않고 홈키를 눌러 나간 경우, 다시 공유하기를 눌러도 url을 가져오지 못한다

* 예정 해결법
> onUserLeaveHint() 라는 메소드를 사용할 예정 <br/>
> `
void Activity.onUserLeaveHint ()
`
이 메서드는 사용자에 의해 액티비티가 백그라운드로 전환되기 직전에 onPause 바로 앞에 호출된다. Home 키를 누르기 직전에 호출되며 Back키를 누르거나 전화 통화 앱이 올라올 때, 타이머에 의해 종료될 때는 호출되지 않는다. Home키는 키입력 이벤트로 전달되지 않아 검출이 어려운데 이 메서드가 호출될 때 Home키에 의해 백그라운드가 됨을 알 수 있다. 이 두 메서드는 상태란의 통지를 관리할 때 사용될 수 있으며 액티비티가 통지를 취소할 시점을 결정하는데 도움을 준다. 다음 예제는 사용자가 5초 이상 관심을 보이지 않으면 즉시 종료한다.

#### :point_right: RecyclerView Item 선택에 관한 문제점

* 현 상황
>  recyclerview에서 한 item을 선택했을 때 나머지 itemView들은 textColor가 회색으로 바뀌어야하고, 선택한 itemView만 검은색으로 되어야 한다.

* 문제점
>  각 itemView들을 하나하나 변경하는 것은 가능하지만, 그 나머지 itemVeiw들에 접근하기가 어려웠다.

* 일단 해결법
> Adapter에 itemClick이라는 interface를 만들어주고, 이 interface를 Dialog에서 호출해서 초기화해준다. <br/>
통신에서 받는 list와 별개로 check라는 arrayList를 따로 판다. itemClick을 통해 선택한 selectIdx을 받은 후, 그 idx가 아닌 경우에 회색으로 만들어주고, 선택 된 itemView만 검은 색으로 바꾸어 준다.

#### :point_right: Swipe and Refresh에 관한 문제점

* 현 상황
> 뷰를 위에서 아래로 스와이프(pull down)했을 때, 디자이너가 만든 gif로 로딩이미지를 처리해야 한다.

* 문제점
> 특정 이미지로 리프레시 로딩 이미지를 커스텀하기 어렵다


<br/><br/>
## :family_man_woman_girl_boy: 팀원 소개

:surfing_woman: **[선지희](https://github.com/JiheeSeon)**<br>

> 이번 앱잼을 통해 안드로이드의 전반적인 함수 사용 및 뷰 구현 등을 익혔다면, <br/>앞으로는 좀 더 시간적인 여유를 가지고 공부하여 컴팩트하고 깨끗한, 코틀린스러운 코드를 작성하고 싶습니다. <br/>그리고 플러드 안드 너무 고생 많았구 사...랑...ㅎ...♥ <br/>+ 다시는 없을 좋은 멤버들과 함께 해서 영광이었고, 하드캐리해준 현주랑 청하 다시 한번 너무 고마워! 밥사야지 룰루

:surfing_woman: **[성청하](https://github.com/cheongha)**<br>

> SOPT 안드로이드 파트 25기 OB 파트원입니다.<br>
두 번째 앱잼을 플러드와 함께 할 수 있어서 너무 좋아씁니다우~!<br>
플러드 기획, 디자인, 안드, iOS, 서버 팀원들 다 잘 맞고 열심히 해서 좋아요 ><

:surfing_woman: **[이현주](https://github.com/bokdoll)**<br>

> SOPT25기 안드로이드 OB파트원이자 안드로이드 리드 개발자로 개발에 참여하게 되었습니다. <br/>
> 플러드라는 좋은 기획으로 좋은 팀원들 만나 좋은 결과물 낼 수 있어서 행복했습니다 :) <br/>
> Flood 최고~~ :heart:
