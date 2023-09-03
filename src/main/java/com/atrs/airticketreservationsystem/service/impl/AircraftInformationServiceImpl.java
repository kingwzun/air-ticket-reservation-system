package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.AircraftInformation;
import com.atrs.airticketreservationsystem.mapper.AircraftInformationMapper;
import com.atrs.airticketreservationsystem.service.AircraftInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AircraftInformationServiceImpl extends ServiceImpl<AircraftInformationMapper, AircraftInformation> implements AircraftInformationService {
}
