# 개발 계획: Apple Reminder Web App

> 단순한 기능부터 시작하여 점진적으로 완성도를 높이는 방식으로 진행합니다.
> 각 Phase 완료 시 동작하는 상태를 유지합니다.

---

## 기술 스택

### Backend
| 항목 | 기술 | 비고 |
|------|------|------|
| Framework | Spring Boot 3.5 | Java 17 |
| ORM | MyBatis 3.x | XML Mapper 방식 |
| DB | MySQL 8.x | |
| 빌드 | Maven | |
| API 형식 | REST JSON | |
| CORS | WebMvcConfigurer | Frontend 연동용 |

### Frontend
| 항목 | 기술 | 비고 |
|------|------|------|
| Framework | Next.js 15 (App Router) | TypeScript |
| 스타일링 | Tailwind CSS 4 | Apple 디자인 재현 |
| HTTP 클라이언트 | fetch API | Next.js 내장 |
| 상태 관리 | React useState/useContext | 필요 시 Zustand 도입 |
| 아이콘 | Lucide React | |

### 개발 환경
| 항목 | 설정 |
|------|------|
| Backend 포트 | 8080 |
| Frontend 포트 | 3000 |
| API Proxy | next.config.ts → rewrites로 /api/** → localhost:8080 프록시 |

---

## Phase 1: 기반 구축 — DB + Backend CRUD + Frontend 초기화

> 목표: DB 스키마 생성, 리스트/리마인더 기본 CRUD API 완성, Next.js 프로젝트 생성

### 1-1. DB 스키마

- [ ] `schema.sql` 작성 (lists, reminders 테이블)
- [ ] 테이블 생성 DDL + 초기 데이터 INSERT

### 1-2. Backend — 기존 예제 코드 정리

- [ ] 기존 User 관련 예제 코드 삭제 (UserController, UserService, UserMapper, UserDto, UserMapper.xml)
- [ ] CORS 설정 추가 (WebConfig.java)

### 1-3. Backend — 리스트 API

- [ ] `ReminderListDto.java` — id, name, color, icon, sortOrder, createdAt, updatedAt
- [ ] `ReminderListMapper.java` — findAll, findById, insert, update, delete, reorder
- [ ] `ReminderListMapper.xml` — SQL 매핑
- [ ] `ReminderListService.java` — 비즈니스 로직
- [ ] `ReminderListController.java` — REST 엔드포인트
  - `GET /api/lists`
  - `POST /api/lists`
  - `PUT /api/lists/{id}`
  - `DELETE /api/lists/{id}`

### 1-4. Backend — 리마인더 API (기본 CRUD)

- [ ] `ReminderDto.java` — id, listId, title, memo, dueDate, priority, isFlagged, isCompleted, completedAt, sortOrder, createdAt, updatedAt
- [ ] `ReminderMapper.java` — findByListId, findById, insert, update, delete, toggleComplete
- [ ] `ReminderMapper.xml` — SQL 매핑
- [ ] `ReminderService.java`
- [ ] `ReminderController.java`
  - `GET /api/reminders?listId={id}`
  - `POST /api/reminders`
  - `PUT /api/reminders/{id}`
  - `PATCH /api/reminders/{id}/toggle`
  - `DELETE /api/reminders/{id}`

### 1-5. Backend 검증

- [ ] `mvn clean compile` 성공
- [ ] 서버 기동 후 Postman/curl로 CRUD 동작 확인

### 1-6. Frontend 초기화

- [ ] `npx create-next-app@latest frontend` (TypeScript, Tailwind CSS, App Router)
- [ ] `next.config.ts` — API 프록시 설정 (rewrites)
- [ ] `src/types/index.ts` — ReminderList, Reminder 타입 정의
- [ ] `src/lib/api.ts` — API 호출 함수 (fetch wrapper)
- [ ] 빌드 확인 (`npm run build`)

---

## Phase 2: 기본 UI — 사이드바 + 리마인더 목록

> 목표: 사이드바에서 리스트를 선택하면 해당 리마인더 목록을 표시하고, 기본 CRUD 동작

### 2-1. 레이아웃

- [ ] `layout.tsx` — 사이드바 + 메인 콘텐츠 2단 레이아웃
- [ ] Apple 스타일 색상 적용 (사이드바 `#F2F1F6`, 메인 `#FFFFFF`)
- [ ] 시스템 폰트 설정

### 2-2. 사이드바 — 내 리스트

- [ ] `Sidebar.tsx` — 리스트 목록 표시
- [ ] 리스트 항목: 색상 원형 아이콘 + 이름 + 리마인더 수
- [ ] 리스트 선택 시 하이라이트
- [ ] `+ 리스트 추가` 버튼 → 리스트 생성 모달/인라인 입력

### 2-3. 메인 — 리마인더 목록

- [ ] `ReminderList.tsx` — 선택된 리스트의 리마인더 표시
- [ ] 리스트 제목 (큰 볼드, 리스트 색상)
- [ ] `ReminderItem.tsx` — 각 리마인더 행
  - 원형 체크박스 (리스트 색상 테두리)
  - 제목 표시
  - 완료 토글 (체크 시 취소선)
- [ ] `+ 새로운 리마인더` 버튼 → 인라인 제목 입력, Enter로 생성

### 2-4. 리마인더 상세 편집

- [ ] `ReminderDetail.tsx` — 리마인더 클릭 시 인라인 확장
- [ ] 제목, 메모, 마감일, 우선순위, 플래그, 리스트 이동 편집
- [ ] 확장/축소 애니메이션

---

## Phase 3: 스마트 리스트

> 목표: 오늘/예정/전체/플래그/완료됨 스마트 리스트 동작

### 3-1. Backend — 스마트 리스트 API

- [ ] `GET /api/reminders/today` — 마감일이 오늘인 리마인더
- [ ] `GET /api/reminders/scheduled` — 마감일 설정된 미완료 리마인더 (날짜별 그룹핑)
- [ ] `GET /api/reminders/all` — 전체 미완료 리마인더
- [ ] `GET /api/reminders/flagged` — 플래그 리마인더
- [ ] `GET /api/reminders/completed` — 완료된 리마인더
- [ ] ReminderMapper.xml에 각 쿼리 추가

### 3-2. Frontend — 스마트 리스트 카드

- [ ] 사이드바 상단 2x2 그리드 카드 (오늘/예정/전체/플래그)
- [ ] 각 카드: Apple 색상 아이콘 + 카운트
- [ ] 완료됨 카드 (그리드 아래 전체 너비)
- [ ] 카드 클릭 시 해당 필터 리마인더 표시

### 3-3. Frontend — 예정 리스트 날짜별 그룹핑

- [ ] 날짜 헤더로 그룹핑하여 표시
- [ ] 오늘/내일/이번 주 등 상대 날짜 레이블

### 3-4. 스마트 리스트 카운트

- [ ] `GET /api/reminders/counts` — 각 스마트 리스트 카운트 일괄 조회 API
- [ ] 사이드바 카드에 실시간 카운트 반영

---

## Phase 4: 검색 + 리스트 관리 강화

> 목표: 검색 기능, 리스트 수정/삭제, 색상/아이콘 선택

### 4-1. 검색

- [ ] Backend: `GET /api/reminders/search?q={keyword}` (제목 + 메모 LIKE 검색)
- [ ] Frontend: 사이드바 상단 검색 바
- [ ] 검색 결과를 메인 영역에 리스트 형태로 표시
- [ ] 디바운스 적용 (300ms)

### 4-2. 리스트 수정/삭제

- [ ] 리스트 우클릭 컨텍스트 메뉴 (수정, 삭제)
- [ ] 리스트 수정 모달: 이름, 색상, 아이콘 변경
- [ ] 리스트 삭제 확인 다이얼로그 (소속 리마인더 함께 삭제 경고)

### 4-3. 리스트 색상/아이콘 선택 UI

- [ ] 12가지 색상 프리셋 팔레트
- [ ] 아이콘 선택 그리드 (Lucide 아이콘 셋)

---

## Phase 5: 애니메이션 + 인터랙션 완성

> 목표: Apple Reminder 수준의 부드러운 UX

### 5-1. 체크 애니메이션

- [ ] 완료 체크 시 원형 체크박스 색상 채움 + 체크마크 (0.3s)
- [ ] 완료 후 0.5s 딜레이 → 페이드아웃으로 목록에서 제거

### 5-2. 전환 애니메이션

- [ ] 리스트/스마트 리스트 전환 시 메인 콘텐츠 크로스페이드 (0.2s)
- [ ] 상세 편집 확장/축소 (0.25s ease)
- [ ] 사이드바 hover 배경색 전환 (0.15s)

### 5-3. 드래그 앤 드롭

- [ ] 리마인더 순서 변경 (드래그)
- [ ] 리스트 순서 변경 (드래그)
- [ ] Backend: `PUT /api/lists/reorder`, `PUT /api/reminders/reorder`
- [ ] 라이브러리: `@dnd-kit/core` 또는 `react-beautiful-dnd`

### 5-4. 삭제 인터랙션

- [ ] 리마인더 우클릭 삭제 시 슬라이드아웃 애니메이션

---

## Phase 6: 마무리 + 품질

> 목표: 안정성, 에러 처리, 반응형

### 6-1. 에러 처리

- [ ] Backend: 글로벌 예외 핸들러 (@ControllerAdvice)
- [ ] Frontend: API 에러 시 토스트 알림
- [ ] 네트워크 오류 시 재시도 안내

### 6-2. 로딩 상태

- [ ] 리스트/리마인더 로딩 시 스켈레톤 UI
- [ ] 버튼 클릭 시 로딩 스피너

### 6-3. 반응형 디자인

- [ ] 태블릿: 사이드바 축소 가능
- [ ] 모바일: 사이드바 오버레이 (햄버거 메뉴)

### 6-4. 빈 상태

- [ ] 리스트가 없을 때 안내 메시지
- [ ] 리마인더가 없을 때 안내 메시지 (스마트 리스트별 다른 메시지)

---

## 범위 외 (Out of Scope)

- 사용자 인증/로그인
- 반복 리마인더
- 태그 기능
- 위치 기반 알림
- 하위 리마인더 (서브태스크)
- 리스트 공유
- 푸시 알림
