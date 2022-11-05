# 맛동산 프로젝트

해커톤을 통해 팀원과 함께 프로젝트를 기획, 개발하였습니다.

# 맛집 저장 웹사이트

맛동산 프로젝트는 나만의 맛집을 한 곳에 저장할 수 있는 웹앱입니다.

여러 식당들에 대한 정보를 맛동산이라는 페이지의 한 공간에 저장하여 관리할 수 있으며 유저들과 공유 및 교류할 수 있는 웹사이트입니다.


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
- 배포 파이프라인 구성 및 AWS EC2를 통한 배포(운영중) https://flavourtown.site (크롬환경) 
- 성능 테스트(진행중) (Jmeter)
- CI/CD 구축(진행중)
