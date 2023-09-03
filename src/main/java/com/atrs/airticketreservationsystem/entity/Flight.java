package com.atrs.airticketreservationsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
public class Flight {
  @TableId(type = IdType.AUTO)
  private Long flightId;
  private Long routeId;
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


  private Integer economyClassNum;
  private Integer firstClassNum;
  private Integer status;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String departureTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String arrivalTime;

  private Double economyClassPrice;
  private Double firstClassPrice;
  private LocalDateTime publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;



}
