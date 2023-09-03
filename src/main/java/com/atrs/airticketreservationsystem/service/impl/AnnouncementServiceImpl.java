package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.Announcement;
import com.atrs.airticketreservationsystem.mapper.AnnouncementMapper;
import com.atrs.airticketreservationsystem.service.AnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
}
