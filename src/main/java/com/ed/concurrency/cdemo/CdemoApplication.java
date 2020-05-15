package com.ed.concurrency.cdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication()
@MapperScan("com.ed.concurrency.cdemo.mapper")
public class CdemoApplication {
    //在内存标记存在的情况下，所有访问都被直接挡出，连Redis都没有访问，理论上是机器QPS的极限
    public static void main(String[] args) {
        SpringApplication.run(CdemoApplication.class, args);
    }

}
