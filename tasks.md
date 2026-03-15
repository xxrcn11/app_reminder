# Tasks: Apple Reminder Web App

> 완료된 작업은 `[x]`로 체크합니다.

---

## Phase 1: 기반 구축

### DB 스키마
- [x] `src/main/resources/schema.sql` 작성
  - [x] `lists` 테이블 DDL (id, name, color, icon, sort_order, created_at, updated_at)
  - [x] `reminders` 테이블 DDL (id, list_id, title, memo, due_date, priority, is_flagged, is_completed, completed_at, sort_order, created_at, updated_at)
  - [x] 외래키 제약조건 (reminders.list_id → lists.id, ON DELETE CASCADE)
  - [x] 초기 샘플 데이터 INSERT (리스트 3개, 리마인더 9개)

### Backend — 기존 예제 코드 정리
- [x] `UserController.java` 삭제
- [x] `UserService.java` 삭제
- [x] `UserMapper.java` 삭제
- [x] `UserDto.java` 삭제
- [x] `UserMapper.xml` 삭제
- [x] `WebConfig.java` 생성 — CORS 설정 (localhost:3000 허용)

### Backend — ReminderList API
- [x] `ReminderListDto.java` 생성
  - [x] 필드: id, name, color, icon, sortOrder, createdAt, updatedAt
  - [x] getter/setter
- [x] `ReminderListMapper.java` 생성 (@Mapper 인터페이스)
  - [x] `List<ReminderListDto> findAll()`
  - [x] `ReminderListDto findById(Long id)`
  - [x] `void insert(ReminderListDto list)`
  - [x] `void update(ReminderListDto list)`
  - [x] `void deleteById(Long id)`
- [x] `ReminderListMapper.xml` 생성
  - [x] findAll SELECT
  - [x] findById SELECT
  - [x] insert INSERT (useGeneratedKeys)
  - [x] update UPDATE
  - [x] deleteById DELETE
- [x] `ReminderListService.java` 생성
  - [x] findAll, findById, create, update, delete 메서드
- [x] `ReminderListController.java` 생성
  - [x] `GET /api/lists` — 전체 리스트 조회
  - [x] `POST /api/lists` — 리스트 생성
  - [x] `PUT /api/lists/{id}` — 리스트 수정
  - [x] `DELETE /api/lists/{id}` — 리스트 삭제

### Backend — Reminder API (기본 CRUD)
- [x] `ReminderDto.java` 생성
  - [x] 필드: id, listId, title, memo, dueDate, priority, isFlagged, isCompleted, completedAt, sortOrder, createdAt, updatedAt
  - [x] getter/setter (Lombok)
- [x] `ReminderMapper.java` 생성 (@Mapper 인터페이스)
  - [x] `List<ReminderDto> findByListId(Long listId)`
  - [x] `ReminderDto findById(Long id)`
  - [x] `void insert(ReminderDto reminder)`
  - [x] `void update(ReminderDto reminder)`
  - [x] `void deleteById(Long id)`
  - [x] `void toggleComplete(Long id)`
- [x] `ReminderMapper.xml` 생성
  - [x] findByListId SELECT (is_completed, sort_order 정렬)
  - [x] findById SELECT
  - [x] insert INSERT (useGeneratedKeys)
  - [x] update UPDATE
  - [x] deleteById DELETE
  - [x] toggleComplete UPDATE (is_completed 토글 + completed_at 설정)
- [x] `ReminderService.java` 생성 (인터페이스 + DefaultReminderService 구현)
  - [x] findByListId, findById, create, update, delete, toggleComplete 메서드
- [x] `ReminderController.java` 생성
  - [x] `GET /api/reminders?listId={id}` — 리스트별 리마인더 조회
  - [x] `POST /api/reminders` — 리마인더 생성 (201 Created)
  - [x] `PUT /api/reminders/{id}` — 리마인더 수정
  - [x] `PATCH /api/reminders/{id}/toggle` — 완료 토글
  - [x] `DELETE /api/reminders/{id}` — 리마인더 삭제 (204 No Content)

### Backend 검증
- [x] `mvn clean compile` 성공 확인
- [x] ReminderMapperTest (9개 테스트 통과)
- [x] ReminderListMapperTest (5개 테스트 통과)

