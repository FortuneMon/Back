# Contributing Guidelines

`FortuneMon` 프로젝트에 기여해주셔서 감사합니다. 이 문서는 개발 시 지켜야 할 규칙들을 설명합니다. 협업의 원활함을 위해 반드시 아래 코딩 가이드 및 규칙들을 따라주세요.

> # BackEnd 코딩 가이드

## 📌 \[1] Swagger / OpenAPI 문서 동기화

* 백엔드 API 문서(Swagger/OpenAPI)와 항상 **동기화된 상태**로 API를 개발 및 사용합니다.
* 실제 사용하는 API 요청 경로, 응답 타입 등을 Swagger와 일치시켜 유지합니다.

---

## 📌 \[2] 일관된 예외 처리 구조

* 공통적인 예외 처리를 위한 **커스텀 예외 클래스** 및 **공통 Response 형식**을 사용합니다.
* 예상 가능한 예외는 미리 정의된 형식으로 프론트에 전달되도록 처리합니다.

---

## 📌 \[3] 환경 변수 관리

* 환경 변수는 `.env` 또는 secrets 등으로 관리하며, 절대 **하드코딩된 키/비밀번호는 금지**합니다.
* 예: `REACT_APP_API_URL`, `REACT_APP_GOOGLE_CLIENT_ID` 등

---

## 📌 \[4] 반복 로직 관리

* 반복적으로 사용되는 함수 또는 로직은 `common/`, `utils/` 등 공통 유틸 디렉토리에 모듈화합니다.
* 코드 중복을 줄이고 유지보수를 용이하게 합니다.

---

<br>

> # FrontEnd 코딩 가이드


## 🔗 \[1] 컴포넌트 설계 원칙

### ✅ 재사용성

* 반복 사용되는 UI 요소는 **컴포넌트화**합니다.
* 하나의 목적만 수행하도록 설계하며 **비즈니스 로직은 최대한 분리**합니다.

### ✅ 분리

* 하나의 파일이 너무 길거나 복잡해지는 경우 **기능 단위로 컴포넌트를 분리**합니다.

### ✅ 네이밍

* 역할이 명확하게 드러나는 네이밍을 사용합니다.
* `ButtonComponent`, `InputComponent`와 같은 \*\*불필요한 접미사(Component)\*\*는 지양합니다.


### 🗂️ \[현재 컴포넌트 구조]

```
src/components/
├── Common        # 공통 요소 (버튼, 인풋, 로딩 등)
├── Layouts       # 페이지 레이아웃 관련
├── Chart         # 루틴 통계 시각화 요소 (캘린더, 데이, 케러셀 등)
├── Fortune       # 운세 관련 컴포넌트
├── Pokemon       # 포켓몬 관련 컴포넌트
└── Routines      # 루틴 관련 컴포넌트
```

<br>

##  🔗 \[2] 전역 상태 관리

### ✅ 사용 도구

