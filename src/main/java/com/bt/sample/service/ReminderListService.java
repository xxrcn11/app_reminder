package com.bt.sample.service;

import com.bt.sample.domain.ReminderListDto;

import java.util.List;

public interface ReminderListService {

    List<ReminderListDto> findAll();

    ReminderListDto findById(Long id);

    void create(ReminderListDto list);

    void update(ReminderListDto list);

    void deleteById(Long id);
}
