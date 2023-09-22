package com.chaoxi.uniapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaoxi.uniapp.entity.UserEntity;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends  MPJBaseMapper<UserEntity> {
}
