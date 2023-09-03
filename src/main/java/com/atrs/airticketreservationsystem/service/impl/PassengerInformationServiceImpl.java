package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.PassengerInformation;
import com.atrs.airticketreservationsystem.mapper.PassengerInformationMapper;
import com.atrs.airticketreservationsystem.service.PassengerInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PassengerInformationServiceImpl extends ServiceImpl<PassengerInformationMapper, PassengerInformation> implements PassengerInformationService {
}
