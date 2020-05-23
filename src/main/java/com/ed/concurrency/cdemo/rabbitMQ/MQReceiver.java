package com.ed.concurrency.cdemo.rabbitMQ;


import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.User;
import com.ed.concurrency.cdemo.controller.SecKillController;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class MQReceiver {
//    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
//
//    @Autowired
//    SecKillService secKillService;
//
//    @Autowired
//    RedisService redisService;
//
//    @RabbitListener(queues = MQConfig.SEC_KILL_QUEUE)
//    public void receive(String message){
//        log.info("receive message:"+message);
//        SecKillMessage secKillMessage = RedisService.stringToBean(message, SecKillMessage.class);
//        //取出数据
//        User user = secKillMessage.getUser();
//        String secId = secKillMessage.getSecId();
//
//        try{
//            secKillService.SecKillGoods(user, secId);
//        }catch (Exception e){
//            if("请勿重复购买".equals(e.getMessage())){
//                //重复购买，复原库存,内存标记
//                redisService.incr(secId);
//                SecKillController.proMap.put(secId, false);
//            }
//            log.info(e.getMessage());
//        }
//
//
//    }
//
//}
