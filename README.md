# Flow-Android
제대로 기록하는 당신의 지출 흐름, Flow ✍🏻 Android Repository

![](https://user-images.githubusercontent.com/100260416/220561044-df2a16d8-2180-43dd-8e75-d3056265c415.png)

## 💡 Flow는 어떤 서비스인가요?
Flow는 정확한 소비 흐름을 분석하지 못하는 기존 서비스의 한계점을 개선한 금융앱입니다😊
### ✍🏻 기존 서비스의 한계점은 무엇인가요?
**1. 더치 페이 상황을 커버하지 못하는 분석 서비스**<br><br>
지갑에도 없던 몇백만원이 소비한 금액으로 찍혀있던 경험, 다들 있으신가요?<br>
실질적인 지출은 내가 먹은 금액에 불과하지만, 한번에 결제한 비용 전체가 소비한 금액으로,<br> 친구들이 이체한 금액은 수입으로 분류되어 수입과 소비 금액이 눈덩이처럼 늘어납니다.<br><br>
**2. 거래 대행사 정보에 의존한 카테고리 분류의 사각지대 존재**<br><br>
온라인에서 구매한 내역들, 다양한 카테고리의 소비가 이뤄짐에도 불구하고<br> NICE 결제대행과 같은 거래 대행사 정보로 되어있는 내역의 카테고리는 온라인쇼핑, 혹은 기타로 분류되곤 합니다.<br>
이체한 내역도 마찬가지입니다. 식비, 여행 등 다양한 목적으로 이체가 이뤄지지만, 이체한 내역은 모두 이체 카테고리로 분류됩니다
### ✍🏻 Flow의 주요 기능은 아래와 같아요!
![image](https://user-images.githubusercontent.com/52921222/222952521-38e0b957-07c8-47c2-9476-a4872bfc3c91.png)
![image](https://user-images.githubusercontent.com/52921222/222952539-20e9335f-7e26-4c50-b191-ef0f61aa215e.png)
![image](https://user-images.githubusercontent.com/52921222/222952557-d81b750a-c6ae-415d-8400-7ba65a52e71e.png)
![image](https://user-images.githubusercontent.com/52921222/222952571-f4cfff80-b54b-444f-9f88-1cbc2e1e6ade.png)
![image](https://user-images.githubusercontent.com/52921222/222952579-a16fe7ec-185f-4052-b0a9-ac18ebca69f9.png)
### ✍🏻 Q&A
1. 내역은 어떻게 받아오나요?<br>
: 은행 API와 연결하지 않고, 은행앱 알림 혹은 입출금 문자 내역을 캡쳐한 후 문자열을 파싱하여 내역 정보를 추출하여 자동 기입합니다. 수동으로 기입도 가능합니다.
2. 다른 날짜의 내역들도 통합이 가능한가요?<br>
: 넵, 가능합니다. 통합된 내역은 대표 내역의 날짜를 기준으로 정렬되어 보여집니다.
3. 대표 내역은 지출이지만, 통합된 금액이 양수이면 어떻게 되나요?<br>
: 지출과 수입 태그는 대표 내역이 아닌 통합된 금액을 기준으로 나타납니다. 통합된 금액을 기준으로 양수이면 지출로, 음수이면 수입으로 분류됩니다. 
4. 카테고리는 매번 push알림을 통해 선택해야 하나요?<br>
: 기본적인 keyword가 포함된 내역은 카테고리가 자동으로 분류됩니다. 카테고리를 알 수 없는 내역이 발생한 경우 일단 지출은 기타 지출 카테고리로, 수입은 수입 카테고리로 분류되어 기록되며, 추후 push 알림을 통해 카테고리를 선택하면 선택한 카테고리로 내역이 수정됩니다.
### ✍🏻 시현영상 및 랜딩페이지 링크
[랜딩페이지 바로가기](https://makeus-challenge.notion.site/Flow-cbaffda62eb149a2b0336470ce551456)

## 💡 팀원 소개
| Name | [정민정](https://github.com/Minjungh63) | [이서영](https://github.com/leeseoyoung0822) | [정현정](https://github.com/jhjalison01) | [박재은](https://github.com/nkavay) |
| ------- | :---: | :---: | :---: | :---: |
| Profile | 사진 | 사진 | 사진 | 사진 |
| Role | 역할| 역할 | 역할 | 역할 |

## 💡 사용 기술
- Kotlin
- Android Studio
- RoomDatabase
- retrofit2
- viewpager2
- kakao, naver, google login
