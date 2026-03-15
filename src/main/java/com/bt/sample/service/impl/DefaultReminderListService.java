package com.bt.sample.service.impl;

import com.bt.sample.service.ReminderListService;

import com.bt.sample.domain.ReminderListDto;
import com.bt.sample.mapper.ReminderListMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DefaultReminderListService implements ReminderListService {

    private final ReminderListMapper reminderListMapper;

    public DefaultReminderListService(ReminderListMapper reminderListMapper) {
        this.reminderListMapper = reminderListMapper;
    }

    @Override
    public List<ReminderListDto> findAll() {
        return reminderListMapper.findAll();
    }

    @Override
    public ReminderListDto findById(Long id) {
        return reminderListMapper.findById(id);
    }

    @Override
    @Transactional
    public void create(ReminderListDto list) {
        reminderListMapper.insert(list);
    }

    @Override
    @Transactional
    public void update(ReminderListDto list) {
        reminderListMapper.update(list);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reminderListMapper.deleteById(id);
    }
}
