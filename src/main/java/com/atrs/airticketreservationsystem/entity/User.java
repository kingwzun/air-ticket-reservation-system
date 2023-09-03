package com.atrs.airticketreservationsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data

public class User {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String phoneNumber;
  private String email;
  private String idNumber;
  private Long age;
  private String gender;
  private Long vipStatus;
  private String accountStatus;
  private Double totalExpenses;
  private String userType;


}
