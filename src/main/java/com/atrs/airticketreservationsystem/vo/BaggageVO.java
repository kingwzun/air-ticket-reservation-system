package com.atrs.airticketreservationsystem.vo;

import lombok.Data;

@Data
public class BaggageVO {
    private Long baggageId;
    private String baggageCode;
    private String ticketId;
    private Double baggageWeight;
    private Long baggageItemCount;
    private Double price;
    private Integer status;
}
