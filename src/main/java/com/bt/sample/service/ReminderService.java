package com.bt.sample.service;

import com.bt.sample.domain.ReminderDto;

import java.util.List;
import java.util.Map;

public interface ReminderService {

    List<ReminderDto> findByListId(Long listId);

    ReminderDto findById(Long id);

    void create(ReminderDto reminder);

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
    List<ReminderDto> search(String keyword);

    // 카운트
    Map<String, Object> getCounts();
}
