# 맛동산 프로젝트

해커톤을 통해 팀원과 함께 프로젝트를 기획, 개발하였습니다. 맛동산 프로젝트는 나만의 맛집을 한 곳에 저장할 수 있는 웹앱입니다.

여러 식당들에 대한 정보를 맛동산이라는 페이지의 한 공간에 저장하여 관리할 수 있으며 유저들과 공유 및 교류할 수 있는 웹사이트입니다.


## 왜 이 프로젝트를 만들었는가?

방문한 여러 맛집들을 네이버, 카카오 등에 저장할 수 있지만, 막상 찾을 때 내가 어디서 이 맛집을 찾았는지 헷갈리곤 합니다.

이러한 문제점을 해결해보고자 지도뷰를 맛집을 한 곳에서 볼 수 있는 서비스를 기획하게 되었습니다.

평소 자주가는 식당들을 검색하고 북마크하여 효과적으로 개인의 맛집을 관리할 수 있습니다.

### Teck Stack
- Java
- Spring, Spring Boot
- [Spring Security](https://spring.io/projects/spring-security)
- [Thymeleaf](https://www.thymeleaf.org/)
- [MySQL](https://www.mysql.com/)

### Library
- [jQuery v3.5.1](https://jquery.com/)
- [Kakao Login API](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)
- [Kakao Map API](https://apis.map.kakao.com/web/sample/)
- [Lombok](https://projectlombok.org/download)
- [queryDsl](http://querydsl.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

### Database ERD
<img width="577" alt="스크린샷 2023-01-05 23 54 49" src="https://user-images.githubusercontent.com/96028167/210809547-de2dddb3-0942-438c-a9ce-f3d3cb6bbf50.png">

- 작성한 게시글, 북마크한 식당을 프로필에서 확인할 수 있습니다.
<img width="919" alt="스크린샷 2023-01-05 23 51 09" src="https://user-images.githubusercontent.com/96028167/210808568-0ff310e1-8143-4afa-aa4e-f738f8e94ec6.png">



- 카테고리별로 나누어 식당들을 저장하여 현재 위치 중심으로 지도에서 북마킹한 식당들을 확인할 수 있습니다.
<img width="922" alt="스크린샷 2023-01-05 22 45 09" src="https://user-images.githubusercontent.com/96028167/210794372-183d83ba-ad37-4cdd-a9a6-862c5d77541c.png">



- 지도를 통해 식당 검색이 가능합니다
<img width="1104" alt="스크린샷 2023-01-05 22 46 03" src="https://user-images.githubusercontent.com/96028167/210794565-3401f90a-bb93-4818-a53b-8ef12772a29c.png">



- 실시간으로 올라오는 맛집식당들을 확인 및 댓글로 유저간 의견공유를 할 수 있습니다.
<img width="1207" alt="스크린샷 2023-01-05 23 43 16" src="https://user-images.githubusercontent.com/96028167/210806941-222ddf91-7724-4b37-8b1a-6a2608dcb2c3.png">





### <추후 진행 계획>
팀프로젝트 후 test코드 작성, 많은 레거시 코드를 걷어내며 배포, 버그fix 및 리팩토링을 진행하였습니다.
하지만 현재 프로젝트에서 걷어내야 할 코드가 아직도 너무 많다는 판단이 들었고
전체적으로 더 객체지향적이고 클린한 코드를 만들기 위해 관련된 학습 부족한 부분을 보충하며 프로젝트를 진행할 생각입니다.



<!-- 
### DevOps
- [AWS EC2](https://aws.amazon.com/ko/ec2/)
- [AWS RDS](https://aws.amazon.com/ko/rds/)
- [Docker](https://www.docker.com/)
- [GitHub Actions](https://github.com/features/actions) -->

