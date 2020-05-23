package com.ed.concurrency.cdemo.controller;

import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.User;
//import com.ed.concurrency.cdemo.rabbitMQ.MQSender;
//import com.ed.concurrency.cdemo.rabbitMQ.MQSender;
import com.ed.concurrency.cdemo.rabbitMQ.SecKillMessage;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class SecKillController {

    @Autowired
    SecKillService secKillService;

    @Autowired
    RedisService redisService;

//    @Autowired
//    MQSender sender;

    //内存标记
    public static Map<String, Boolean> proMap = new HashMap<>();

    @RequestMapping("/seckill/{id}")
    public Result SecKill(@PathVariable("id") String secId, HttpServletRequest request){
        //获取登录用户
        User user = secKillService.getLoginUser(request);
        if(user==null){
            return ResultUtil.error(CodeMsgUtil.USER_NOT_LOGIN);
        }
        //加内存标记
        if(proMap.containsKey(secId)&&proMap.get(secId)){
            return ResultUtil.error(CodeMsgUtil.SEC_SOLD_OUT);
        }
        //预减库存
        Long remain = redisService.decr(secId);     //redis是安全的
        if(remain < 0){
            proMap.put(secId, true);
            //不能秒杀的归还库存
            redisService.incr(secId);
            return ResultUtil.error(CodeMsgUtil.SEC_SOLD_OUT);
        }

        try{
            secKillService.SecKillGoods(user, secId);
        }catch (Exception e){
            if("请勿重复购买".equals(e.getMessage())){
                //重复购买，复原库存,内存标记
                redisService.incr(secId);
                proMap.put(secId, false);
            }
            return ResultUtil.error(CodeMsgUtil.ALREADY_HAVE_ORDER);
        }
        //发送到消息队列中异步处理
//        SecKillMessage secKillMessage = new SecKillMessage();
//        secKillMessage.setSecId(secId);
//        secKillMessage.setUser(user);
//        sender.sendSecKillMessage(secKillMessage);
        //直接返回，前端再轮询redis缓存去检查秒杀结果
        return ResultUtil.success(CodeMsgUtil.SUCCESS, "秒杀中，请等待");
    }


    @RequestMapping("/secdetail/{secId}")
    public Result SecKillDetail(@PathVariable String secId, HttpServletRequest request){
        if(secId==null){
            return ResultUtil.error(CodeMsgUtil.QUERY_ERROR);
        }
        return secKillService.SecKillDetail(secId, request);
    }

}
