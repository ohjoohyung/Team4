# Bodiary
Bodiary는 약 한달여간 준비한 비트캠프 4조의 파이널 프로젝트로, 20년 2월 17일 부터 20년 7월 31일까지 약 6개월의 시간동안 배운 것들을 녹여내어 비전공자 6명의 친구들과 함께 만든 프로젝트입니다.
스프링 기반 + 타임리프를 적용하여 개발 진행하였으며, 실제 코딩 기간 18일여 간 만들어낸 결과물임을 고려하여 봐주시면 감사하겠습니다.


## 프로젝트 개요
Bodiary는 자신의 운동루틴과 식단을 쉽게 작성하여 관리하고, 체계적으로 자신의 변화를 관리할 수 있는 운동 일지로서의 기능과, 자신의 정보를 남들과 공유하고, 다른사람들의 좋은 운동,식단 정보를 얻고싶은 사람들을 위한 자기계발 커뮤니티 사이트가 되고자합니다.

## Target

Main Target : 자기 관리에 대한 니즈를 가지고 있으며, 이를 기록하며 자신의 변화를 체계적으로 관리하고 싶은 사용자.

Sub Target : 운동 및 식단 정보에 대한 갈증을 가지고 있고, 광고성 또는 원론적인 이야기가 아닌 실제 집단 내에서 교류되는 생생한 정보에 대한 니즈가 있는 사용자.

## URL : 

# 기능 별 기술 소개

## 1. 회원가입 및 로그인, 탈퇴

