package com.ed.concurrency.cdemo.rabbitMQ;


import com.ed.concurrency.cdemo.redisService.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendSecKillMessage(SecKillMessage sm){
        String msg = RedisService.beanToString(sm);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.SEC_KILL_QUEUE, msg);
    }

}
