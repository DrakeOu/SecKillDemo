package com.ed.concurrency.cdemo.controller;


import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.mapper.DBResetMapper;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.DBResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ResetController {

    @Autowired
    RedisService redisService;

    @Autowired
    DBResetService dbResetService;

    @RequestMapping("/reset")
    public Result resetDB(){
        //根据订单计算每件商品各自剩余的秒杀库存
        List<SecKillGoods> goodsList = dbResetService.countFromOrders();
        //复原mysql,redis
        for (SecKillGoods goods :
                goodsList) {
            redisService.add(String.valueOf(goods.getGoodsId()), goods.getStockCount());
            dbResetService.ResetDB(String.valueOf(goods.getGoodsId()), goods.getStockCount());
            //清空全部订单
            dbResetService.clearOrders(String.valueOf(goods.getGoodsId()));
        }
        return ResultUtil.success(CodeMsgUtil.SUCCESS, "复原完成");
    }

}
