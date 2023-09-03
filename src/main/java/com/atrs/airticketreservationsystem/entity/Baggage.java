package com.atrs.airticketreservationsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Baggage {
  @TableId(type = IdType.AUTO)
  private Long baggageId;
  private String baggageCode;
  private Long ticketId;
  private Double baggageWeight;
  private Long baggageItemCount;
  private Double price;
  private Integer status;


}
