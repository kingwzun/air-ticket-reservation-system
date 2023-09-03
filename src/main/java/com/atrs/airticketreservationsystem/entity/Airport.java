package com.atrs.airticketreservationsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Airport {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String province;
  private String city;
  private String airportName;
  private Double airportCoordinateX;
  private Double airportCoordinateY;
  private LocalDateTime publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;


}