* [Redux Toolkit](https://redux-toolkit.js.org/) 사용

### ✅ 상태 관리 구조

```
src/store/
├── slices/
│   └── user.js
├── thunks/
│   └── user.js
└── store.js
```

### ✅ store.js 예시

```js
// src/store/store.js
import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./slices/user";
// import new slice!!

export const store = configureStore({
  reducer: {
    user: userReducer,
    //                  <- add here!!!
  },
});
```

### ✅ Thunk 예시

```js
// 1. thunk 파일에 thunk를 생성한다.
// src/store/thunks/user.js
...
export const addMyRoutine = createAsyncThunk("addMyRoutine", async (routineId) => {
  const {
    data: { result },
  } = await axiosInstance.post(`${prefix}/routines/${routineId}`);
  return result;
});
...

// 2. 해당 도메인의 slice 에서 import 하고, 비동기 요청 후 수행할 동작 reducer에 추가
// src/store/slices/user.js
const userSlice = createSlice({
  name: "user",
  ...
  extraReducers: (builder) => {
	  ...
	  builder.addCase(addMyRoutine.fulfilled, (state, action) => {
      const routineName = action.payload.routineName;
      const routineId = action.meta.arg;
      state.myRoutines = [...state.myRoutines, { routineId, name: routineName, isCompeleted: false }];
      console.log("addMyRoutine Result:", state.myRoutines);
    });
    ...
  },
});

// 3. 사용 시 useDispatch 및 dispatch로 사용
// src/components/routines/RoutineCard.jsx
const dispatch = useDispatch();

...

const onClick = useCallback(() => {
    if (isRegistered) {
			...
    } else {
      dispatch(addMyRoutine(routineId)); //here!!
    }
  }, [dispatch, routineId, isRegistered]);
```

### ✅ 상태 사용 예시

```js
// src/store/slices/user.js

export const selectMyInfo = (state) => state.user?.me; //here!!

// src/pages/MyPage.jsx
import { selectMyInfo } from "../store/slices/user"; //here!!

const MyPage = () => {
	...
	const user = useSelector(selectMyInfo); //here!!
	...
}
```


<br>

## 🔗 \[3] API 통신 구조

### ✅ API 통신 라이브러리

* [axios](https://axios-http.com/)

### ✅ 디렉토리 구조

```
src/apis/
├── UserApi.js
├── RoutineApi.js
├── FortuneApi.js
└── PokeApi.js
```

### ✅ API 호출 예시

```js
// 사용 예시
// src/apis/RoutineApi.js
...

/**
 * @param {number} year
 * @param {number} month
 * @returns {Promise<{routineId: number; routineName: string; daysStatistics: {}}[]>}
 */
export async function fetchMyStatistics(year, month) {
  try {
    const date = dayjs(new Date(year, month - 1)).format("YYYY-MM-DD");
    const {
      data: {
        result: { statistics },
      },
    } = await axiosInstance.get(`${prefix}/routines/${date}/statistics`);
    return statistics;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

...

//src/pages/ChartPage.jsx
import { fetchMyStatistics } from "../apis/RoutineApi";

...

useEffect(() => {
    if (!isLoading) {
	    // here!!
      fetchMyStatistics(selectedDate.year, selectedDate.month).then((s) => {
        setStatistics(s);
      });
    }
  }, [isLoading, selectedDate]);
  
...
```

> ❗ 직접 `axios`를 import 하지 않고, 반드시 api 모듈에서 함수로 추출해서 사용하세요.

## 🔗 \[4] Hooks, Util 관리
- 복잡한 hooks 로직이나 재사용 가능한 로직은 hooks 디렉토리에 custom hooks 파일을 추가하여 사용하세요.
- 반복되는 로직이나, 복잡한 로직은 utils 디렉토리의 AppUtils.js 에 유틸 함수를 추가하여 사용하세요.

<br>

---

<br>

> # 작업 기여 가이드

<br>

## 1. Issues 기반 작업 프로세스

모든 개발 작업은 반드시 GitHub 이슈를 생성한 뒤에 시작합니다.
이슈가 생성되면 자동으로 고유한 번호가 부여되며, **해당 번호를 기준으로 브랜치명, 커밋 메시지, PR 제목을 작성**합니다.

예시:

```
#35 루틴 수행여부 기능 버그 수정
```

---

<br>

## 2. Git 브랜치 규칙

브랜치 이름은 다음 형식을 따릅니다:

```
{커밋 메시지 컨벤션 타입}: #{이슈번호} - {간단한설명}
```

예시:

```
feat: #9 로그인 UI 개발
refactor: #42 운세페이지_리팩토링
hotfix: #50 배포 후 https 요청 오류 수정
```

---

<br>

## 3. Git 커밋 메시지 컨벤션

커밋 메시지는 다음 형식을 지켜주세요.
Git branch 규칙과 유사하나 내용의 상세 정도의 차이가 있습니다.

```
{타입}: #{이슈번호} {내용}
```

### ✅ 타입 목록

| 타입       | 설명                        |
| -------- | ------------------------- |
| feat     | 새로운 기능 추가                 |
| fix      | 버그 수정                     |
| refactor | 코드 리팩토링                   |
| style    | 코드 포맷팅, 세미콜론 누락, 코드 변경 없음 |
| docs     | 문서 수정                     |
| chore    | 기타 변경사항 (빌드 스크립트 수정 등)    |
| test     | 테스트 관련 코드 추가/수정           |
| perf     | 성능 향상                     |

### ✅ 커밋 메시지 예시

```
feat: #35 루틴 미수행 시 경고 메시지 추가
fix: #35 루틴 수행 여부 체크 버그 수정
docs: #40 README 업데이트
refactor: #42 포켓몬 뽑기 결과 컴포넌트 구조 개선
```

---

<br>

## 4. Pull Request (PR) 작성 규칙

PR 제목도 **이슈 번호 기반으로** 작성합니다.
Git branch 전략을 따라왔다면 자동적으로 제목이 생성됩니다.

PR 본문에서는 보다 자세한 설명을 작성해주세요.
이미지나 코드에 대한 설명을 적어주시면 원활한 소통이 가능합니다.

```
#{이슈번호} {변경 요약}
```

예시:

```
fix: #35 루틴 미수행 기능 관련 버그 수정
refactor: #42 포켓몬 뽑기 결과 페이지 UI 개선
```

### 📋 PR 본문 예시

```md
💡 작업 개요
- 루틴 수행 여부 체크 로직에서 null 체크가 누락되어 발생하던 버그를 수정했습니다.

## ✅ 변경 사항

- 루틴 완료 여부 판단 로직 수정
- 경고 메시지 추가
- 관련 유닛 테스트 추가

## 🔗 관련 이슈

- Closes #35
```

---

<br>

## 5. Pull Request (PR) MERGE 유의사항

FortuneMon의 PR은 **승인**을 기반으로 MERGE가 가능합니다.

**총 4명의 팀원 중 4명이 모두 승인해야** MERGE가 활성화됩니다.

이를 통해 코드의 품질과 안정성을 확보하고, 모든 팀원이 변경사항을 인지할 수 있도록 합니다.

---

> 작성자: `FortuneMon` 협업의 기술

> 업데이트: 2025-06-15

---
