package com.atrs.airticketreservationsystem.mapper;

import com.atrs.airticketreservationsystem.entity.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {
}
