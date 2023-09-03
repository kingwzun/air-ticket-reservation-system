package com.atrs.airticketreservationsystem.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String account;
    private String avatar;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private Long age;
    private String gender;
    private Long vipStatus;
    private Double totalExpenses;
    private String administratorType;
}
