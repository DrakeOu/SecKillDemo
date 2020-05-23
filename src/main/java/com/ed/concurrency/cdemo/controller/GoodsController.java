package com.ed.concurrency.cdemo.controller;

import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    //直接写url上，会被别人直接遍历出全部数据
    @RequestMapping("/detail/{goodsId}")
    public Result queryGoods(@PathVariable String goodsId){
        //普通 商品
        return goodsService.queryGoodsInfo(goodsId);
    }

}
