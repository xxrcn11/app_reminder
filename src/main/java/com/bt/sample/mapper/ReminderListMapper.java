package com.bt.sample.mapper;

import com.bt.sample.domain.ReminderListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReminderListMapper {

    List<ReminderListDto> findAll();

    ReminderListDto findById(Long id);

    void insert(ReminderListDto list);

    void update(ReminderListDto list);

    void deleteById(Long id);
}
