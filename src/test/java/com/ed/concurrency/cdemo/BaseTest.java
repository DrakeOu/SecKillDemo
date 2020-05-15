package com.ed.concurrency.cdemo;
import com.ed.concurrency.cdemo.CdemoApplicationTests;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CdemoApplication.class)
public class BaseTest {

    public void startTest(){
        System.out.println("-------------starting test-----------------");
    }
}
