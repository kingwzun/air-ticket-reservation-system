package com.atrs.airticketreservationsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
@Data
public class Announcement {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String title;
  private String content;
  private Integer status;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String ttl;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String publishTime;
  private LocalDateTime modifyTime;
  private String creator;
  private String modifier;



}
