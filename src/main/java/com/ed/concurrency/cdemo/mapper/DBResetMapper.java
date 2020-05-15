package com.ed.concurrency.cdemo.mapper;

import com.ed.concurrency.cdemo.bean.SecKillGoods;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface DBResetMapper {

    public int ResetDB(@Param("secId") String secId, @Param("stockCount") Integer stockCount);

    public int clearOrders(String secId);

    public List<SecKillGoods> countFromOrders();
}