### Frontend 초기화
- [ ] `npx create-next-app@latest frontend` 실행 (TypeScript, Tailwind CSS, App Router, src/ 디렉토리)
- [ ] `frontend/next.config.ts` — rewrites 설정 (`/api/:path*` → `http://localhost:8080/api/:path*`)
- [ ] `frontend/src/types/index.ts` 생성
  - [ ] `ReminderList` 타입 정의 (id, name, color, icon, sortOrder, createdAt, updatedAt)
  - [ ] `Reminder` 타입 정의 (id, listId, title, memo, dueDate, priority, isFlagged, isCompleted, completedAt, sortOrder, createdAt, updatedAt)
- [ ] `frontend/src/lib/api.ts` 생성
  - [ ] 리스트 API 함수 (fetchLists, createList, updateList, deleteList)
  - [ ] 리마인더 API 함수 (fetchReminders, createReminder, updateReminder, toggleReminder, deleteReminder)
- [ ] `npm run build` 성공 확인

---

## Phase 2: 기본 UI

### 레이아웃
- [ ] `frontend/src/app/layout.tsx` — 2단 레이아웃 (사이드바 280px + 메인)
- [ ] `frontend/src/app/globals.css` — Apple 스타일 글로벌 CSS
  - [ ] 사이드바 배경 `#F2F1F6`, 메인 배경 `#FFFFFF`
  - [ ] 시스템 폰트 (`-apple-system, BlinkMacSystemFont, 'Segoe UI'`)
  - [ ] 기본 색상 변수 (텍스트 `#1C1C1E`, 보조 `#8E8E93`, 구분선 `#E5E5EA`)
- [ ] `frontend/src/app/page.tsx` — 메인 페이지 (사이드바 + 리마인더 목록 조합)

### 사이드바 — 내 리스트
- [ ] `frontend/src/components/Sidebar.tsx` 생성
  - [ ] API에서 리스트 목록 로드
  - [ ] 각 리스트 항목 렌더링 (색상 원형 아이콘 + 이름 + 리마인더 수)
  - [ ] 리스트 클릭 시 선택 상태 변경 (배경 하이라이트)
  - [ ] 선택된 리스트 ID를 부모에 전달 (콜백)
- [ ] `+ 리스트 추가` 버튼
  - [ ] 클릭 시 인라인 입력 필드 표시
  - [ ] 이름 입력 후 Enter → API 호출로 리스트 생성
  - [ ] 기본 색상 `#007AFF` 자동 적용

### 메인 — 리마인더 목록
- [ ] `frontend/src/components/ReminderList.tsx` 생성
  - [ ] 선택된 리스트의 리마인더 목록 표시
  - [ ] 리스트 제목 헤더 (28px bold, 리스트 색상 적용)
  - [ ] 리마인더 수 표시
- [ ] `frontend/src/components/ReminderItem.tsx` 생성
  - [ ] 원형 체크박스 (빈 원, 리스트 색상 테두리)
  - [ ] 리마인더 제목 표시
  - [ ] 보조 정보 표시 (메모 미리보기, 마감일, 우선순위)
  - [ ] 체크 클릭 → toggleComplete API 호출 → 취소선 표시
  - [ ] 우측 플래그 아이콘 (설정 시 주황색)
  - [ ] 각 행 사이 구분선 (체크박스 이후부터 시작)
- [ ] `+ 새로운 리마인더` 버튼
  - [ ] 클릭 시 목록 하단에 빈 입력 행 추가
  - [ ] 제목 입력에 자동 포커스
  - [ ] Enter → API 호출로 리마인더 생성
  - [ ] 연속 입력 지원 (생성 후 다음 빈 행 자동 추가)

### 리마인더 상세 편집
- [ ] `frontend/src/components/ReminderDetail.tsx` 생성
  - [ ] 리마인더 클릭 시 인라인 확장 (카드 형태)
  - [ ] 제목 인라인 편집
  - [ ] 메모 텍스트 영역
  - [ ] 마감일 date/time picker
  - [ ] 우선순위 드롭다운 (없음/낮음/보통/높음)
  - [ ] 플래그 토글
  - [ ] 리스트 이동 드롭다운
  - [ ] 변경 시 자동 저장 (debounce 500ms) 또는 blur 시 저장

---

## Phase 3: 스마트 리스트

