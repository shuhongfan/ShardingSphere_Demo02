package com.shf.shardingspherejdbc.controller;

import com.shf.shardingspherejdbc.entity.User;
import com.shf.shardingspherejdbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("selectAll")
    public List<User> selectAll() {
        List<User> users = userMapper.selectList(null);
        return users;
    }
}
