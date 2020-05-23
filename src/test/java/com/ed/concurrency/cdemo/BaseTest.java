package com.ed.concurrency.cdemo;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CdemoApplication.class)
public class BaseTest {

    public void startTest(){
        System.out.println("-------------starting test-----------------");
    }
}
