package com.example.rabbitmq.controller;

import com.example.rabbitmq.Sender.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitmqController {

    @Autowired
    private HelloSender helloSender;

    @RequestMapping("test")
    public String hello(){
        helloSender.send();
        return "hello";
    }
} 