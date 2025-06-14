## 🐱‍🏍 FortuneMon 백엔드 API 명세서

### 📌 Base URL

```
/
```

---

## 👤 User 관련 API

### 🔐 회원가입

**POST** `/users/signup`

- 회원가입 API입니다. 닉네임, 아이디, 패스워드를 입력합니다.

#### Request Body

```json
{
  "loginId": "string",
  "password": "string",
  "nickname": "string"
}
```

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🔐 로그인

**POST** `/users/signin`

- 로그인 API입니다. 아이디와 비밀번호를 입력합니다.

#### Request Body

```json
{
  "loginId": "string",
  "password": "string"
}
```

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🔁 액세스 토큰 재발급

**POST** `/users/refresh`

- 로그인 후, 토큰 만료 시 재발급합니다.

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 📥 루틴 추가

**POST** `/users/routines/{id}`

- 유저의 진행 중인 루틴 추가

#### Path Parameter

- `id`: 루틴 ID (integer)

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🗑 루틴 삭제

**DELETE** `/users/routines/{id}`

- 유저의 루틴 삭제

#### Path Parameter

- `id`: 루틴 ID (integer)

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🔁 루틴 상태 변경

**PATCH** `/users/routines/{id}/status`

- 유저 루틴의 수행 여부 상태를 변경합니다.

#### Path Parameter

- `id`: 루틴 ID (integer)

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 📊 루틴 통계 조회

**GET** `/users/routines/{date}/statistics`

#### Path Parameter

- `date`: 날짜 (yyyy-MM-dd)

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 📃 루틴 조회

**GET** `/users/routine`

- 유저의 진행중인 루틴 목록 조회

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🧍 유저 정보 조회

**GET** `/users/info`

- 닉네임, 파트너 포켓몬 등 정보 반환

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🆔 로그인 ID 중복 체크

**GET** `/users/check-login-id?loginId=abc`

#### Query Parameter

- `loginId`: 중복 확인할 아이디

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🆔 닉네임 중복 체크

**GET** `/users/check-nickname?nickname=abc`

#### Query Parameter

- `nickname`: 중복 확인할 닉네임

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

## 🎲 몬스터볼 관련

### 🏐 몬스터볼 오픈

**POST** `/users/balls/{id}/open`

#### Path Parameter

- `id`: 몬스터볼 ID

#### Response

```json
{
  "id": 1,
  "name": "피카츄",
  "url": "https://...",
  "type": ["전기"],
  "groupName": "1세대",
  "owned": true,
  "parnter": true
}
```

---

### 🎁 유저 몬스터볼 조회

**GET** `/users/balls`

#### Response

```json
[
  {
    "id": 1,
    "ballId": 2,
    "url": "https://...",
    "created_at": "2025-06-14T13:26:31.602Z",
    "open": true
  }
]
```

---

## 🧧 오늘의 운세

### 🎰 오늘의 운세 뽑기

**POST** `/fortunes`

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🧾 오늘의 운세 조회

**GET** `/users/fortune`

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

## 🧬 포켓몬 관련

### ⭐ 파트너 포켓몬 설정

**PATCH** `/users/partners/{id}`

#### Path Parameter

- `id`: 포켓몬 ID

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

---

### 🧬 유저 포켓몬 조회

**GET** `/users/pokemons`

#### Response

```json
[
  {
    "id": 1,
    "name": "피카츄",
    "url": "string",
    "type": ["전기"],
    "groupName": "1세대",
    "owned": true,
    "parnter": true
  }
]
```

---

## 📂 루틴 카테고리 조회

### 📚 루틴 카테고리별 조회

**GET** `/routines?category=운동`

#### Query Parameter

- `category`: 문자열, 필수

#### Response

```json
{
  "isSuccess": true,
  "code": "string",
  "message": "string",
  "result": {}
}
```

