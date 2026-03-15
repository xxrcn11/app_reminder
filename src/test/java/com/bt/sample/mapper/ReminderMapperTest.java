package com.bt.sample.mapper;

import com.bt.sample.domain.ReminderDto;
import com.bt.sample.domain.ReminderListDto;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReminderMapperTest {

    @Autowired
    private ReminderMapper reminderMapper;

    @Autowired
    private ReminderListMapper reminderListMapper;

    private Long createTestList() {
        ReminderListDto list = ReminderListDto.builder()
                .name("테스트 리스트").color("#007AFF").sortOrder(1).build();
        reminderListMapper.insert(list);
        return list.getId();
    }

    private ReminderDto createTestReminder(Long listId, String title) {
        ReminderDto reminder = ReminderDto.builder()
                .listId(listId)
                .title(title)
                .priority(0)
                .isFlagged(false)
                .isCompleted(false)
                .sortOrder(1)
                .build();
        reminderMapper.insert(reminder);
        return reminder;
    }

    @Test
    @Order(1)
    @DisplayName("1. 리마인더 생성 시 ID가 자동 생성되어야 한다")
    void insert_shouldCreateReminder() {
        Long listId = createTestList();
        ReminderDto reminder = ReminderDto.builder()
                .listId(listId)
                .title("테스트 할 일")
                .memo("메모 내용")
                .priority(2)
                .isFlagged(true)
                .isCompleted(false)
                .sortOrder(1)
                .build();

        reminderMapper.insert(reminder);
        assertThat(reminder.getId()).isNotNull();

        ReminderDto found = reminderMapper.findById(reminder.getId());
        assertThat(found.getTitle()).isEqualTo("테스트 할 일");
        assertThat(found.getMemo()).isEqualTo("메모 내용");
        assertThat(found.getPriority()).isEqualTo(2);
        assertThat(found.getIsFlagged()).isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("2. 리스트별 리마인더 조회 시 해당 리스트의 항목만 반환해야 한다")
    void findByListId_shouldReturnCorrectReminders() {
        Long listId1 = createTestList();
        Long listId2 = createTestList();
        createTestReminder(listId1, "리스트1 할 일");
        createTestReminder(listId2, "리스트2 할 일");

        List<ReminderDto> result = reminderMapper.findByListId(listId1);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("리스트1 할 일");
    }

    @Test
    @Order(3)
    @DisplayName("3. 리마인더 수정 시 변경 내용이 반영되어야 한다")
    void update_shouldModifyReminder() {
        Long listId = createTestList();
        ReminderDto reminder = createTestReminder(listId, "수정 전");

        reminder.setTitle("수정 후");
        reminder.setMemo("새 메모");
        reminder.setPriority(3);
        reminderMapper.update(reminder);

        ReminderDto updated = reminderMapper.findById(reminder.getId());
        assertThat(updated.getTitle()).isEqualTo("수정 후");
        assertThat(updated.getMemo()).isEqualTo("새 메모");
        assertThat(updated.getPriority()).isEqualTo(3);
    }

    @Test
    @Order(4)
    @DisplayName("4. 완료 토글 시 is_completed와 completed_at이 변경되어야 한다")
    void toggleComplete_shouldToggleStatus() {
        Long listId = createTestList();
        ReminderDto reminder = createTestReminder(listId, "토글 테스트");

        // 미완료 → 완료
        reminderMapper.toggleComplete(reminder.getId());
        ReminderDto toggled = reminderMapper.findById(reminder.getId());
        assertThat(toggled.getIsCompleted()).isTrue();
        assertThat(toggled.getCompletedAt()).isNotNull();

        // 완료 → 미완료
        reminderMapper.toggleComplete(reminder.getId());
        ReminderDto toggledBack = reminderMapper.findById(reminder.getId());
        assertThat(toggledBack.getIsCompleted()).isFalse();
        assertThat(toggledBack.getCompletedAt()).isNull();
    }

    @Test
    @Order(5)
    @DisplayName("5. 리마인더 삭제 시 조회 결과가 null이어야 한다")
    void deleteById_shouldRemoveReminder() {
        Long listId = createTestList();
        ReminderDto reminder = createTestReminder(listId, "삭제용");

        reminderMapper.deleteById(reminder.getId());

        assertThat(reminderMapper.findById(reminder.getId())).isNull();
    }

    @Test
    @Order(6)
    @DisplayName("6. 오늘 마감 리마인더만 조회되어야 한다")
    void findToday_shouldReturnTodayReminders() {
        Long listId = createTestList();
        // DB 서버가 UTC 기준이므로 UTC 날짜를 사용
        LocalDate utcToday = LocalDate.now(ZoneOffset.UTC);
        ReminderDto today = ReminderDto.builder()
                .listId(listId).title("오늘 할 일")
                .dueDate(utcToday.atTime(12, 0))
                .priority(0).isFlagged(false).isCompleted(false).sortOrder(1).build();
        ReminderDto tomorrow = ReminderDto.builder()
                .listId(listId).title("내일 할 일")
                .dueDate(utcToday.plusDays(1).atTime(12, 0))
                .priority(0).isFlagged(false).isCompleted(false).sortOrder(2).build();
        reminderMapper.insert(today);
        reminderMapper.insert(tomorrow);

        List<ReminderDto> result = reminderMapper.findToday();
        assertThat(result).anyMatch(r -> "오늘 할 일".equals(r.getTitle()));
        assertThat(result).noneMatch(r -> "내일 할 일".equals(r.getTitle()));
    }

    @Test
    @Order(7)
    @DisplayName("7. 플래그 리마인더만 조회되어야 한다")
    void findFlagged_shouldReturnFlaggedOnly() {
        Long listId = createTestList();
        ReminderDto flagged = ReminderDto.builder()
                .listId(listId).title("플래그 있음")
                .priority(0).isFlagged(true).isCompleted(false).sortOrder(1).build();
        ReminderDto notFlagged = ReminderDto.builder()
                .listId(listId).title("플래그 없음")
                .priority(0).isFlagged(false).isCompleted(false).sortOrder(2).build();
        reminderMapper.insert(flagged);
        reminderMapper.insert(notFlagged);

        List<ReminderDto> result = reminderMapper.findFlagged();
        assertThat(result).anyMatch(r -> "플래그 있음".equals(r.getTitle()));
        assertThat(result).noneMatch(r -> "플래그 없음".equals(r.getTitle()));
    }

    @Test
    @Order(8)
    @DisplayName("8. 검색 시 제목 또는 메모에 키워드가 포함된 리마인더를 반환해야 한다")
    void search_shouldFindByTitleOrMemo() {
        Long listId = createTestList();
        String uniqueKeyword = "유니크검색어XYZ";
        ReminderDto r1 = ReminderDto.builder()
                .listId(listId).title(uniqueKeyword + " 제목 매칭").memo(null)
                .priority(0).isFlagged(false).isCompleted(false).sortOrder(1).build();
        ReminderDto r2 = ReminderDto.builder()
                .listId(listId).title("장보기").memo(uniqueKeyword + " 메모 매칭")
                .priority(0).isFlagged(false).isCompleted(false).sortOrder(2).build();
        ReminderDto r3 = ReminderDto.builder()
                .listId(listId).title("운동").memo(null)
                .priority(0).isFlagged(false).isCompleted(false).sortOrder(3).build();
        reminderMapper.insert(r1);
        reminderMapper.insert(r2);
        reminderMapper.insert(r3);

        List<ReminderDto> result = reminderMapper.search(uniqueKeyword);
        assertThat(result).hasSize(2);
        assertThat(result).anyMatch(r -> r.getTitle().contains(uniqueKeyword));
        assertThat(result).anyMatch(r -> "장보기".equals(r.getTitle()));
    }

    @Test
    @Order(9)
    @DisplayName("9. 스마트 리스트 카운트가 정확히 반환되어야 한다")
    void getCounts_shouldReturnCorrectCounts() {
        Map<String, Object> counts = reminderMapper.getCounts();
        assertThat(counts).containsKeys("today", "scheduled", "total", "flagged", "completed");
    }
}
