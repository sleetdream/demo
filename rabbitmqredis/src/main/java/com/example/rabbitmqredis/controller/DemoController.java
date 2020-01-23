package com.example.rabbitmqredis.controller;

import com.example.rabbitmqredis.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PutMapping(value = "order")
    public String putOrder(){
        String msg = "putOrder";
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_QUEUE, msg, new CorrelationData(UUID.randomUUID().toString()));
        return "OK";
    }
}
