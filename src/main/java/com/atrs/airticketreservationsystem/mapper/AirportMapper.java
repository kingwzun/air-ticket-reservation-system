package com.atrs.airticketreservationsystem.mapper;

import com.atrs.airticketreservationsystem.entity.Airport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AirportMapper extends BaseMapper<Airport> {
}
