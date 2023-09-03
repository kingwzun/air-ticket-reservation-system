package com.atrs.airticketreservationsystem.dto;

import com.atrs.airticketreservationsystem.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    List<Orders> orders;
}
