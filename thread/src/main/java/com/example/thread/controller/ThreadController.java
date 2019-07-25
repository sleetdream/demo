package com.example.thread.controller;

import com.example.thread.service.MultiThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadController {

    @Autowired
    MultiThreadService service;

    @RequestMapping("test")
    public String hello(){
        service.executeAysncTask();
        return "hello";
    }
} 