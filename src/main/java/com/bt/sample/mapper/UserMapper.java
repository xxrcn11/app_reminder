package com.bt.sample.mapper;

import com.bt.sample.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserDto> findAll();

    UserDto findById(Long id);

    void insert(UserDto user);

    void update(UserDto user);

    void deleteById(Long id);
}
