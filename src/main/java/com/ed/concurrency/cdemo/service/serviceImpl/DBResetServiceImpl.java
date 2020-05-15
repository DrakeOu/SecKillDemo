package com.ed.concurrency.cdemo.service.serviceImpl;

import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.mapper.DBResetMapper;
import com.ed.concurrency.cdemo.service.DBResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBResetServiceImpl implements DBResetService {

    @Autowired
    DBResetMapper dbResetMapper;

    @Override
    public int ResetDB(String secId, Integer stockCount) {
        return dbResetMapper.ResetDB(secId, stockCount);
    }

    @Override
    public int clearOrders(String secId) {
        return dbResetMapper.clearOrders(secId);
    }

    @Override
    public List<SecKillGoods> countFromOrders() {
        return dbResetMapper.countFromOrders();
    }
}
