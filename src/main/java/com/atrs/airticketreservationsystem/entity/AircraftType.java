package com.atrs.airticketreservationsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AircraftType {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String model;

  private Long passengerCapacity;
  private Long firstClassCapacity;
  private Long economyClassCapacity;
  private LocalDateTime publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;


}
