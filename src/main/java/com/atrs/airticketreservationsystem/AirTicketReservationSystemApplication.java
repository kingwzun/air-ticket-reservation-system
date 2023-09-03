package com.atrs.airticketreservationsystem;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class AirTicketReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirTicketReservationSystemApplication.class, args);
        System.out.println("启动成功");
    }

}
