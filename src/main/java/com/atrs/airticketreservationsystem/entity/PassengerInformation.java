package com.atrs.airticketreservationsystem.entity;

import lombok.Data;

@Data
public class PassengerInformation {

  private long id;
  private long userId;
  private String passengerName;
  private String identificationCard;
  private String phoneNumber;


}
