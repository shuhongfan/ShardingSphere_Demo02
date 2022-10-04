package com.shf.shardingsphereproxy;

import com.shf.shardingsphereproxy.entity.User;
import com.shf.shardingsphereproxy.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShardingSphereProxyApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    /**
     * 读数据测试
     */
    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}
