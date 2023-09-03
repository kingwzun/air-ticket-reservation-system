package com.atrs.airticketreservationsystem.mapper;

import com.atrs.airticketreservationsystem.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}
