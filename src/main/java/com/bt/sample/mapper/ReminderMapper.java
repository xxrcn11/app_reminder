package com.bt.sample.mapper;

import com.bt.sample.domain.ReminderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReminderMapper {

    List<ReminderDto> findByListId(Long listId);

    ReminderDto findById(Long id);

    void insert(ReminderDto reminder);

    void update(ReminderDto reminder);

    void deleteById(Long id);

    void toggleComplete(Long id);

    // 스마트 리스트
    List<ReminderDto> findToday();

    List<ReminderDto> findScheduled();

    List<ReminderDto> findAllIncomplete();

    List<ReminderDto> findFlagged();

    List<ReminderDto> findCompleted();

    // 검색
    List<ReminderDto> search(@Param("keyword") String keyword);

    // 카운트
    Map<String, Object> getCounts();
}
