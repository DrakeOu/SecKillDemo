package com.ed.concurrency.cdemo.service.serviceImpl;

import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.Goods;
import com.ed.concurrency.cdemo.mapper.GoodsMapper;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Result queryGoodsInfo(String goodsId) {
        if(goodsId == null){
            return ResultUtil.error(CodeMsgUtil.QUERY_ERROR);
        }
        Goods goods = goodsMapper.queryGoods(goodsId);
        if(goods==null){
            return ResultUtil.error(CodeMsgUtil.GOODS_NOT_FOUND);
        }
        return ResultUtil.success(CodeMsgUtil.SUCCESS, goods);
    }
}
