package com.atrs.airticketreservationsystem.dto;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class RouteDTO{
    private Long id;
    private Long departureAirportId;
    private Long destinationAirportId;
    private LocalDateTime publishTime;
    private LocalDateTime modifyTime;
    private String creator;
    private String modifier;
    private String departureAirport;
    private String destinationAirport;
}
