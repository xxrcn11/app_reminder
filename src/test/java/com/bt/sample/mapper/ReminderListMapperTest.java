package com.bt.sample.mapper;

import com.bt.sample.domain.ReminderListDto;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReminderListMapperTest {

    @Autowired
    private ReminderListMapper reminderListMapper;

    @Test
    @Order(1)
    @DisplayName("1. 새 리스트 생성 시 ID가 자동 생성되고 데이터가 저장되어야 한다")
    void insert_shouldCreateNewList() {
        ReminderListDto newList = ReminderListDto.builder()
                .name("테스트 리스트")
                .color("#FF3B30")
                .icon("star")
                .sortOrder(1)
                .build();

        reminderListMapper.insert(newList);
        assertThat(newList.getId()).isNotNull();

        ReminderListDto found = reminderListMapper.findById(newList.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("테스트 리스트");
        assertThat(found.getColor()).isEqualTo("#FF3B30");
        assertThat(found.getIcon()).isEqualTo("star");
    }

    @Test
    @Order(2)
    @DisplayName("2. 전체 리스트 조회 시 생성한 데이터가 포함되어야 한다")
    void findAll_shouldReturnLists() {
        reminderListMapper.insert(ReminderListDto.builder()
                .name("조회용 리스트").color("#007AFF").sortOrder(1).build());

        List<ReminderListDto> lists = reminderListMapper.findAll();
        assertThat(lists).isNotEmpty();
        assertThat(lists).anyMatch(l -> "조회용 리스트".equals(l.getName()));
    }

    @Test
    @Order(3)
    @DisplayName("3. ID로 리스트 조회 시 해당 리스트를 반환해야 한다")
    void findById_shouldReturnList() {
        ReminderListDto newList = ReminderListDto.builder()
                .name("ID조회 리스트").color("#34C759").sortOrder(1).build();
        reminderListMapper.insert(newList);

        ReminderListDto found = reminderListMapper.findById(newList.getId());
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(newList.getId());
        assertThat(found.getName()).isEqualTo("ID조회 리스트");
    }

    @Test
    @Order(4)
    @DisplayName("4. 리스트 수정 시 이름과 색상이 변경되어야 한다")
    void update_shouldModifyList() {
        ReminderListDto newList = ReminderListDto.builder()
                .name("수정 전").color("#007AFF").icon("folder").sortOrder(1).build();
        reminderListMapper.insert(newList);

        newList.setName("수정 후");
        newList.setColor("#FF9500");
        reminderListMapper.update(newList);

        ReminderListDto updated = reminderListMapper.findById(newList.getId());
        assertThat(updated.getName()).isEqualTo("수정 후");
        assertThat(updated.getColor()).isEqualTo("#FF9500");
    }

    @Test
    @Order(5)
    @DisplayName("5. 리스트 삭제 시 조회 결과가 null이어야 한다")
    void deleteById_shouldRemoveList() {
        ReminderListDto newList = ReminderListDto.builder()
                .name("삭제용 리스트").color("#FF2D55").sortOrder(1).build();
        reminderListMapper.insert(newList);
        Long id = newList.getId();

        reminderListMapper.deleteById(id);

        ReminderListDto deleted = reminderListMapper.findById(id);
        assertThat(deleted).isNull();
    }
}
