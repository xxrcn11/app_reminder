package com.bt.sample.service.impl;

import com.bt.sample.domain.ReminderDto;
import com.bt.sample.mapper.ReminderMapper;
import com.bt.sample.service.ReminderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DefaultReminderService implements ReminderService {

    private final ReminderMapper reminderMapper;

    public DefaultReminderService(ReminderMapper reminderMapper) {
        this.reminderMapper = reminderMapper;
    }

    @Override
    public List<ReminderDto> findByListId(Long listId) {
        return reminderMapper.findByListId(listId);
    }

    @Override
    public ReminderDto findById(Long id) {
        return reminderMapper.findById(id);
    }

    @Override
    @Transactional
    public void create(ReminderDto reminder) {
        reminderMapper.insert(reminder);
    }

    @Override
    @Transactional
    public void update(ReminderDto reminder) {
        reminderMapper.update(reminder);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reminderMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void toggleComplete(Long id) {
        reminderMapper.toggleComplete(id);
    }

    @Override
    public List<ReminderDto> findToday() {
        return reminderMapper.findToday();
    }

    @Override
    public List<ReminderDto> findScheduled() {
        return reminderMapper.findScheduled();
    }

    @Override
    public List<ReminderDto> findAllIncomplete() {
        return reminderMapper.findAllIncomplete();
    }

    @Override
    public List<ReminderDto> findFlagged() {
        return reminderMapper.findFlagged();
    }

    @Override
    public List<ReminderDto> findCompleted() {
        return reminderMapper.findCompleted();
    }

    @Override
    public List<ReminderDto> search(String keyword) {
        return reminderMapper.search(keyword);
    }

    @Override
    public Map<String, Object> getCounts() {
        return reminderMapper.getCounts();
    }
}
