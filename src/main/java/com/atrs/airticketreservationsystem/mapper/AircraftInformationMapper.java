package com.atrs.airticketreservationsystem.mapper;

import com.atrs.airticketreservationsystem.entity.AircraftInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AircraftInformationMapper extends BaseMapper<AircraftInformation> {
}
