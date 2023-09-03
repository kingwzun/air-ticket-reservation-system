package com.atrs.airticketreservationsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Orders {

  private Long orderId;
  private Long flightId;
  private String seatType;
  private Integer bookingPerson;
  private Integer passenger;
  private Long isCheckedBaggage;
  private Double amount;
  private Integer isUse;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String orderTime;
  private Long isCancelled;
  private LocalDateTime cancellationTime;
  private Long isUpgrade;
  private Long upgradeOrderId;
  private Integer isUpgradeOrder;
  @TableField(exist = false)
  private Long aircraftId;
  @TableField(exist = false)
  private String aircraftCode;
  @TableField(exist = false)
  private Long departureAirportId;
  @TableField(exist = false)
  private Long destinationAirportId;
  @TableField(exist = false)
  private String departureAirport;
  @TableField(exist = false)
  private String destinationAirport;
  @TableField(exist = false)
  private String departureCity;
  @TableField(exist = false)
  private String destinationCity;
  @TableField(exist = false)
  private String bookPersonName;
  @TableField(exist = false)
  private String passengerName;


}
