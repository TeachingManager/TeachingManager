# TeachingManager
홍익대학교 졸업프로젝트

잘부탁드립니다!!
멤버 소개
팀장 : 김승찬
팀원 : 김동겸, 양현

프로젝트 명 :  티칭매니저
프로젝트 설명 : 학원생 관리 웹사이트

# 역할 & 목표:

김승찬 : 
- 프론트엔드의 모든 페이지 구현
- (Student, Schedule, Teacher, Institute,Enroll, Attend, Fee, Lecture) 페이지 구현
- Login 페이지, 비밀번호 찾기 페이지 등 유저 인증/인가 관련 페이지 구현
- React.js 을 이용한 비동기식 처리
- 페이지 UI/UX 디자인
- 일정의 달력등 각 페이지에 맞는 다양한 라이브러리 추가
- 유저 사용 시나리오 작성


김동겸 : 5개 API
- (Lecture) CRUD API 
- Jenkins 을 이용한 CI/CD


양현 : 48개 API
- (Student, Schedule, Teacher, Institute, Enroll, Attend, Fee) CRUD & 인증 & 메일전송 API 작성 
- DB 초기 세팅 & 등록
- 유저 타입별로 도메인 별도 구현
- 유저 2개 타입 인증을 위한  JWT 필터와 CustomAuthenticationProvider 구현
- 유저 초기인증, 비밀번호 재설정, 잠금 해제, 초대 등을 위한 이메일 전송 라이브러리 추가 (javaMail API)
- 로그인을 위한 JWT 토큰 +  JWE 암호화 적용
- Authentication, Authorization 설정
- 인증 실패시 로직 추가
- 유저 사용 시나리오 작성
- 리캡챠 적용
- api 명세서 작성
- 구글 , Naver OAuth2.0 적용




깃허브 페이지 - develop 브랜치
https://github.com/TeachingManager/TeachingManager


MYSQL 에서 ON DELETE 설정해주어야함.
