package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.AircraftType;
import com.atrs.airticketreservationsystem.mapper.AircraftTypeMapper;
import com.atrs.airticketreservationsystem.service.AircraftTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AircraftTypeServiceImpl extends ServiceImpl<AircraftTypeMapper, AircraftType> implements AircraftTypeService {
}
