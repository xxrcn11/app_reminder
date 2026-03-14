package com.bt.sample.service;

import com.bt.sample.dto.UserDto;
import com.bt.sample.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserDto> findAll() {
        return userMapper.findAll();
    }

    public UserDto findById(Long id) {
        return userMapper.findById(id);
    }

    public void create(UserDto user) {
        userMapper.insert(user);
    }

    public void update(UserDto user) {
        userMapper.update(user);
    }

    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
}
