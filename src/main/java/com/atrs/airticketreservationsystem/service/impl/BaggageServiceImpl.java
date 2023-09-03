package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.Baggage;
import com.atrs.airticketreservationsystem.mapper.BaggageMapper;
import com.atrs.airticketreservationsystem.service.BaggageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BaggageServiceImpl extends ServiceImpl<BaggageMapper, Baggage> implements BaggageService {
}
