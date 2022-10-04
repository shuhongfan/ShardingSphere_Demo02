package com.shf.shardingspherejdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.shardingspherejdbc.entity.Dict;
import com.shf.shardingspherejdbc.entity.Order;
import com.shf.shardingspherejdbc.entity.OrderItem;
import com.shf.shardingspherejdbc.entity.User;
import com.shf.shardingspherejdbc.mapper.DictMapper;
import com.shf.shardingspherejdbc.mapper.OrderItemMapper;
import com.shf.shardingspherejdbc.mapper.OrderMapper;
import com.shf.shardingspherejdbc.mapper.UserMapper;
import com.shf.shardingspherejdbc.vo.OrderVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShardingSphereJdbcApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUname("zhansan");
        userMapper.insert(user);
    }

    @Transactional
    @Test
    public void testTrans() {
        User user = new User();
        user.setUname("体操");
        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectList(null);
        List<User> users2 = userMapper.selectList(null);
        List<User> users3 = userMapper.selectList(null);
        List<User> users4 = userMapper.selectList(null);
    }


    /**
     * 垂直分片：插入数据测试
     */
    @Test
    public void testInsertOrderAndUser() {
        User user = new User();
        user.setUname("张三");
        userMapper.insert(user);

        Order order = new Order();
        order.setOrderNo("ATGUIGU001");
        order.setUserId(user.getId());
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);
    }

    /**
     * 水平分片：插入数据测试
     */
    @Test
    public void testInsertOrder() {
        Order order = new Order();
        order.setOrderNo("ATGUIGU001");
        order.setUserId(1L);
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);
    }

    /**
     * 水平分片：分表插入数据测试
     */
    @Test
    public void testInsertOrderTableStrategy() {

        for (long i = 1; i < 5; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }

        for (long i = 5; i < 9; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }

    /**
     * 测试哈希取模
     */
    @Test
    public void testHash() {

        //注意hash取模的结果是整个字符串hash后再取模，和数值后缀是奇数还是偶数无关
        System.out.println("ATGUIGU001".hashCode() % 2);
        System.out.println("ATGUIGU0011".hashCode() % 2);
    }


    /**
     * 水平分片：查询所以记录
     */
    @Test
    public void testShardingSelectAll() {
        List<Order> orders = orderMapper.selectList(null);
        orders.forEach(System.out::println);
    }

    /**
     * 水平分片： 根据user_id查询记录
     */
    @Test
    public void testShardingSelectByUserId() {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id", 1L);

        List<Order> orders = orderMapper.selectList(orderQueryWrapper);
        orders.forEach(System.out::println);
    }

    /**
     * 测试关联表插入
     */
    @Test
    public void testInsertOrderAndOrderItem() {

        for (long i = 1; i < 3; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(1L);
            orderMapper.insert(order);

            for (long j = 1; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("ATGUIGU" + i);
                orderItem.setUserId(1L);
                orderItem.setPrice(new BigDecimal(10));
                orderItem.setCount(2);
                orderItemMapper.insert(orderItem);
            }
        }

        for (long i = 5; i < 7; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(2L);
            orderMapper.insert(order);

            for (long j = 1; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("ATGUIGU" + i);
                orderItem.setUserId(2L);
                orderItem.setPrice(new BigDecimal(1));
                orderItem.setCount(3);
                orderItemMapper.insert(orderItem);
            }
        }

    }

    /**
     * 测试关联表查询
     */
    @Test
    public void testGetOrderAmount() {

        List<OrderVo> orderAmountList = orderMapper.getOrderAmount();
        orderAmountList.forEach(System.out::println);
    }


    /**
     * 广播表：每个服务器中的t_dict同时添加了新数据
     */
    @Test
    public void testBroadcast() {

        Dict dict = new Dict();
        dict.setDictType("type1");
        dictMapper.insert(dict);
    }

    /**
     * 查询操作，只从一个节点获取数据
     * 随机负载均衡规则
     */
    @Test
    public void testSelectBroadcast() {

        List<Dict> dicts = dictMapper.selectList(null);
        dicts.forEach(System.out::println);
    }
}
