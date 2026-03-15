-- ============================================
-- Apple Reminder Web App - Database Schema
-- ============================================

CREATE DATABASE IF NOT EXISTS testdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE testdb;

-- --------------------------------------------
-- 리스트 테이블
-- --------------------------------------------
DROP TABLE IF EXISTS reminders;
DROP TABLE IF EXISTS lists;

CREATE TABLE lists (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    color       VARCHAR(20)  NOT NULL DEFAULT '#007AFF',
    icon        VARCHAR(50)  NULL,
    sort_order  INT          NOT NULL DEFAULT 0,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- 리마인더 테이블
-- --------------------------------------------
CREATE TABLE reminders (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    list_id       BIGINT       NOT NULL,
    title         VARCHAR(255) NOT NULL,
    memo          TEXT         NULL,
    due_date      DATETIME     NULL,
    priority      TINYINT      NOT NULL DEFAULT 0 COMMENT '0:없음, 1:낮음, 2:보통, 3:높음',
    is_flagged    TINYINT(1)   NOT NULL DEFAULT 0,
    is_completed  TINYINT(1)   NOT NULL DEFAULT 0,
    completed_at  DATETIME     NULL,
    sort_order    INT          NOT NULL DEFAULT 0,
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_reminders_list FOREIGN KEY (list_id) REFERENCES lists (id) ON DELETE CASCADE,
    INDEX idx_reminders_list_id (list_id),
    INDEX idx_reminders_due_date (due_date),
    INDEX idx_reminders_is_completed (is_completed),
    INDEX idx_reminders_is_flagged (is_flagged)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 초기 샘플 데이터
-- ============================================

INSERT INTO lists (name, color, icon, sort_order) VALUES
('개인',  '#FF3B30', 'user',          1),
('업무',  '#007AFF', 'briefcase',     2),
('쇼핑',  '#34C759', 'shopping-cart', 3);

INSERT INTO reminders (list_id, title, memo, due_date, priority, is_flagged, sort_order) VALUES
(1, '운동하기',           '헬스장 30분',            DATE_ADD(CURDATE(), INTERVAL 0 DAY),  2, 0, 1),
(1, '독서 - 클린코드',     '5장까지 읽기',           DATE_ADD(CURDATE(), INTERVAL 1 DAY),  1, 1, 2),
(1, '병원 예약',           NULL,                    DATE_ADD(CURDATE(), INTERVAL 3 DAY),  0, 0, 3),
(2, '프로젝트 회의 준비',   '발표자료 정리',          CURDATE(),                            3, 1, 1),
(2, '코드 리뷰',           'PR #42 리뷰',           DATE_ADD(CURDATE(), INTERVAL 1 DAY),  2, 0, 2),
(2, '주간 보고서 작성',     NULL,                    DATE_ADD(CURDATE(), INTERVAL 4 DAY),  1, 0, 3),
(3, '우유',                NULL,                    NULL,                                 0, 0, 1),
(3, '세제',                '대용량',                 NULL,                                 0, 0, 2),
(3, '커피 원두',            '에티오피아 예가체프',     NULL,                                 0, 1, 3);
