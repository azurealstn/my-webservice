# 게시판 프로젝트
이 프로젝트는 이동욱 개발자님의 책 '스프링 부트와 AWS로 혼자 구현하는 웹 서비스' 를 참고하여 개발하였습니다.
주소 : http://ec2-13-209-201-142.ap-northeast-2.compute.amazonaws.com/

## 개발 환경

- intellij (Community 버전)
- Spring Boot 2.1.7
- java 1.8
- junit4
- JPA
- MariaDB 10.4
- AWS EC2

## 기능
- 포스팅
	- Create
	- Read
	- Update
	- Delete
- 회원
	- 구글 & 네이버 로그인
	- 로그인한 사용자에게 글 작성 권한
	- 로그아웃

## 배포
- AWS
	- EC2 (리눅스 서버, 1년간 무료, t2.micro 1대만 사용가능) -> putty.exe 로 접속
	- RDS (MariaDB 10.4)
	- S3 (Travis CI에서 생성된 Build 파일 저장)
- Travis CI 자동화
	- 깃허브 코드 `push` 되면 자동 배포 진행
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

![캡처](https://user-images.githubusercontent.com/55525868/92077770-0765b600-edf8-11ea-8f8f-2ead62f143f2.PNG)

메인 화면

![캡처1](https://user-images.githubusercontent.com/55525868/92077777-0896e300-edf8-11ea-8945-98b9750ffc3c.PNG)

등록 화면

![캡처](https://user-images.githubusercontent.com/55525868/92077473-78589e00-edf7-11ea-9a65-18524d7a45e2.PNG)

수정 화면
