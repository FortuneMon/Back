# 🔧 설치 및 실행 방법

- FortuneMon은 프론트엔드(React)와 백엔드(Spring Boot)로 구성된 웹 애플리케이션입니다.
- 로컬에서 실행하려면 아래 단계에 따라 설정하세요.


## ✅ 사전 준비

- Node.js 18 이상
- Java 17 이상
- MySQL 8 이상
- OpenAI API Key
- Redis 호스팅 정보 (ex. Redis Cloud)


## 📁 1. 저장소 클론

```bash
git clone https://github.com/FortuneMon/Back.git
cd fortunemon
```
## ⚙️ 2. 백엔드 실행

① application.yml 설정
src/main/resources/application.yml 파일을 아래 예시를 참고해 생성합니다:

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/fortunemondb
    username: your_db_username
    password: your_db_password
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        show_sql: true
        format_sql: true
        use_sql_comments: true
        hbm2ddl:
          auto: update
        default_batch_fetch_size: 1000
  data:
    redis:
      host: your_redis_host
      port: your_redis_port
      password: your_redis_password

jwt:
  secret: your_jwt_secret_key
  access-token-expiration: 3600000
  refresh-token-expiration: 1209600000

openai:
  api:
    key: your_openai_api_key

server:
  port: 8080  # 로컬 실행 시 8080 권장
```
② 실행 명령어
bash
```
cd backend
./gradlew build
java -jar build/libs/fortunemon-backend.jar
```

## 🌐 3. 프론트엔드 실행
① .env 파일 설정
frontend/.env 파일을 생성 후 아래와 같이 API 서버 주소를 명시합니다:

```
REACT_APP_SERVER_BASE_URL= https://api.fortunemon.shop
```
② 실행 명령어

bash
```
cd frontend
npm install
npm run start
```


