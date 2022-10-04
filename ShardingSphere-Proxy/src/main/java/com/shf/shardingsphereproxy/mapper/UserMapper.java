package com.shf.shardingsphereproxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.shardingsphereproxy.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
