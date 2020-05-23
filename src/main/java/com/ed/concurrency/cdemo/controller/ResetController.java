package com.ed.concurrency.cdemo.controller;


import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.bean.SecKillOrder;
import com.ed.concurrency.cdemo.mapper.DBResetMapper;
import com.ed.concurrency.cdemo.redisService.RedisConfig;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.DBResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ResetController {

    @Autowired
    RedisService redisService;

    @Autowired
    DBResetService dbResetService;

    @RequestMapping("/reset")
    public Result resetDB(){
        //根据订单计算每件商品各自剩余的秒杀库存
        List<SecKillOrder> secKillOrders = dbResetService.countFromOrders();
        //复原mysql,redis
        for (SecKillOrder order :
                secKillOrders) {
            redisService.incr(String.valueOf(order.getGoodsId()));
            dbResetService.ResetDB(String.valueOf(order.getGoodsId()), 1);
            //清空全部订单
            dbResetService.clearOrders(String.valueOf(order.getGoodsId()));
            redisService.del(RedisConfig.ORDER_PREFIX+order.getUserId()+order.getGoodsId());
        }
        return ResultUtil.success(CodeMsgUtil.SUCCESS, "复原完成");
    }

}
