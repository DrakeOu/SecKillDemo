package com.ed.concurrency.cdemo.mapper;

import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.bean.SecKillOrder;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SecKillMapper {

    public int SecKillGoods(String secId);

    int CreateSecOrder(@Param("userId") String userId, @Param("secId") String secId, @Param("orderId") String orderId);

    int CreateOrder(@Param("userId") String userId, @Param("secId") String secId, @Param("orderId") String orderId);

    public SecKillGoods querySecKillDetail(@Param("goodsId") String secId);

    public List<SecKillGoods> queryAllGoods();

    public SecKillOrder querySecKillOrder(String userId);
}
