# CLAUDE.md — 코딩 관례

## 프로젝트 개요
- Apple Reminder 웹 앱 (Backend: Spring Boot 3.5 + MyBatis + MySQL, Frontend: Next.js)
- 패키지: `com.bt.sample`
- Java 17, Maven


## 코딩 규칙

### 네이밍
- DTO 패키지: `domain` (dto 아님)
- 인터페이스 구현 클래스: `Default` 접두사 (예: `DefaultReminderListService`)
- `Impl` 접미사 사용하지 않음


### Service
- 인터페이스: `service/` 패키지에 위치
- 구현 클래스: `service/impl/` 하위 패키지에 위치 (예: `service/impl/DefaultReminderListService`)
- 클래스 레벨: `@Transactional(readOnly = true)`, 쓰기 메서드: `@Transactional`
- 생성자 주입

### 테스트
- 기능 추가/수정 시 반드시 테스트도 함께 작성
- 테스트 클래스는 대상 클래스의 패키지에 맞춰 배치 (예: `mapper/` → `test/.../mapper/`)

## 프로젝트 문서
- `spec.md` — 기능/UI 명세 (Apple Reminder 웹 앱)
- `plan.md` — 6단계 개발 계획 (Phase 1~6)
- `tasks.md` — 세부 작업 체크리스트
- `src/main/resources/schema.sql` — DB 스키마 DDL + 샘플 데이터

