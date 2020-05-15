package com.ed.concurrency.cdemo.aspect;

import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitMethod {

    private final Logger log= LoggerFactory.getLogger(InitMethod.class);

    @Autowired
    SecKillService secKillService;

    @Autowired
    RedisService redisService;

    @PostConstruct
    public void init(){
        log.info("--------------------加载redis中--------------------");
        loadToRedis();
    }

    private void loadToRedis(){
        //完全清空redis
        redisService.flushAll();
        //从mysql中读取出秒杀数据
        List<SecKillGoods> goodsList = secKillService.queryAllGoods();
        //遍历，加载到redis
        for (SecKillGoods goods: goodsList) {
            log.info("loadSecGoods: "+goods.getGoodsId());
            redisService.set(String.valueOf(goods.getGoodsId()), String.valueOf(goods.getStockCount().toString()));
        }
    }
}
