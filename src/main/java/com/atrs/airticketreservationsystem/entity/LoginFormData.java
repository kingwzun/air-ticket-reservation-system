package com.atrs.airticketreservationsystem.entity;

import lombok.Data;

@Data
public class LoginFormData {
    private String phoneNumber;
    private String account;
    private String password;
    private String code;
    private String RedisKey;
    private String email;
}
