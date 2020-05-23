package com.ed.concurrency.cdemo.service;

import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.bean.SecKillOrder;

import java.util.List;

public interface DBResetService {

    public int ResetDB(String secId, Integer stockCount);

    public int clearOrders(String secId);

    public List<SecKillOrder> countFromOrders();
}
