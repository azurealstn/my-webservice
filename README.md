# 게시판 프로젝트
이 프로젝트는 이동욱 개발자님의 책 '스프링 부트와 AWS로 혼자 구현하는 웹 서비스' 를 참고하여 개발하였습니다.

주소 : http://ec2-13-209-201-142.ap-northeast-2.compute.amazonaws.com/

## 개발 환경

- intellij (Community 버전)
- Spring Boot 2.1.7
- Java 1.8
- Gradle 4.10.2

## 게시판 기능
- 포스팅
	- Create
	- Read
	- Update
	- Delete
- 회원
	- 구글 & 네이버 로그인
	- 로그인한 사용자에게 글 작성 권한
	- 본인 작성 글에 대한 권한 관리
	
### 등록/수정/조회 API
총 3개의 클래스 필요

- Request 데이터를 받을 Dto
- API 요청을 받을 Controller
- 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service

### JPA Auditing으로 생성시간/수정시간 자동화

- [BaseTimeClass](https://github.com/azurealstn/my-webservice/blob/master/src/main/java/com/minsu/springboot/domain/BaseTimeEntity.java)

### 스프링 시큐리티 Oauth2 구현
스프링 부트 1.5와 2.0에서 OAuth2 연동 방법이 크게 변경되었지만, 설정 방법에 크게 차이가 없습니다. 이는 `spring-security-oauth2-autoconfigure` 라이브러리 덕분입니다.

다음으로 application.properties 혹은 application.yml 정보가 차이가 있습니다.
(2.0버전으로 넘어오면서 모두 enum으로 대체)
이 정보는 `.gitignore` 에 등록합니다. (비밀 정보)

#### 글 작성 권한
로그인을 한 사람에게만 글을 작성할 수 있는데, 로그인을 한 사람중에서도 `USER` 권한이 있어야만 작성이 가능합니다. 즉, 관리자가 `update user set role = 'USER'`로 권한을 주면 작성이 가능합니다.

![4](https://user-images.githubusercontent.com/55525868/92633973-88e4a900-f30e-11ea-92d0-20bec979cede.PNG)

## 배포
- AWS
	- EC2 (리눅스 서버, 1년간 무료, t2.micro 1대만 사용가능) -> putty.exe 로 접속
		- Java 8 설치
		- 타임존 변경 (한국)
		- 호스트 네임
	- RDS (MariaDB 10.4)
	- S3 (Travis CI에서 생성된 Build 파일 저장)
	- CodeDeploy (AWS의 배포 서비스)
- Travis CI, CD 자동화
	- 깃허브 코드 `push` 되면 자동 배포 진(CI)
	- 무중단 배포 (CD) -> nginx
- 무중단 배포
	- nginx
	- 스프링 부트 jar 2대 사용(8081, 8082)
	- switch.sh 스크립트로 스프링 부트 최신 버전으로 변경
	- 재배포하는 동안 종료되지 않고 동작(real1 리턴), 배포 완료시(real2로 리턴) 

## 로그

```
tail -f /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log
```

CodeDeploy 로그 확인

```
cat ~/app/step3/nohup.out
```

스프링 부트 실행 로그 확인

```
ps -ef | grep java
```

실행되고 있는 애플리케이션 확인

---

## 결과 페이지

![5](https://user-images.githubusercontent.com/55525868/92637058-4f626c80-f313-11ea-8bab-13e159701220.PNG)

메인 화면

![6](https://user-images.githubusercontent.com/55525868/92637064-512c3000-f313-11ea-9564-6282a3b5f13c.PNG)

등록 화면

![7](https://user-images.githubusercontent.com/55525868/92637066-525d5d00-f313-11ea-9086-46a0bf764ab6.PNG)

수정 화면

---

저의 블로그입니다.
https://azurealstn.tistory.com/
