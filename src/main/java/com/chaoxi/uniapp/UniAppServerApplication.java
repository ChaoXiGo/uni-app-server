package com.chaoxi.uniapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j // 自定义打印日志信息
@SpringBootApplication
@ServletComponentScan
public class UniAppServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniAppServerApplication.class, args);
    }
}
