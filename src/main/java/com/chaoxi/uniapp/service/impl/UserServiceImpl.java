package com.chaoxi.uniapp.service.impl;

import com.chaoxi.uniapp.entity.UserEntity;
import com.chaoxi.uniapp.mapper.UserMapper;
import com.chaoxi.uniapp.service.UserService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    UserMapper userMapper;

    public String testSpring(){
        return "测试两下";
    }
}
