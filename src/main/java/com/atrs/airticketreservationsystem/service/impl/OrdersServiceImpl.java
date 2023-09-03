package com.atrs.airticketreservationsystem.service.impl;

import com.atrs.airticketreservationsystem.entity.JsonResponse;

import com.atrs.airticketreservationsystem.entity.Orders;

import com.atrs.airticketreservationsystem.mapper.OrdersMapper;

import com.atrs.airticketreservationsystem.service.OrdersService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
