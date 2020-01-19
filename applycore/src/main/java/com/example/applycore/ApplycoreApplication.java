package com.example.applycore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.applycore.mapper")
public class ApplycoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplycoreApplication.class, args);
    }

}
