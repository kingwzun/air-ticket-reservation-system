package com.atrs.airticketreservationsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atrs.airticketreservationsystem.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailServiceImpl {
    @Resource
    private JavaMailSenderImpl javaMailSender;

    /**
     * 消息队列发送邮箱
     * @param msg
     */
    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consume(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(jsonObject.getString("subject"));
        mail.setFrom("2927697242@qq.com");
        mail.setTo(jsonObject.getString("receiver"));
        mail.setText(jsonObject.getString("content"));
        javaMailSender.send(mail);
    }
}
