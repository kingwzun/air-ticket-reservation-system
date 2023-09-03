package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.Vip;
import com.atrs.airticketreservationsystem.mapper.VipMapper;
import com.atrs.airticketreservationsystem.service.VipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VipServiceImpl extends ServiceImpl<VipMapper, Vip> implements VipService {
}
