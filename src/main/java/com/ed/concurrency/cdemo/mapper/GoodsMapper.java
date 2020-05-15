package com.ed.concurrency.cdemo.mapper;

import com.ed.concurrency.cdemo.bean.Goods;

public interface GoodsMapper {

    public Goods queryGoods(String goodsId);
}
