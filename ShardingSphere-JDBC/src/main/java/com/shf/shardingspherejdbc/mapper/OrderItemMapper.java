package com.shf.shardingspherejdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.shardingspherejdbc.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
