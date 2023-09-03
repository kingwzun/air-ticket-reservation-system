package com.atrs.airticketreservationsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Administrator {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String administratorType;
  private String status;


}