### Backend — 스마트 리스트 API
- [x] `ReminderMapper.java`에 메서드 추가
  - [x] `List<ReminderDto> findToday()`
  - [x] `List<ReminderDto> findScheduled()`
  - [x] `List<ReminderDto> findAllIncomplete()`
  - [x] `List<ReminderDto> findFlagged()`
  - [x] `List<ReminderDto> findCompleted()`
- [x] `ReminderMapper.xml`에 쿼리 추가
  - [x] findToday — `WHERE DATE(due_date) = CURDATE() AND is_completed = 0`
  - [x] findScheduled — `WHERE due_date IS NOT NULL AND is_completed = 0 ORDER BY due_date`
  - [x] findAllIncomplete — `WHERE is_completed = 0`
  - [x] findFlagged — `WHERE is_flagged = 1 AND is_completed = 0`
  - [x] findCompleted — `WHERE is_completed = 1 ORDER BY completed_at DESC`
- [x] `ReminderService.java`에 메서드 추가
- [x] `ReminderController.java`에 엔드포인트 추가
  - [x] `GET /api/reminders/today`
  - [x] `GET /api/reminders/scheduled`
  - [x] `GET /api/reminders/all`
  - [x] `GET /api/reminders/flagged`
  - [x] `GET /api/reminders/completed`

### Backend — 스마트 리스트 카운트 API
- [x] `ReminderMapper.java` — `Map<String, Object> getCounts()`
- [x] `ReminderMapper.xml` — 각 스마트 리스트별 COUNT 쿼리
- [x] `ReminderController.java` — `GET /api/reminders/counts`

### Frontend — 스마트 리스트 카드
- [ ] `frontend/src/components/SmartListCards.tsx` 생성
  - [ ] 2x2 그리드 레이아웃 (오늘/예정/전체/플래그)
  - [ ] 각 카드: 둥근 모서리(12px), 원형 배경 아이콘 + 카운트(우측 상단 bold)
  - [ ] 오늘 — `#007AFF`, 캘린더 아이콘
  - [ ] 예정 — `#FF3B30`, 캘린더 아이콘
  - [ ] 전체 — `#1C1C1E`, 트레이 아이콘
  - [ ] 플래그 — `#FF9500`, 깃발 아이콘
  - [ ] 완료됨 카드 — 그리드 아래 전체 너비, `#8E8E93`, 체크 아이콘
  - [ ] 카드 클릭 → 해당 스마트 리스트 리마인더 표시
  - [ ] hover 시 배경색 변화
- [ ] `frontend/src/lib/api.ts`에 스마트 리스트 API 함수 추가
  - [ ] fetchTodayReminders, fetchScheduledReminders, fetchAllReminders
  - [ ] fetchFlaggedReminders, fetchCompletedReminders, fetchCounts
- [ ] Sidebar.tsx에 SmartListCards 통합

### Frontend — 예정 리스트 날짜별 그룹핑
- [ ] 예정(Scheduled) 뷰에서 날짜 헤더로 그룹핑
- [ ] 상대 날짜 레이블 (오늘, 내일, 이번 주 요일명, 이후는 날짜)
- [ ] 각 날짜 그룹 아래 해당 리마인더 표시

---

## Phase 4: 검색 + 리스트 관리 강화

### Backend — 검색 API
- [x] `ReminderMapper.java` — `List<ReminderDto> search(String keyword)`
- [x] `ReminderMapper.xml` — `WHERE title LIKE CONCAT('%',#{keyword},'%') OR memo LIKE CONCAT('%',#{keyword},'%')`
- [x] `ReminderService.java` — search 메서드
- [x] `ReminderController.java` — `GET /api/reminders/search?q={keyword}`

### Frontend — 검색 UI
- [ ] 사이드바 상단 검색 바 컴포넌트
  - [ ] 돋보기 아이콘 + 입력 필드
  - [ ] 디바운스 300ms 적용
  - [ ] 검색어 입력 시 API 호출
- [ ] 검색 결과 표시
  - [ ] 메인 영역에 검색 결과 리마인더 목록 표시
  - [ ] 각 리마인더에 소속 리스트 이름 표시
  - [ ] 검색어 하이라이트
- [ ] 검색 초기화 (X 버튼 또는 ESC)

### Frontend — 리스트 수정/삭제
- [ ] 리스트 항목 우클릭 컨텍스트 메뉴 컴포넌트
  - [ ] "리스트 정보 수정" 메뉴
  - [ ] "리스트 삭제" 메뉴
