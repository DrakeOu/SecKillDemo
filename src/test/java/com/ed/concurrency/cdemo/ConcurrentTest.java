package com.ed.concurrency.cdemo;

import com.ed.concurrency.cdemo.mapper.SecKillMapper;
import com.ed.concurrency.cdemo.service.SecKillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConcurrentTest extends BaseTest{

    @Autowired
    SecKillMapper mapper;

    @Autowired
    SecKillService service;

    @Test
    public void MapperTest(){
//        Product product = new Product("1", 10);
//        mapper.SecKillProduct(product);
    }

    @Test
    public void SecKillTest(){
//        Product product = new Product();
//        product.setProductId("1");
//        service.SecKillProduct(product);
    }

    @Test
    public void CreateOrderTest(){
//        Order order = new Order("2", "3213");

    }
}
