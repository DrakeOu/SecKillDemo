package com.ed.concurrency.cdemo.service.serviceImpl;

import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.bean.SecKillOrder;
import com.ed.concurrency.cdemo.bean.User;
import com.ed.concurrency.cdemo.mapper.SecKillMapper;
import com.ed.concurrency.cdemo.redisService.RedisConfig;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    SecKillMapper mapper;

    @Autowired
    RedisService redisService;

    /**
     * 第一版：直接访问MySQL，每个请求都会查询DB
     * QPS：270，开始商品没有售完QPS极低
     * @param
     */
    @Transactional
    @Override
    public void SecKillGoods(User user, String secId) {
        //检查是否重复秒杀，这个方法不是同步的，主要依赖mysql的unique索引保证不会重复秒杀
        String existOrder = redisService.get(RedisConfig.ORDER_PREFIX + user.getId()+secId);
        if(!"".equals(existOrder)){
            throw new RuntimeException("请勿重复购买");
        }
        // MyBatis中类似update这种没有返回值的操作是可以返回一个int表示更改的行数，可以用来判断操作是否执行
        int success = mapper.SecKillGoods(secId);
        if(success == 0){
            throw new RuntimeException("商品已售完");
        }
        //创建订单
        String orderId = UUID.randomUUID().toString().substring(0, 25);
        try{
            //写到mysql中
            mapper.CreateSecOrder(String.valueOf(user.getId()), secId, orderId);
        }catch (Exception e){
            throw new RuntimeException("请勿重复购买");
        }
        //将订单写到redis缓存中
        redisService.set(RedisConfig.ORDER_PREFIX+user.getId().toString()+secId, orderId);
        //先不创建真实的订单
//        mapper.CreateOrder(String.valueOf(user.getId()), secId, orderId);
    }

    @Override
    public Result SecKillDetail(String secId, HttpServletRequest request) {
        //检查是否登录了
        Cookie[] cookies = request.getCookies();
        Cookie cookie;
        //没有登录
        if(cookies==null || (cookie = findLoginCookie(cookies))==null){
            return ResultUtil.error(CodeMsgUtil.USER_NOT_LOGIN);
        }
        //在redis中检查token
        String token = cookie.getValue();
        if(!redisService.exists(token)){
            return ResultUtil.error(CodeMsgUtil.USER_NOT_LOGIN);
        }

        SecKillGoods secKillGoods = mapper.querySecKillDetail(secId);
        //秒杀商品不存在
        if(secKillGoods==null){
            return ResultUtil.error(CodeMsgUtil.SEC_NOT_EXIST);
        }
        return ResultUtil.success(CodeMsgUtil.SUCCESS, secKillGoods);
    }

    private Cookie findLoginCookie(Cookie[] cookies){
        for (Cookie c :
                cookies) {
            if ("token".equals(c.getName())) return c;
        }
        return null;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        //获取到用户
        Cookie cookie = findLoginCookie(request.getCookies());
        String token = cookie.getValue();
        String jsonUser = redisService.get(token);
        return RedisService.stringToBean(jsonUser, User.class);
    }

    @Override
    public List<SecKillGoods> queryAllGoods() {
        return mapper.queryAllGoods();
    }

    @Override
    public boolean checkSeckOrder(User user) {
        //在redis中检查订单
        boolean exists = redisService.exists(RedisConfig.ORDER_PREFIX+user.getId());
        if(exists){
            return true;
        }
        //在mysql中检查
        SecKillOrder secKillOrder = mapper.querySecKillOrder(user.getId().toString());
        return secKillOrder!=null;
    }
}
