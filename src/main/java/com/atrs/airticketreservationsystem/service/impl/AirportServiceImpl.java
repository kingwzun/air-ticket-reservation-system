package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.Airport;
import com.atrs.airticketreservationsystem.mapper.AirportMapper;
import com.atrs.airticketreservationsystem.service.AirportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl extends ServiceImpl<AirportMapper, Airport> implements AirportService {
}
