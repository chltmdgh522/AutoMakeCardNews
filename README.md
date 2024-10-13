[![Deploy to EC2](https://github.com/chltmdgh522/AutoMakeCardNews/actions/workflows/deploy.yml/badge.svg)](https://github.com/chltmdgh522/AutoMakeCardNews/actions/workflows/deploy.yml)

# 📰 AutoMakeCardNews
##### 🏆 캡스톤디자인 및 졸업 프로젝트 작품

### 📜 Contents
 1. [Overview](#-overview)
 2. [서비스 화면](#-서비스-화면)
 3. [주요 기능](#-주요-기능)
 4. [개발 환경](#%EF%B8%8F-개발-환경)
 5. [시스템 아키텍처](#-시스템-아키텍처)
 6. [기술 특이점](#-기술-특이점)
 7. [기획 및 설계 산출물](#-기획-및-설계-산출물)
 8. [Conventions](#-conventions)
 9. [팀원 소개](#-팀원-소개)
 
## ✨ Overview

> 현대 사회에서 정보의 전달 속도와 방식은 매우 중요한 요소로 자리 잡았다. 특히, 소셜 미디어와 인터넷 플랫폼의 발달로 시각적 콘텐츠의 수요가 급증하고 있다. 이러한 환경 속에 카드뉴스는 간결하고 직관적인 정보 전달 방식으로 인기를 얻고 있으나, 고품질의 카드뉴스 제작은 시간과 노력이 많이 소요되는 작업이다. 따라서 본 프로젝트에서는 생성형 AI를 활용하여 자동으로 카드뉴스를 제작하는 시스템을 개발하였다. 본 애플리케이션은 사용자의 텍스트를 기반으로 음성(TTS)이 포함된 카드뉴스를 생성할 수 있을뿐더러 생성된 이미지를 바탕으로 편집을 할 수 있다. 또한 뉴스 요약 모델을 개발하여 사용자가 원하는 뉴스 요약 정보를 쉽게 얻을 수 있다. 이 외에도, 취업 및 다양한 카테고리에서 정보를 공유하는 커뮤니티 서비스 등을 개발하였다.

## ✨ AMCN의 배포 사이트
##### 🏆 [사이트](https://amcn.kr/)



## ✨ AMCN의 소통 플랫폼 
##### 🏆 [노션](https://www.notion.so/ae9aab2290414d5ebc4510c922177e54)
##### 🏆 [디스코드]


## 👀 서비스 화면
### ✨ `모바일(아이폰 12 Pro 기준 max-width:480px)` 지원X


### 홈
- 닌텐도 화면 적용
- 화면이 작아질 수록 DS - GBA - GDC 로 구현했다.
- Start 버튼을 통해 캐릭터리스트로 갈 수 있다.
- 로그인 및 회원가입 버튼을 누르수 있다
  
<div>
  <img src="https://github.com/user-attachments/assets/ece63fc2-ca98-441d-9a4c-ddc80e4b7c6b" width="75%"/>
  <img src="https://github.com/user-attachments/assets/ff5d977d-9281-4e4a-b1ce-020682c30086" width="20%"/>
</div>


### 회원가입 & 로그인 & 로그아웃
- `네이버, 카카오 구글 소셜 로그인` 및 유저 회원가입/로그인
- 로그인을 하면 캐릭터 리스트로 넘어간다.
<div>
<img src="https://github.com/user-attachments/assets/0e57d0ce-0ad5-4dac-9ff1-0c662a67d439" width="20%"/>
<img src="https://github.com/user-attachments/assets/b97d5b9e-feef-414d-9360-d90527ecb66f" width="75%">
</div>


### 마이페이지
- `프로필 이미지 변경`
- `닉넴임 변경`
<div>
<img src="https://github.com/user-attachments/assets/34053b7f-a080-4a15-9e72-bcdf90e28dd0" width="75%">
<img src="https://github.com/user-attachments/assets/5e5e1139-14d8-4a05-86ca-0d2a9ad82018" width="20%">
</div>


### 캐릭터 리스트
- 생성, 삭제, 편집 버튼을 이용해 캐릭터 관리 
<div>
<img src="https://github.com/user-attachments/assets/b153ded0-f9ce-4879-a4b9-a610c65515ce" width="20%">
<img src="https://github.com/user-attachments/assets/9a6c0d05-4b62-4656-bb69-3878b0e4e9e7" width="75%">
</div>



### 캐릭터 생성
- 5개의 캐릭터 중 하나를 뽑는다. 하지만 이미 생성된 캐릭터는 못 고른다.
- 캐릭터의 이름과 선택한 이유를 적으면 생성이 된다.
<div>
<img src="https://github.com/user-attachments/assets/30e88da7-8b5b-40be-b4dc-1df85bc1b333" width="75%">
<img src="https://github.com/user-attachments/assets/80557388-2081-4a7a-8b18-91fce71355fa" width="20%">
</div>


### 휴지통
- 캐릭터 리스트에서 버린 캐릭터들을 볼 수 있다.
- 여기서는 복구 및 영구 삭제를 할 수 있다. 
<div>
<img src="https://github.com/user-attachments/assets/2f525651-67e9-42da-95f6-448dc40eba47" width="20%">
<img src="https://github.com/user-attachments/assets/2c5642fe-11b9-421d-8891-6d827256086f" width="75%">
</div>


### 챗봇 
- 음성 인식을 통해 대화를 할 수 있다.
- 영상통화 버전에서는 TTS 구현되어있다.
- Ajax를 통해 실시간으로 캐릭터와 채팅! 
<div>
<img src="https://github.com/user-attachments/assets/3d1a2ca3-fcb7-49c9-b991-172636f5c0c2" width="75%"/>
<img src="https://github.com/user-attachments/assets/2afcf85a-804e-4647-819b-3a02022202e8" width="20%"/>
</div>

<div>
<img src="https://github.com/user-attachments/assets/e8f38e20-c9c6-40e6-847e-9f00ebbedbd8" width="20%"/>
<img src="https://github.com/user-attachments/assets/76c34384-8782-4794-b18c-1a31cd966f6f" width="75%"/>
</div>


### 피드백
- 별점 및 리뷰를 통해 해당 에플리케이션의 평가를 알 수 있다.
- 피드백을 통해 추후 계속 업데이트 할 예정이다.
 <div>
<img src="https://github.com/user-attachments/assets/27097924-b693-4c12-aca7-93dc3a93b3c2" width="75%">
<img src="https://github.com/user-attachments/assets/444327ce-8070-4f2b-9b6d-242c0661db92" width="20%">
</div>



### 감정 로그
- 5개의 캐릭터들과 챗봇을 통해 나온 결과를 보여준다.
- 주간 및 누적 기능이 있어 감저의 정보를 쉽게 파악할 수 있다.
<div>
<img src="https://github.com/user-attachments/assets/9aa369fc-a257-4693-a5ce-6772540210e2" width="20%">
<img src="https://github.com/user-attachments/assets/44cad03c-362b-4fc8-a86a-7e844ceb8c20" width="75%">
</div>

  
## ✨ 주요 기능 (세부 기래 참고!!)

- `소셜 로그인`
	- 분노, 기쁨, 불안, 두려움, 불안 총 5개의 캐릭터를 생성할 수 있다. 
  	- 생성된 캐릭터를 수정 및 휴지통에 버릴 수 있다. 
	- 휴지통에 버려진 캐릭터는 다시 복구 할 수 있고 영원히 삭제할 수 있다.
   
- `커뮤니티 Quill API 도입`
	- 분노, 기쁨, 불안, 두려움, 불안 총 5개의 캐릭터를 생성할 수 있다. 
  - 생성된 캐릭터를 수정 및 휴지통에 버릴 수 있다. 
	- 휴지통에 버려진 캐릭터는 다시 복구 할 수 있고 영원히 삭제할 수 있다.

- `뉴스 기사 크롤링`
	- 분노, 기쁨, 불안, 두려움, 불안 총 5개의 캐릭터를 생성할 수 있다. 
  - 생성된 캐릭터를 수정 및 휴지통에 버릴 수 있다. 
	- 휴지통에 버려진 캐릭터는 다시 복구 할 수 있고 영원히 삭제할 수 있다.

- `키워드 도출 및 주요 문장 추출`
	- 분노, 기쁨, 불안, 두려움, 불안 총 5개의 캐릭터를 생성할 수 있다. 
  - 생성된 캐릭터를 수정 및 휴지통에 버릴 수 있다. 
	- 휴지통에 버려진 캐릭터는 다시 복구 할 수 있고 영원히 삭제할 수 있다.
 
- `뉴스 요약 모델`
	- 총 5개의 캐릭터마다 프롬프트를 설계한다. 
	- 프롬프트에 이전 대화를 기억할 수 있도록 DB에서 해당 데이터를 찾아와 프롬프트에 넘겨준다.
  
- `DALL-E 모델 연동`
	- Open AI를 통해 API와 연결한뒤 사용자 답변에 따른 AI 답변이 제공이 된다.
	- Ajax를 통해 실시간으로 대화가 진행되며 시간 마지막 답변들도 실시간으로 추가가 된다.
   
- `카드뉴스 이미지 편집`
	- Open AI를 통해 API와 연결한뒤 사용자 답변에 따른 AI 답변이 제공이 된다.
	- Ajax를 통해 실시간으로 대화가 진행되며 시간 마지막 답변들도 실시간으로 추가가 된다.   
   
- `카드뉴스 JSON 편집`
	- 영상 통화 화면에 넘어간뒤 사용자가 답하면 AI 답변이 TTS로 제공이 된다. 
	- 여러 목소리 TTS 기능이 설정이 되어있다.

- `동영상 다운로드`
	- 사용자가 각각의 캐릭터마다 대화한 기록을 수치화하여 로그로 보여준다. 
	- 누적 및 주간이 있어 해당 감정 로그를 확인할 수 있다.

- `카드뉴스 포크`
	- 사용자가 각각의 캐릭터마다 대화한 기록을 수치화하여 로그로 보여준다. 
	- 누적 및 주간이 있어 해당 감정 로그를 확인할 수 있다.  

- `카드뉴스 휴지통`
	- 사용자가 각각의 캐릭터마다 대화한 기록을 수치화하여 로그로 보여준다. 
	- 누적 및 주간이 있어 해당 감정 로그를 확인할 수 있다.

- `자동 파일 삭제 시스템`
	- 사용자들이 서비스를 이용하고 나서 후기를 올리 수 있는 공간이다.
   
- `Spring Batch 및 Redis 적용`
	- 네이버, 구글, 카카오 소셜 로그인 기능을 도입했다. 
	- 비밀번호 재설정 기능 도입했다.
   
- `AWS 서버 배포`
	- 비동기적으로 처리  
	- 병렬로 작업을 처리함으로써 서버의 부하를 크게 줄임 
   
- `사용자 친화적 UI`
	- 반응형 모바일 뷰 지원
	- 색다른 3D CSS 도입

## 🖥️ 개발 환경

**Management Tool**
- 형상 관리 : Git
- 커뮤니케이션 : Discord, Notion
- 디자인 : Figma

**🐳 Backend**
- Java `21`
- Python `3.11.0`
- Spring Framework `3.2.4`
- pipenv or poetry (패키지 관리 도구)
- MySQL  `8.0.4`
- Swagger `2.6.0`
- Batch `5,x`
- Redis `7.x`
- Thymeleaf
- Jpa

**🤖 AI**
- OpenAI
- KoBart 
  

**🦊 Frontend**
- lang: HTML5, CSS3, JAVASCRIPT

**🖼️ Gradle**
```
implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3'
	implementation 'org.springframework.session:spring-session-core'
	implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
	implementation 'org.springframework.boot:spring-boot-starter-mail:3.2.5'
	implementation 'org.springframework.boot:spring-boot-starter-json'
	implementation 'org.python:jython-standalone:2.7.2'
	implementation 'com.googlecode.json-simple:json-simple:1.1.1'
	implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
	runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
	runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
	implementation 'com.theokanning.openai-gpt3-java:client:0.10.0'
	implementation 'com.theokanning.openai-gpt3-java:service:0.10.0'
	implementation 'com.stripe:stripe-java:23.2.0'
	implementation 'javax.xml.bind:jaxb-api:2.3.1'
	implementation 'sh.platform:config:2.2.2'
	implementation 'commons-io:commons-io:2.11.0'
	implementation 'com.madgag:animated-gif-lib:1.4'
	implementation 'io.github.cdimascio:java-dotenv:5.2.2'
	implementation 'io.github.cdimascio:dotenv-java:2.2.0'
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	implementation 'org.json:json:20211205'
	implementation 'org.springframework.boot:spring-boot-starter-websocket'
	implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0'
```

**🗝️ API**
- [Web Speech API](https://developer.mozilla.org/en-US/docs/Web/API/Web_Speech_API)
- [Quill_API](https://quilljs.com/docs/api/)
   

**🗂️ DB**
- MySQL `8.0.30`

**🌐 Server**
- AWS EC2 (Ubuntu `20.04`)
- Nginx `1.23` (Reverse Proxy)
- HTTPS (TLS `1.2`)

**🔨 IDE**
- IntellJ `2023.2`

## 💫 시스템 아키텍처

![image](https://github.com/user-attachments/assets/265a7eca-8434-411e-841d-2d1887dabc82)


## ✨ 기술 특이점

- **캐릭터별 고유 프롬프트**를 사용하여 사용자 경험을 맞춤화
- 사용자가 선택한 캐릭터에 따라 다른 프롬프트가 적용되어 대화 진행
- 각 캐릭터는 고유한 성격과 대화 스타일을 가지고 있으며, 감정 분석 결과에 따라 다양한 반응을 생성
- OpenAI의 GPT 모델을 활용하여 실시간 감정 분석 및 캐릭터 기반 응답 제공
- Celery를 통해 대규모 사용자 요청을 효율적으로 비동기 처리하여 서버 성능 최적화


# 📂 기획 및 설계 산출물

### [💭 요구사항 정의 및 기능 명세](https://www.notion.so/Feelbuddy-6330c0b568714b6ab0a4659d635ad782)

![image](https://github.com/user-attachments/assets/608d90da-08f0-4e0e-bffe-09c32e2be53f)


### [🎨 화면 설계서](https://www.figma.com/design/2MIHENt866R7jjAyDBO3lp/Untitled?node-id=0-1)

![image](https://github.com/user-attachments/assets/ce15a380-b42c-49c6-906a-d86d37250992)


### [✨ ER Diagram](https://www.erdcloud.com/d/p9ocstx53DrdNzupt)

![image](https://github.com/user-attachments/assets/135eac39-5e08-42a9-b97f-6bf5afe6fdf4)


# 💞 팀원 소개
##### ❤️‍🔥 FEELBUDDY를 개발한 `피로그래밍 21기` 팀원들을 소개합니다!

| **[나예원](https://github.com/Anna-user)** | **[최승호](https://github.com/chltmdgh522)** | **[전진명](https://github.com/JNMYNG)** | **[이민수](https://github.com/msoolee)** |
| :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: |
| <img src="https://github.com/user-attachments/assets/7f6428b7-e110-40ed-98b1-be6e595c9f79" width="400"> | <img src="https://github.com/user-attachments/assets/e792dfc6-e2a7-4b42-b5a5-27672d4df6c7" width="400"> | <img src="https://github.com/user-attachments/assets/aec44d20-60ee-4411-9a6f-8dba81ff5403" width="400"> | <img src="https://github.com/user-attachments/assets/9e92ceed-574a-4bbb-80ff-78ea2587f4c2" width="400"> |
| Leader & Frontend & Designer | Backend & AI | Frontend & Backend |  Backend |




## 😃 팀원 역할

- **나예원**
  - 팀장, 기획, 캐릭터 및 로고 디자인, 프론트, 와이어프레임 설계, 3D CSS 설계, AI 프롬프트 설계
- **최승호**
  - ERD 설계, 챗봇 기능, 캐릭터 관리 기능, REST API 설계, AWS 서버 배포 및 CICD 설정
- **전진명**
  - 회원관리, 마이페이지, 피드백, 감정 로그 
- **이민수**
  - 감성 글귀, User 닉네임 랜덤 기능, 캐릭터 생성관리 기능, 인스타 광고, flutter webview 