- [ ] 리스트 수정 모달
  - [ ] 리스트 이름 입력
  - [ ] 색상 선택 (12가지 프리셋 팔레트)
  - [ ] 아이콘 선택 그리드 (Lucide 아이콘)
  - [ ] 저장/취소 버튼
- [ ] 리스트 삭제 확인 다이얼로그
  - [ ] "이 리스트와 포함된 N개의 리마인더가 모두 삭제됩니다" 경고
  - [ ] 삭제/취소 버튼

---

## Phase 5: 애니메이션 + 인터랙션

### 완료 체크 애니메이션
- [ ] 체크박스 클릭 시 원형이 리스트 색상으로 채워지는 애니메이션 (0.3s)
- [ ] 체크마크 아이콘 등장 애니메이션
- [ ] 제목에 취소선 애니메이션
- [ ] 0.5s 딜레이 후 높이 축소 + 페이드아웃으로 목록에서 제거

### 전환 애니메이션
- [ ] 리스트/스마트 리스트 선택 변경 시 메인 콘텐츠 크로스페이드 (0.2s)
- [ ] 리마인더 상세 확장/축소 높이 애니메이션 (0.25s ease)
- [ ] 사이드바 항목 hover 배경색 전환 (0.15s)

### 드래그 앤 드롭
- [ ] `@dnd-kit/core` 또는 `react-beautiful-dnd` 패키지 설치
- [ ] 리마인더 드래그로 순서 변경
  - [ ] 드래그 핸들 표시
  - [ ] 드래그 중 시각 피드백 (그림자, 반투명)
  - [ ] 드롭 시 API 호출 (`PUT /api/reminders/reorder`)
- [ ] 사이드바 리스트 드래그로 순서 변경
  - [ ] 드롭 시 API 호출 (`PUT /api/lists/reorder`)
- [ ] Backend: reorder 엔드포인트 구현
  - [ ] `PUT /api/lists/reorder` — 리스트 순서 일괄 업데이트
  - [ ] `PUT /api/reminders/reorder` — 리마인더 순서 일괄 업데이트

### 삭제 인터랙션
- [ ] 리마인더 우클릭 삭제 시 좌측 슬라이드아웃 애니메이션
- [ ] 삭제 확인 없이 즉시 삭제 (Apple 스타일)

---

## Phase 6: 마무리 + 품질

### Backend — 에러 처리
- [x] `GlobalExceptionHandler.java` 생성 (@RestControllerAdvice)
  - [x] 필수 파라미터 누락 (400) 처리
  - [x] 파라미터 타입 불일치 (400) 처리
  - [x] 서버 에러 (500) 처리
  - [x] 통일된 에러 응답 형식 (code, message)

### Frontend — 에러 처리
- [ ] 토스트 알림 컴포넌트
  - [ ] API 에러 시 하단 토스트 표시
  - [ ] 자동 사라짐 (3초)
- [ ] 네트워크 오류 시 재시도 안내 메시지

### Frontend — 로딩 상태
- [ ] 리스트 목록 로딩 스켈레톤 UI
- [ ] 리마인더 목록 로딩 스켈레톤 UI
- [ ] 버튼/액션 로딩 스피너

### Frontend — 반응형 디자인
- [ ] 태블릿 (768px~1024px): 사이드바 축소 모드 (아이콘만 표시)
- [ ] 모바일 (~768px): 사이드바 숨김 + 햄버거 메뉴 버튼
  - [ ] 메뉴 클릭 시 사이드바 오버레이로 표시
  - [ ] 배경 딤 처리 + 바깥 클릭 시 닫기

### Frontend — 빈 상태 UI
- [ ] 리스트가 없을 때: "리스트를 추가하여 시작하세요" 안내
- [ ] 리마인더가 없을 때 (일반 리스트): "리마인더 없음" 안내
- [ ] 스마트 리스트별 빈 상태 메시지
  - [ ] 오늘: "오늘 예정된 리마인더가 없습니다"
  - [ ] 예정: "예정된 리마인더가 없습니다"
  - [ ] 전체: "모든 리마인더를 완료했습니다"
  - [ ] 플래그: "플래그가 지정된 리마인더가 없습니다"
  - [ ] 완료됨: "완료된 리마인더가 없습니다"
