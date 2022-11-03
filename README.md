# 맛동산 프로젝트

`멋쟁이사자처럼 백엔드스쿨 1기 해커톤 프로젝트`를 통해 기획하고, 개발되었습니다.

## 개발자

### [가준영](https://github.com/Jwhyee)
- 프로젝트 팀장
- `Spring Security`를 이용한 `Account`, `Member` 회원 도메인 개발
- `Profile`, `Post`도메인 개발
- `Post`, `Place`, `Reply`, `Likes` 등 전체적인 기능 보조 개발

### [남의영](https://github.com/young0264)
- `Reply`, `Like`, `Post` 도메인 개발
- 댓글, 과 관련된 `CRUD` 기능 개발
- 댓글 관련 비동기 방식(JavaScript Ajax) 개발
- AWS EC2를 사용하여 배포 및 운영 경험

### [최수용](https://github.com/Choisooyoung98)
- `Favorite`, `Post` 북마크 도메인 개발
- 북마크 폴더와 관련된 `CRUD` 기능 개발
- `Place`와 연관된 북마크 기능을 비동기 방식(JavaScript Ajax) 개발 

### [왕종휘](https://github.com/woowang789)
- `Place` 도메인 개발
- `Kakao Map Api`를 활용하여 `Place` 기능 개발

# 맛집 저장 웹사이트

맛동산 프로젝트는 나만의 맛집을 한 곳에 저장할 수 있는 웹앱입니다.

네이버, 카카오, 인스타그램 등 흩어져있는 나만의 맛집을 한 곳에 모아 저장할 수 있는 페이지입니다.


## 왜 이 프로젝트를 만들었는가?

우리는 보통 맛집을 여러 SNS에서 접하고 방문하게 됩니다.

이런 맛집들을 네이버, 카카오 등에 저장할 수 있지만, 막상 찾을 때 내가 어디서 이 맛집을 찾았는지 헷갈리곤 합니다.

이러한 문제점을 해결해보고자 맛집을 한 곳에서 볼 수 있는 서비스를 기획하게 되었습니다.

## 실행하기 위해서 필요한 것

Chromium 기반의 웹에서 모두 작동하며, 위치 기반의 서비스를 사용합니다.

## Tech Stack & Library

### Teck Stack
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Thymeleaf](https://www.thymeleaf.org/)
- [MySQL](https://www.mysql.com/)

### Library
- [jQuery v3.5.1](https://jquery.com/)
- [Kakao Map API](https://apis.map.kakao.com/web/sample/)
- [Lombok](https://projectlombok.org/download)
- [queryDsl](http://querydsl.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
<!-- 
### DevOps
- [AWS EC2](https://aws.amazon.com/ko/ec2/)
- [AWS RDS](https://aws.amazon.com/ko/rds/)
- [Docker](https://www.docker.com/)
- [GitHub Actions](https://github.com/features/actions) -->

## 10월부터 진행될 상황들 요약
- ~9월까지 구현된 기능, 배포시 버그들 리팩터링(완료)
- 배포 파이프라인 구성 및 AWS EC2를 통한 배포(운영중) https://flavourtown.site (크롬환경, wifi 5G 혹은 모바일 데이터를 사용해야 접속이 원활합니다.) 
- 성능 테스트
- CI/CD 구축
