package com.shf.shardingspherejdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("t_order")
@Data
public class Order {
    @TableId(type = IdType.AUTO) //依赖数据库的主键自增策略
//    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal amount;
}