![https://user-images.githubusercontent.com/61967128/90310514-00503400-df2d-11ea-8086-4591a3cb5286.png](https://user-images.githubusercontent.com/61967128/90310514-00503400-df2d-11ea-8086-4591a3cb5286.png)

- 일반 회원가입시 Trigger를 통해 준회원으로 role이 insert되며 Spring Security를 적용하여 권한에 따른 페이지 접근권한이 상이하게 됩니다.
- 처음 회원가입 후 login페이지로 유도되며, 로그인시 정보 수정을 유도하여 정상적으로 유저의 정보가 업데이트 되었을 경우 정회원으로 role 권한을 업데이트하여 사이트를 정상적으로 이용할 수 있습니다.

![https://user-images.githubusercontent.com/61967128/90310534-368db380-df2d-11ea-9585-e46bd7dbe978.png](https://user-images.githubusercontent.com/61967128/90310534-368db380-df2d-11ea-9585-e46bd7dbe978.png)

- 로그인 화면에서 소셜 로그인이 가능하며, 가입하지 않은 소셜 이메일일 경우 회원가입 페이지로 유도하며 기가입자인 경우 로그인처리되어 각각의 소셜에 맞는 callback 함수를 통해 session을 등록하고 main 페이지로 유도합니다.
- 잘못된 로그인 시도 시 LoginFailureHandler에서 정의되는 메세지에 따라 각각에 맞는 에러 메세지를 함께 출력합니다.
- 비밀번호는 BCryptPasswordEncoder 를 통해 암호화하여 관리되고있으며, 유저가 입력한 비밀번호와 DB에 암호화하여 등록된 비밀번호의 일치여부만 확인하여 일치할 경우 LoginSuccessHandler를 통해 Session에 등록됩니다.

![https://user-images.githubusercontent.com/61967128/90310539-4c9b7400-df2d-11ea-85f6-63c4532867b8.png](https://user-images.githubusercontent.com/61967128/90310539-4c9b7400-df2d-11ea-85f6-63c4532867b8.png)

- 탈퇴하기 시 유저의 개인정보는 `N` 또는 `0` 으로 변경하고 `user_deletedate` 컬럼에 현재 시간을 기록한 후, enabled를 `0` 으로 바꿔주어 업데이트하여 로그인을 막았습니다. 이 후 로그인 시도시, 탈퇴한 회원이라는 에러메세지와 함께 한 달 후 재가입이 가능함을 안내합니다.
- 스케줄링을 통해 user_deletedate에 기록된 날짜로부터 한달이 지난 시점에 실제로 유저데이터가 삭제되며, 유저와 관련한 다른 데이터 또한 cascade를 통해 일괄 삭제합니다.

## 2. 메인과 공지사항

![https://user-images.githubusercontent.com/61967128/90310550-6dfc6000-df2d-11ea-8dd8-a603bcd99b62.png](https://user-images.githubusercontent.com/61967128/90310550-6dfc6000-df2d-11ea-8dd8-a603bcd99b62.png)

- 오늘의 루틴 : 오늘 등록된 루틴 자랑게시판의 게시글 중 가장 최신 게시글 2개를 보여줍니다. 만약 없을 경우 날짜유무와 관계없이 게시글 중 최신 게시글을 보여줍니다.
- 자유게시판 : 하루 중 가장 조회수가 높은 게시글 8개를 보여줍니다.

## 3. 일지 관리

![https://user-images.githubusercontent.com/61967128/90310563-866c7a80-df2d-11ea-8872-ee7b91c04597.png](https://user-images.githubusercontent.com/61967128/90310563-866c7a80-df2d-11ea-8872-ee7b91c04597.png)

- 자신의 몸무게, 컨디션, 식단, 루틴 등의 항목을 적어 오늘의 일지를 작성, 열람, 수정, 삭제할 수 있습니다.
- 일지 상세보기에서 자신의 몸무게와 사이트 내에 자신과 키가 같은 유저들의 평균 몸무게 그리고 하루 칼로리 섭취량을 통계 그래프를 통해 직관적으로 볼 수 있습니다.
- 일지 작성과 수정은 MySQL의 저장 프로시저(Stored Procedure)를 통해 실행이 됩니다.

## 4. 운동 검색

![https://user-images.githubusercontent.com/61967128/90310572-9ab07780-df2d-11ea-913c-d5b2bb04f719.png](https://user-images.githubusercontent.com/61967128/90310572-9ab07780-df2d-11ea-913c-d5b2bb04f719.png)

- MyBatis의 동적 쿼리(Dynamic Query)를 이용한 필터로 원하는 운동을 찾을 수 있습니다.
- 원하는 운동을 추가시킨 루틴을 만들어 일지 작성 및 루틴 자랑 게시판에 사용할 수 있습니다.

## 5. 루틴 자랑 게시판

![Untitled (6)](https://user-images.githubusercontent.com/61967128/90310589-d8ad9b80-df2d-11ea-8ef0-6158d84bb16f.png)

- 자신의 루틴을 공유할 수 있는 이미지 게시판으로 Masonry 라이브러리를 통해 핀터레스트식 레이아웃을 구현할 수 있도록 만들었습니다.
- 조회수가 높은 게시글 4개는 상단에서 HOT 게시글로 노출됩니다.

## 6. 자유게시판

![Untitled (7)](https://user-images.githubusercontent.com/61967128/90310588-d77c6e80-df2d-11ea-863b-4df44185b41d.png)

- 카테고리별 글 등록이 가능합니다. 자유 / 팁 / 질문 카테고리가 존재합니다.
- 조회수가 높은 게시글은 상단에서 HOT 게시글로 노출됩니다.
- 댓글을 작성하고, 수정하고, 삭제할 수 있습니다.

## 7. 채팅

![Untitled (8)](https://user-images.githubusercontent.com/61967128/90310586-d2b7ba80-df2d-11ea-98ec-d4da59b7f33c.png)

- 웹소켓을 이용해 구현한 채팅방으로 채팅방에 입장한 유저들끼리 채팅을 할 수 있습니다.
- 채팅방은 회원 누구나 만들 수 있으며 인원 수는 2명에서 10명까지 제한이 가능합니다. 비밀번호를 통해 비밀방도 만들 수 있고 방장의 경우 방 삭제를 할 수 있습니다.

## 8. 관리자 페이지

![Untitled (9)](https://user-images.githubusercontent.com/61967128/90310620-39d56f00-df2e-11ea-95bf-d41b462ad08d.png)

- 관리자페이지 대시보드에서 오늘 가입한 회원수 / 회원 남녀 성비 / 오늘 일지 작성 총 개수 / 탈퇴 회원수 / 남 녀 누적 회원수 / 가입자수 변동 추이 등을 통계로 확인할 수 있습니다.
- 바디어리 관리 탭에서 관리자는 운동데이터 관리 페이지를 통해 운동 항목에 대한 CRUD가 가능합니다.
- 바디어리 관리 탭에서 관리자는 유저 관리 페이지를 통해 유저의 정보를 확인하고 Role권한을 변경하거나 휴면계정을 해지할 수 있습니다.

![Untitled (10)](https://user-images.githubusercontent.com/61967128/90310619-393cd880-df2e-11ea-94b4-4e99a3e546a4.png)

![Untitled (11)](https://user-images.githubusercontent.com/61967128/90310618-38a44200-df2e-11ea-9050-f9d455a45014.png)

- 문의 게시판을 통해 유저는 관리자에게 문의를 할 수 있고 관리자는 관리자페이지에서 문의 답변을 할 수 있습니다.
- 문의에 대한 알람은 웹소켓을 이용하여 구현하였으며 각 문의가 도착할 때 마다 알람을 통해 확인할 수 있도록 만들었습니다.

# PL/SQL 
![Untitled (12)](https://user-images.githubusercontent.com/61967128/90310617-38a44200-df2e-11ea-8d14-f50f5ca884d1.png)
# MyBatis 
![Untitled (13)](https://user-images.githubusercontent.com/61967128/90310616-380bab80-df2e-11ea-8206-c81bdb0ead57.png)
---

# 형상관리 및 도구 

프로젝트의 변경사항을 체계적으로 관리하기 위해 Git을 사용하였습니다. 또 이를 더욱 직관적인 GUI 환경에서 사용하고자 SourceTree 툴을 이용했습니다.
![Untitled (14)](https://user-images.githubusercontent.com/61967128/90310615-37731500-df2e-11ea-86dd-257aa2e67c95.png)
# 이슈 관리 

프로젝트의 이슈관리는 Notion을 통해 진행하였습니다.  하나의 페이지를 만들고 해당 페이지에서 각자 발생한 이슈를 기록하고 해결 방법 등을 작성할 수 있도록 만들었습니다.
![Untitled (15)](https://user-images.githubusercontent.com/61967128/90310614-36da7e80-df2e-11ea-9812-de3a22166a7b.png)
# 구글 애널리틱스에 의한 추적

![Untitled (16)](https://user-images.githubusercontent.com/61967128/90310612-3641e800-df2e-11ea-9813-aabc93da3990.png)
![Untitled (17)](https://user-images.githubusercontent.com/61967128/90310608-3215ca80-df2e-11ea-818a-ae967f730fbf.png)

사이트 방문자 추적을 위한 구글 애널리틱스를 적용해보았습니다.
로직을 만들어 직접 구현하기에 약 18일의 짧은 일정 등 여러 조건들을 고려할 때, 현재로서는 좋은 라이브러리를 사용하는 것이 좀 더 효율적일 것으로 판단 했으며, 2차적으로는 애널리틱스를 통해 구글의 데이터 수집 및 분석방식을 사용자 입장에서 체험해봄으로써 이 후 통계 데이터를 활용하는 방식에 대한 저희들의 식견이 좀 더 넓어질 것을 기대하였습니다.
